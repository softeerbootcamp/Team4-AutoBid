import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import {CarInfo, getCarTypeName} from "../../model/car";
import {Auction, AuctionStatus} from "../../model/auction";
import {deltaTimeToString} from "../../core/util";
import AnimatedNumber from "../AnimatedNumber/AnimatedNumber";
import "./auctioncard.css";


const getInfoStr = ({ distance, type, sellName }: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;


class AuctionCard extends Component<any, { auction: Auction, onClick: (arg: any) => any }> {
    template(): InnerHTML["innerHTML"] {
        const { title, carInfo, status } = this.props.auction;
        // Code for hammer (completed bid background)
        // ${status === AuctionStatus.COMPLETED ? `<!--<div class="sold-out"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">&lt;!&ndash;! Font Awesome Pro 6.3.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. &ndash;&gt;<path d="M318.6 9.4c-12.5-12.5-32.8-12.5-45.3 0l-120 120c-12.5 12.5-12.5 32.8 0 45.3l16 16c12.5 12.5 32.8 12.5 45.3 0l4-4L325.4 293.4l-4 4c-12.5 12.5-12.5 32.8 0 45.3l16 16c12.5 12.5 32.8 12.5 45.3 0l120-120c12.5-12.5 12.5-32.8 0-45.3l-16-16c-12.5-12.5-32.8-12.5-45.3 0l-4 4L330.6 74.6l4-4c12.5-12.5 12.5-32.8 0-45.3l-16-16zm-152 288c-12.5-12.5-32.8-12.5-45.3 0l-112 112c-12.5 12.5-12.5 32.8 0 45.3l48 48c12.5 12.5 32.8 12.5 45.3 0l112-112c12.5-12.5 12.5-32.8 0-45.3l-1.4-1.4L272 285.3 226.7 240 168 298.7l-1.4-1.4z"/></svg></div>-->` : ''}
        return `
        ${status === AuctionStatus.PROGRESS ? `<div class="ribbon"><span>입찰 진행 중</span></div>` : ''}
        ${status === AuctionStatus.COMPLETED ? `<div class="ribbon red-ribbon"><span>입찰 완료</span></div>` : ''}
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
        const { images } = this.props.auction;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, { imageUrls: images, width: 250, height: 140 });

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
        const { status, startTime, endTime } = this.props.auction;
        switch (status) {
            case AuctionStatus.BEFORE:
                return `시작 <b>${deltaTimeToString((new Date(startTime)).getTime() - Date.now())}</b>전`;
            case AuctionStatus.COMPLETED:
                return '경매 종료됨';
            case AuctionStatus.PROGRESS:
                return `종료 <b>${deltaTimeToString((new Date(endTime)).getTime() - Date.now())}</b>전`;
            default:
                return '계산 중'
        }
    }

    priceInfo() {
        const { status, startPrice, endPrice } = this.props.auction;
        switch (status) {
            case AuctionStatus.BEFORE:
                return `
                <div class="card-item__details__price status--before">
                    <em>시작가</em><b><div data-component="AnimatedNumber" data-price="${startPrice}"></div></b>만원
                </div>
                `;
            case AuctionStatus.COMPLETED:
                return `
                <h3 class="card-item__details__price status--complete">
                    <em>낙찰가</em><b><div data-component="AnimatedNumber" data-price="${endPrice}"></div></b>만원
                </h3>
                `;
            case AuctionStatus.PROGRESS:
                return `
                <h3 class="card-item__details__price status--progress">
                    <em>입찰가</em><b><div data-component="AnimatedNumber" data-price="${endPrice}"></div></b>만원
                </h3>
                `;
        }
    }

    animatePriceNumber() {
        const $animatedNumber = this.$target.querySelector('[data-component="AnimatedNumber"]') as HTMLElement;
        const destination = parseInt($animatedNumber.dataset.price as string);
        new AnimatedNumber($animatedNumber, { start: 0, destination, speed: 300 });
    }

    initialize() {
        const { onClick } = this.props;
        this.addEvent('click', '.card-item__details-container', onClick);
    }
}

export default AuctionCard;