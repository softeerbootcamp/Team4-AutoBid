import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import {CarInfo, getCarTypeName} from "../../model/car";
import {Auction, AuctionStatus} from "../../model/auction";
import "./auctioncard.css";
import {deltaTimeToString} from "../../core/util";


const getInfoStr = ({ distance, type, sellName }: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;

class AuctionCard extends Component<any, { auction: Auction, onClick: (arg: any) => any }> {
    template(): InnerHTML["innerHTML"] {
        const { title, carInfo, status } = this.props.auction;
        return `
        ${status === AuctionStatus.PROGRESS ? `<div class="ribbon"><span>입찰 진행 중</span></div>` : ''}
        ${status === AuctionStatus.COMPLETE ? `<div class="sold-out"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--! Font Awesome Pro 6.3.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M256 512c141.4 0 256-114.6 256-256S397.4 0 256 0S0 114.6 0 256S114.6 512 256 512zM369 209L241 337c-9.4 9.4-24.6 9.4-33.9 0l-64-64c-9.4-9.4-9.4-24.6 0-33.9s24.6-9.4 33.9 0l47 47L335 175c9.4-9.4 24.6-9.4 33.9 0s9.4 24.6 0 33.9z"/></svg></div>` : ''}
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
            case AuctionStatus.COMPLETE:
                return '경매 종료됨';
            case AuctionStatus.PROGRESS:
                return `종료 <b>${deltaTimeToString((new Date(endTime)).getTime() - Date.now())}</b>전`;
            default:
                return '계산 중'
        }
    }

    priceInfo() {
        const { status, startPrice, endPrice, currPrice } = this.props.auction;
        switch (status) {
            case AuctionStatus.BEFORE:
                return `
                <h3 class="card-item__details__price status--before">
                    <em>시작가</em><b>${startPrice.toLocaleString()}</b>만원
                </h3>
                `;
            case AuctionStatus.COMPLETE:
                return `
                <h3 class="card-item__details__price status--complete">
                    <em>낙찰가</em><b>${endPrice.toLocaleString()}</b>만원
                </h3>
                `;
            case AuctionStatus.PROGRESS:
                return `
                <h3 class="card-item__details__price status--progress">
                    <em>입찰가</em><b>${currPrice.toLocaleString()}</b>만원
                </h3>
                `;
        }
    }

    initialize() {
        const { onClick } = this.props;
        this.addEvent('click', '.card-item__details-container', onClick);
    }
}

export default AuctionCard;