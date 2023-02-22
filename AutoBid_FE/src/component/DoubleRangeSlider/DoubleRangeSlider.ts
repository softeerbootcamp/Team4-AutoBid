import Component from "../../core/component";
import './doublerangeslider.css'

const THUMB_WIDTH = 16;

class DoubleRangeSlider extends Component<any, {
        min: number, max: number, left: number, right: number,
        onMoved: (left: number, right: number, min: number, max: number) => any,
        onUp: (left: number, right: number, min: number, max: number) => any }> {

    template(): InnerHTML["innerHTML"] {
        return `
        <div class="double-range-slider__thumb thumb--left"></div>
        <div class="double-range-slider__thumb thumb--right"></div>
        <div class="double-range-slider__range"></div>
        <div class="double-range-slider__shadow"></div>
        `
    }

    private currLeftPos: number = this.getTranslatedXFromVal(this.props.left);
    private currRightPos: number = this.getTranslatedXFromVal(this.props.right);
    private $scrollingThumb: HTMLElement|null = null;

    initialize() {
        const { min, max, onUp, onMoved } = this.props;

        this.addEvent('mousedown', '.double-range-slider__thumb', ({ target }) => {
            this.$scrollingThumb = (target as Element).closest('.double-range-slider__thumb') as HTMLElement;
            this.shadowUp();
        });
        this.addEvent('mouseup', '*', () => {
            this.$scrollingThumb = null;
            this.shadowDown();
            onUp(this.getValFromTranslatedX(this.currLeftPos), this.getValFromTranslatedX(this.currRightPos), min, max);
        });
        this.addEvent('mousemove', '*', e => {
            if (!this.$scrollingThumb) return;
            const { clientX } = e as MouseEvent;
            const newThumbPos = this.regularizeMouseX(clientX);
            if (this.$scrollingThumb.closest('.thumb--left')) {
                if (newThumbPos > (this.currRightPos - THUMB_WIDTH)) return;
                this.currLeftPos = newThumbPos;
            } else if (this.$scrollingThumb.closest('.thumb--right')) {
                if (newThumbPos < (this.currLeftPos + THUMB_WIDTH)) return;
                this.currRightPos = newThumbPos;
            }
            this.fitThumbs();
            this.fitRange();
            onMoved(this.getValFromTranslatedX(this.currLeftPos), this.getValFromTranslatedX(this.currRightPos), min, max);
        });
    }

    mounted() {
        const { left, right } = this.props;
        this.currLeftPos = this.getTranslatedXFromVal(left);
        this.currRightPos = this.getTranslatedXFromVal(right);
        this.fitRange();
        this.fitThumbs();
        this.shadowDown();
    }

    getValFromTranslatedX(translatedX: number) {
        const { width } = this.$target.getBoundingClientRect();
        const { min, max } = this.props;
        const limit = width - THUMB_WIDTH;
        return min + ((max - min) * (translatedX / limit));
    }

    getTranslatedXFromVal(val: number) {
        const { min, max } = this.props;
        const fullRange = this.$target.clientWidth - THUMB_WIDTH;
        const ratio = ((val - min) / (max - min));
        return fullRange * ratio;
    }

    regularizeMouseX(viewportX: number) {
        const { left, width } = this.$target.getBoundingClientRect();
        const limit = width - THUMB_WIDTH;
        const sliderBasedX = viewportX - left;
        const newThumbCenterX = sliderBasedX - (THUMB_WIDTH / 2);
        if (newThumbCenterX < 0) return 0;
        if (newThumbCenterX > limit) return limit
        return newThumbCenterX;
    }

    fitRange() {
        const $range = this.$target.querySelector('.double-range-slider__range') as HTMLElement;
        $range.style.width = `${this.currRightPos - this.currLeftPos + THUMB_WIDTH}px`;
        $range.style.transform = `translateX(${this.currLeftPos}px)`;
    }

    fitThumbs() {
        const $thumbLeft = this.$target.querySelector('.thumb--left') as HTMLElement;
        const $thumbRight = this.$target.querySelector('.thumb--right') as HTMLElement;
        $thumbLeft.style.transform = `translateX(${this.currLeftPos}px)`;
        $thumbRight.style.transform = `translateX(${this.currRightPos}px)`;
    }

    shadowUp() {
        const $shadow = this.$target.querySelector('.double-range-slider__shadow') as HTMLElement;
        $shadow.style.pointerEvents = '';
        $shadow.style.width = '100vw';
        $shadow.style.height = '100vh';
    }

    shadowDown() {
        const $shadow = this.$target.querySelector('.double-range-slider__shadow') as HTMLElement;
        $shadow.style.pointerEvents = 'none';
        $shadow.style.width = '0';
        $shadow.style.height = '0';
    }
}

export default DoubleRangeSlider;