import Component from "../../core/component";
import {Auction} from "../../model/auction";
import {CarInfo, getCarTypeName} from "../../model/car";
import ImageSlider from "../ImageSlider/ImageSlider";
import "./auctiondetail.css";
import {deltaTimeToString} from "../../core/util";
import AnimatedNumber from "../AnimatedNumber/AnimatedNumber";
import {
    disconnectSocketSession,
    LiveDTO,
    LiveUser, requestBid,
    requestSocketSession,
    setOnBid,
    setOnEnd,
    setOnStart
} from "../../api/live";
import AnimatedText from "../AnimatedText/AnimatedText";
import {login, UserState, userStateSelector, whoIam} from "../../store/user";
import Toast from "../../core/toast";
import {ModalState, modalStateSelector} from "../../store/modal";
import GlobalStore from "../../core/store";
import {Emoji} from "../../core/emoji";

const BID_UNIT = 5;

const getInfoStr = ({ distance, type, sellName }: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;

class AuctionDetail extends Component<ModalState, Auction> {
    stateSelector(globalState: any): ModalState | undefined {
        return globalState[modalStateSelector];
    }
    onStateChanged(prevLocalState: ModalState) {
        if (!this.state?.pop) {
            disconnectSocketSession();
            Emoji.cancel();
        }
    }

    template(): InnerHTML["innerHTML"] {
        const { title, carInfo } = this.props;
        return `
        <div class="container--left">
            <div data-component="ImageSlider"></div>
            <h4 class="auction-detail__timer">${this.timerInfo()}</h4>
        </div>
        <div class="container--right">
            <h1 class="auction-detail__title">${title}</h1>
            <h2 class="auction-detail__car-info">${getInfoStr(carInfo)}</h2>
            <div class="auction-detail__price">
                KRW <div data-component="AnimatedNumber"></div>
            </div>
            <div class="auction-detail__live-bids-container">
                <div class="auction-detail__live-bids-container__live-bids" data-component="AnimatedText"></div>
            </div>
            <button class="auction-detail__bid-btn" disabled>
                ${this.isEnded() ? '종료된 경매는 입찰할 수 없습니다' : '대기 중'}
            </button>
        </div>
        `;
    }

    private lastPrice = 0;

    initialize() {
        const { auctionId } = this.props;
        this.addEvent('click', '.auction-detail__bid-btn', async () => {
            const suggestedPrice = this.lastPrice + BID_UNIT;
            const userInfo = await whoIam() || await login();
            if (!userInfo) {
                Toast.show('로그인 없이 이용할 수 없습니다', 1000);
                return;
            }
            const res = await requestBid(auctionId, suggestedPrice);
            if (!res) {
                Toast.show(`호가 ${suggestedPrice.toLocaleString()}만원, 입찰 경쟁에 실패했습니다.`, 1000);
            } else {
                Toast.show(`호가 ${suggestedPrice.toLocaleString()}만원, 입찰 성공.`, 1000);
            }
        });
    }

    onStart({ price, users }: LiveDTO) {
        this.isProgress = () => true;
        this.setPrice((this.lastPrice * 10000), (price * 10000), 300);
        this.lastPrice = price;
        this.setBidButton(true, `호가 ${(price + BID_UNIT).toLocaleString()}만원 입찰`);
        this.fetchUsers(users, '경매가 시작되었습니다.');
    }
    onBid({ price, users }: LiveDTO) {
        this.setPrice((this.lastPrice * 10000), (price * 10000), 300);
        this.lastPrice = price;
        this.setBidButton(true, `호가 ${(price + BID_UNIT).toLocaleString()}만원 입찰`);
        this.fetchUsers(users, '새로운 입찰. 호가가 갱신되었습니다.');
    }
    onEnd({ price, users }: LiveDTO) {
        this.isEnded = () => true;
        this.setPrice((this.lastPrice * 10000), (price * 10000), 300);
        this.lastPrice = price;
        this.setBidButton(false, `경매가 종료되었습니다.`);
        this.fetchUsers(users, '경매가 종료되었습니다.');
        if (users.length) {
            this.checkWinnerAndBlast(users[0].userId);
        }
        disconnectSocketSession();
    }

    setBidButton(enable: boolean, msg: string) {
        const $bidButton = this.$target.querySelector('.auction-detail__bid-btn') as HTMLButtonElement;
        $bidButton.innerText = msg;
        $bidButton.disabled = !enable;
    }
    fetchUsers(users: LiveUser[], msg: string) {
        const $holder = this.$target.querySelector('.auction-detail__live-bids-container') as HTMLElement;
        $holder.innerHTML
            = '<div class="auction-detail__live-bids-container__live-bids" data-component="AnimatedText"></div>';

        const $liveBidAnnouncer = $holder.querySelector('[data-component="AnimatedText"]') as HTMLPreElement;
        const text = `$ 안내 : ${msg}\n` +
            users.map(({ userId, username, price, phoneNumber }, idx) =>
                `${idx + 1}위 : ${username}(${phoneNumber})님 입찰가 ${price.toLocaleString()}만원`
            ).join('\n');
        new AnimatedText($liveBidAnnouncer, { text });
    }
    setPrice(start: number, destination: number, speed: number) {
        const $holder = this.$target.querySelector('.auction-detail__price') as HTMLElement;
        $holder.innerHTML = 'KRW <div data-component="AnimatedNumber"></div>';

        const $animatedNumber = $holder.querySelector('[data-component="AnimatedNumber"]') as HTMLElement;
        new AnimatedNumber($animatedNumber, { start, destination, speed });

        if (this.isProgress() && !this.isEnded()) {
            $holder.classList.add('price--live');
        } else {
            $holder.classList.remove('price--live');
        }
    }

    isEnded() {
        const { endTime } = this.props;
        return (new Date(endTime).getTime()) < Date.now();
    }
    isProgress() {
        const { startTime } = this.props;
        return (new Date(startTime).getTime() < Date.now());
    }

    mounted() {
        const { auctionId, images, endPrice } = this.props;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, { imageUrls: images, width: 500, height: 280 });

        this.setPrice(0, endPrice * 10000, 1);
        this.lastPrice = endPrice * 10000;

        this.fetchUsers([], `${this.isEnded() ? '경매가 종료되었습니다.' : '경매 시작을 대기 하고 있습니다.'}`);

        if (!this.isEnded()) {  // TODO 버그
            this.timeoutRecursive();
            setOnStart(this.onStart.bind(this));
            setOnBid(this.onBid.bind(this));
            setOnEnd(this.onEnd.bind(this));
            requestSocketSession(auctionId);
        }
    }

    timeoutRecursive() {
        setTimeout(() => {
            if (!this.$target.isConnected) return;
            this.refreshTimer();

            if (this.isEnded()) return;
            this.timeoutRecursive();
        }, 1000);
    }

    refreshTimer() {
        const $timer = this.$target.querySelector('.auction-detail__timer') as HTMLElement;
        $timer.innerHTML = this.timerInfo();
    }

    timerInfo() {
        const { startTime, endTime } = this.props;
        if (this.isEnded())
            return '경매 종료됨';

        if (this.isProgress())
            return `경매 종료 <b>${deltaTimeToString((new Date(endTime)).getTime() - Date.now())}</b>전`;
        return `경매 시작 <b>${deltaTimeToString((new Date(startTime)).getTime() - Date.now())}</b>전`;
    }

    checkWinnerAndBlast(winnerId: number) {
        const user = GlobalStore.get().getState()[userStateSelector] as UserState;
        if (user && user.id && user.id === winnerId) {
            Emoji.blast();
            Toast.show('경매를 낙찰받았습니다! 축하합니다!', 1000);
        }
    }
}

export default AuctionDetail;
