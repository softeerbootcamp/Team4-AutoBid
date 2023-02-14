import Component from "../../core/component";
import {Auction, AuctionStatus} from "../../model/auction";
import {CarInfo, getCarTypeName} from "../../model/car";
import ImageSlider from "../ImageSlider/ImageSlider";
import "./auctiondetail.css";
import {deltaTimeToString} from "../../core/util";

const getInfoStr = ({ distance, type, sellName }: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;

class AuctionDetail extends Component<any, Auction> {
    template(): InnerHTML["innerHTML"] {
        const { status, title, carInfo, endPrice } = this.props;
        return `
        <div class="container--left">
            <div data-component="ImageSlider"></div>
            <h4 class="auction-detail__timer">계산중</h4>
        </div>
        <div class="container--right">
            <h1 class="auction-detail__title">${title}</h1>
            <h2 class="auction-detail__car-info">${getInfoStr(carInfo)}</h2>
            <h1 class="auction-detail__price ${status === AuctionStatus.PROGRESS ? 'price--live' : ''}">
                KRW ${(endPrice * 10000).toLocaleString()}
            </h1>
            <div class="auction-detail__live-bids-container">
                <pre class="auction-detail__live-bids-container__live-bids">$ 안내 : 접속 중...</pre>
            </div>
            <button class="auction-detail__bid-btn" disabled>갱신 중...</button>
        </div>
        `;
    }

    mounted() {
        const { images, status } = this.props;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, { imageUrls: images, width: 500, height: 280 });

        this.timeoutRecursive();
    }

    timeoutRecursive() {
        setTimeout(() => {
            if (!this.$target.isConnected) return;
            this.refreshTimer();
            this.timeoutRecursive();
        }, 1000);
    }

    refreshTimer() {
        const $timer = this.$target.querySelector('.auction-detail__timer') as HTMLElement;
        $timer.innerHTML = this.timerInfo();
    }

    timerInfo() {
        const { status, startTime, endTime } = this.props;
        switch (status) {
            case AuctionStatus.BEFORE:
                return `경매 시작 <b>${deltaTimeToString((new Date(startTime)).getTime() - Date.now())}</b>전`;
            case AuctionStatus.COMPLETE:
                return '경매 종료됨';
            case AuctionStatus.PROGRESS:
                return `종료 <b>${deltaTimeToString((new Date(endTime)).getTime() - Date.now())}</b>전`;
            default:
                return '계산 중'
        }
    }
}

export default AuctionDetail;