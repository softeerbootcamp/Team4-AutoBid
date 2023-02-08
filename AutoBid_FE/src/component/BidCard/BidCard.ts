import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import "./bidcard.css";

export type Bid = { images: string[], tags: string[], title: string, carInfo: CarInfo, price: number };
export type CarInfo = { year: number, distance: number, type: string, region: string };

const getTagStr = (tags: string[]) => tags.map(tag => `#${tag}`).join(' ');
const getInfoStr = ({ year, distance, type, region }: CarInfo) =>
    `${year} | ${distance.toLocaleString()}km | ${type} | ${region}`;

class BidCard extends Component<any, Bid> {
    template(): InnerHTML["innerHTML"] {
        const { tags, title, carInfo, price } = this.props;
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
        const { images } = this.props;
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, { imageUrls: images, width: 250, height: 140 });
    }
}

export default BidCard;