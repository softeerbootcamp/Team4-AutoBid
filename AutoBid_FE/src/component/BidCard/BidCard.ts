import Component from "../../core/component";
import ImageSlider from "../ImageSlider/ImageSlider";
import "./bidcard.css";

class BidCard extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <div data-component="ImageSlider"></div>
        <div class="card-item__details-container">
            <span class="card-item__details__tags">#1인신조 #완전무사고 #여성운전자</span>
            <h4 class="card-item__details__title">현대 아반떼MD 프리미어 1.6 GDi</h4>
            <span class="card-item__details__info">2018 | 142,852km | 가솔린 | 대전</span>
            <h3 class="card-item__details__price"><b>1,090</b>만원</h3>
        </div>
        `;
    }

    mounted() {
        const $imageSlider = this.$target.querySelector('[data-component="ImageSlider"]') as HTMLElement;
        new ImageSlider($imageSlider, {
            imageUrls: ['https://picsum.photos/200', 'https://picsum.photos/200', 'https://picsum.photos/200'],
            width: 200, height: 140
        });
    }
}

export default BidCard;