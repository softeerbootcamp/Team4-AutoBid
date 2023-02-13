import Component from "../../core/component";
import QueryCarTypeGroup from "../QueryCarTypeGroup/QueryCarTypeGroup";
import DoubleRangeSlider from "../DoubleRangeSlider/DoubleRangeSlider";
import {requestAuctionStatistic} from "../../api/statistic";
import {Histogram} from "../../model/statistic";
import {setRange} from "../../store/query";
import "./querysidebar.css"
import AnimatedNumber from "../AnimatedNumber/AnimatedNumber";

class QuerySidebar extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <div class="query-side-bar__n-sold">
            오늘 <div data-component="AnimatedNumber"></div>대가 판매되었습니다
        </div>
        <h4 class="query-side-bar__title">원하는 차량을 선택하세요</h4>
        <div data-component="QueryCarTypeGroup"></div>
        <button class="query-side-bar__new-auction-btn">경매 등록</button>
        <div class="query-side-bar__set-fund">
            <h4 class="query-side-bar__set-fund__title">예산이 어떻게 되시나요?</h4>
            <span class="query-side-bar__set-fund__val"></span>
            <span class="query-side-bar__set-fund__avg"></span>
            <div class="query-side-bar__set-fund__hist"></div>
            <div data-component="DoubleRangeSlider"></div>
            <div class="query-side-bar__set-fund__labels">
                <span class="set-fund__labels--left">0원</span>
                <span class="set-fund__labels--right">1억원</span>
            </div>
        </div>
        `;
    }

    mounted() {
        const $queryCarTypeGroup = this.$target.querySelector('[data-component="QueryCarTypeGroup"]') as HTMLElement;
        new QueryCarTypeGroup($queryCarTypeGroup, {});
        this.fetchStatistic();
    }

    fetchStatistic() {
        requestAuctionStatistic().then(({ auctionStatistic }) => {
            this.updateNSold(auctionStatistic.nSold);
            this.updateFundVal(auctionStatistic.minPrice, auctionStatistic.maxPrice);
            this.updateHistogram(auctionStatistic.histogram);
            this.mountDoubleRangeSlider(auctionStatistic.minPrice, auctionStatistic.maxPrice);
        });
    }

    markHistogramSelected(left: number, right: number, min: number, max: number) {
        const $histBars = this.$target.querySelectorAll('.query-side-bar__set-fund__hist__bar');
        const histSize = $histBars.length;
        const leftRegularized = Math.ceil(((left - min) / (max - min)) * histSize);
        const rightRegularized = Math.floor(((right - min) / (max - min)) * histSize);
        $histBars.forEach((bar, idx) => {
            if (leftRegularized <= idx && idx < rightRegularized) {
                bar.classList.add('set-fund__hist__bar--selected');
            } else {
                bar.classList.remove('set-fund__hist__bar--selected');
            }
        });
    }

    onSliderDetermined(left: number, right: number) {
        const minPriceInt = Math.floor(left);
        const maxPriceInt = Math.ceil(right);
        setRange(minPriceInt, maxPriceInt);
    }

    updateNSold(nSold: number) {
        const $animatedNumber = this.$target.querySelector('[data-component="AnimatedNumber"]') as HTMLElement;
        new AnimatedNumber($animatedNumber, { start: 0, destination: nSold + 1000, speed: 500 });
    }
    updateFundVal(min: number, max: number) {
        const minPriceInt = Math.floor(min);
        const maxPriceInt = Math.ceil(max);
        const $fundVal = this.$target.querySelector('.query-side-bar__set-fund__val') as HTMLElement;
        const $fundAvg = this.$target.querySelector('.query-side-bar__set-fund__avg') as HTMLElement;
        $fundVal.innerHTML = `<b>${minPriceInt.toLocaleString()}만원</b>에서 <b>${maxPriceInt.toLocaleString()}만원</b>까지 보고싶어요`;
        const avg = Math.round((min + max) / 2);
        $fundAvg.innerHTML = `차량 평균값 <b>${avg.toLocaleString()}만원</b>`
    }
    updateHistogram({ contents }: Histogram) {
        const $histogram = this.$target.querySelector('.query-side-bar__set-fund__hist') as HTMLElement;
        const max = Math.max(...contents);
        $histogram.innerHTML = `${contents.map(value => `
        <span class="query-side-bar__set-fund__hist__bar set-fund__hist__bar--selected" 
            style="height: ${Math.round(value / max * 100)}%"></span>
        `).join('')}`;
    }
    mountDoubleRangeSlider(min: number, max: number) {
        const $doubleRangeSlider = this.$target.querySelector('[data-component="DoubleRangeSlider"]') as HTMLElement;
        new DoubleRangeSlider($doubleRangeSlider, {
            min, max, left: min, right: max,
            onUp: (left, right) => {
                this.onSliderDetermined(left, right);
            },
            onMoved: (left, right, min, max) => {
                this.markHistogramSelected(left, right, min, max);
                this.updateFundVal(left, right);
            }
        });
        const $labelLeft = this.$target.querySelector('.set-fund__labels--left') as HTMLElement;
        const $labelRight = this.$target.querySelector('.set-fund__labels--right') as HTMLElement;
        const minPriceInt = Math.floor(min);
        const maxPriceInt = Math.ceil(max);
        $labelLeft.innerText = `${minPriceInt.toLocaleString()}만원`;
        $labelRight.innerText = `${maxPriceInt.toLocaleString()}만원`;
    }
}

export default QuerySidebar;