import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import {CarInfo, getCarTypeName} from "../../model/car";
import {Auction} from "../../model/auction";
import {deltaTimeToString} from "../../core/util";
import AnimatedNumber from "../AnimatedNumber/AnimatedNumber";
import "./auctioncard.css";


const getInfoStr = ({distance, type, sellName}: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;


class AuctionCard extends Component<any, { auction: Auction, onClick: (arg: any) => any }> {
    template(): InnerHTML["innerHTML"] {
        const {title, carInfo, startTime, endTime} = this.props.auction;
        const $startTime = new Date(startTime).getTime();
        const $endTime = new Date(endTime).getTime();
        const now = Date.now();
        return `
        ${$startTime <= now && now < $endTime ? `<div class="ribbon"><span>입찰 진행 중</span></div>` : ''}
        ${$endTime <= now ? `<div class="ribbon red-ribbon"><span>입찰 완료</span></div>` : ''}
        <div class="card-item__img-slider" data-component="ImageSlider"></div>
        <div class="card-item__details-container">
            <h4 class="card-item__details__title">${title}</h4>
            <span class="card-item__details__info">${getInfoStr(carInfo)}</span>
            ${this.priceInfo()}
            <h4 class="timer">계산 중</h4>
        </div>
        `;
    }

    mounted() {
        const {images} = this.props.auction;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, {imageUrls: images, width: 250, height: 140});

        this.timeoutRecursive();
        this.animatePriceNumber();
    }

    timeoutRecursive() {
        setTimeout(() => {
            if (!this.$target.isConnected) return;
            const $timer = this.$target.querySelector('.timer') as HTMLElement;
            $timer.innerHTML = this.timerInfo();
            this.timeoutRecursive();
        }, 1000);
    }

    timerInfo() {
        const {startTime, endTime} = this.props.auction;
        const $startTime = new Date(startTime).getTime();
        const $endTime = new Date(endTime).getTime();
        const now = Date.now();

        // console.log(`
        // start: ${$startTime},
        // now: ${Date.now()},
        // end: ${$endTime},
        // `);

        if (now <= $startTime) {
            return `시작 <b>${deltaTimeToString($startTime - now)}</b>전`;
        } else if ($startTime <= now && now < $endTime) {
            return `종료 <b>${deltaTimeToString($endTime - now)}</b>전`;
        } else if ($endTime <= now) {
            return '경매 종료됨';
        } else {
            return '계산 중';
        }
    }

    priceInfo() {
        const {startPrice, endPrice, startTime, endTime} = this.props.auction;
        const $startTime = new Date(startTime).getTime();
        const $endTime = new Date(endTime).getTime();
        const now = Date.now();

        if (now <= $startTime) {
            return `
                <div class="card-item__details__price status--before">
                    <em>시작가</em><b><div data-component="AnimatedNumber" data-price="${startPrice}"></div></b>만원
                </div>
                `;
        } else if ($startTime <= now && now < $endTime) {
            return `
                <h3 class="card-item__details__price status--progress">
                    <em>입찰가</em><b><div data-component="AnimatedNumber" data-price="${endPrice}"></div></b>만원
                </h3>
                `;
        } else if ($endTime <= now) {
            return `
                <h3 class="card-item__details__price status--complete">
                    <em>낙찰가</em><b><div data-component="AnimatedNumber" data-price="${endPrice}"></div></b>만원
                </h3>
                `;
        } else {
            console.log("😱😱😱ERROR!!!!");
        }
    }

    animatePriceNumber() {
        const $animatedNumber = this.$target.querySelector('[data-component="AnimatedNumber"]') as HTMLElement;
        const destination = parseInt($animatedNumber.dataset.price as string);
        new AnimatedNumber($animatedNumber, {start: 0, destination, speed: 300});
    }

    initialize() {
        const {onClick} = this.props;
        this.addEvent('click', '.card-item__details-container', onClick);
    }
}

export default AuctionCard;