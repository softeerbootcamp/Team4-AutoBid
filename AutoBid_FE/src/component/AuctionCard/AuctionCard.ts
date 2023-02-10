import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import {CarInfo, getCarTypeName} from "../../model/car";
import {Auction} from "../../model/auction";
import "./auctioncard.css";


const getTagStr = (tags: string[]) => tags.map(tag => `#${tag}`).join(' ');
const getInfoStr = ({ distance, type, sellName }: CarInfo) =>
    `${sellName} | ${distance.toLocaleString()}km | ${getCarTypeName(type)}`;

class AuctionCard extends Component<any, { auction: Auction, onClick: (arg: any) => any }> {
    template(): InnerHTML["innerHTML"] {
        const { tags, title, carInfo, price } = this.props.auction;
        return `
        <div class="card-item__img-slider" data-component="ImageSlider"></div>
        <div class="card-item__details-container">
            <span class="card-item__details__tags">${getTagStr(tags)}</span>
            <h4 class="card-item__details__title">${title}</h4>
            <span class="card-item__details__info">${getInfoStr(carInfo)}</span>
            <h3 class="card-item__details__price"><b>${price.toLocaleString()}</b>만원</h3>
        </div>
        `;
    }

    mounted() {
        const { images } = this.props.auction;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, { imageUrls: images, width: 250, height: 140 });
    }

    initialize() {
        const { onClick } = this.props;
        this.addEvent('click', '.card-item__details-container', onClick);
    }
}

export default AuctionCard;