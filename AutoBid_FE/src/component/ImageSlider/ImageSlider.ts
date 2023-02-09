import Component from "../../core/component";
import './imageslider.css';

export type ImageURL = string;

class ImageSlider extends Component<any, { imageUrls: ImageURL[], width: number, height: number }> {
    template(): InnerHTML["innerHTML"] {
        const {imageUrls, width} = this.props;
        return `
        <div class="swap-button-container">
            <button class="swap-button-left">
                <i class="fas fa-circle-chevron-left"></i>
            </button>
            <button class="swap-button-right">
                <i class="fas fa-circle-chevron-right"></i>
            </button>
        </div>
        <div class="img-container">
            <div class="img-container__scrollable">
            ${imageUrls.map(url => `
                <img class="img-container__img" loading="lazy" src="${url}" width="${width}" alt="">
            `).join('')}            
            </div>
        </div>`;
    }

    initialize() {
        const {width, height} = this.props;
        this.$target.style.width = `${width}px`;
        this.$target.style.height = `${height}px`;
        this.addEvent('click', '.swap-button-left', this.swapImage.bind(this, 'left'));
        this.addEvent('click', '.swap-button-right', this.swapImage.bind(this, 'right'));
    }

    private scrollDiff = 0;

    swapImage(direction: 'left' | 'right') {
        const $imgContainer = this.$target.querySelector('.img-container') as HTMLElement;
        const imageWidth = this.props.width
        if (direction === 'left') {
            if (this.scrollDiff >= imageWidth)
                this.scrollDiff -= imageWidth;
        } else if (this.scrollDiff < $imgContainer.scrollWidth - imageWidth) {
            this.scrollDiff += imageWidth;
        }
        $imgContainer.scrollLeft = this.scrollDiff;
    }
}

export default ImageSlider;