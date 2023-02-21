import Component from "../../core/component";
import {AuctionListDTO} from "../../model/auction";
import {CarListDTO} from "../../model/car";
import "./mypage.css";
import {requestMyAuctionList, requestMyCarList, requestMyParticipationAuctionList} from "../../api/my";
import AuctionCard from "../AuctionCard/AuctionCard";
import CarCard from "../CarCard/CarCard";

class MyPage extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `    
        <div class="my-page__section-holder">
            <h3 class="my-page__section-title">참여 경매</h3>
            <div class="my-page__card-slider my-page__main__participating-bids empty"></div>
        </div>
        <div class="my-page__section-holder">
            <h3 class="my-page__section-title">등록 경매</h3>
            <div class="my-page__card-slider my-page__main__my-auction-list empty"></div>
        </div>
        <div class="my-page__section-holder">
            <h3 class="my-page__section-title">내 차 목록</h3>
            <div class="my-page__card-slider my-page__main__my-car-list empty"></div>
        </div>
        `
    }

    mounted() {  // TODO test 뺄 것
        const $participatingList = this.$target.querySelector('.my-page__main__participating-bids') as HTMLElement;
        requestMyParticipationAuctionList(true).then(auctionListDTO => {
            this.mountAuctionList($participatingList, auctionListDTO);
        });

        const $myAuctionList = this.$target.querySelector('.my-page__main__my-auction-list') as HTMLElement;
        requestMyAuctionList(true).then(auctionListDTO => {
            this.mountAuctionList($myAuctionList, auctionListDTO);
        });

        const $myCarList = this.$target.querySelector('.my-page__main__my-car-list') as HTMLElement;
        requestMyCarList(true).then(carListDTO => {
            this.mountCarList($myCarList, carListDTO);
        });
    }

    mountAuctionList($holder: HTMLElement, auctionListDTO: AuctionListDTO|null) {
        if (!auctionListDTO || !auctionListDTO.auctionInfoList.length) {
            $holder.classList.add('empty');
            return;
        }
        $holder.classList.remove('empty');

        const auctionList = auctionListDTO.auctionInfoList;

        $holder.innerHTML = `${auctionList.map(() =>
            '<div data-component="AuctionCard"></div>'
        ).join('')}`;

        const $auctionCards = $holder.querySelectorAll('[data-component="AuctionCard"]');
        $auctionCards.forEach(($auctionCard, idx) => {
            new AuctionCard($auctionCard as HTMLElement, { auction: auctionList[idx] });
        });
    }

    mountCarList($holder: HTMLElement, carListDTO: CarListDTO|null) {
        if (!carListDTO || !carListDTO.length) {
            $holder.classList.add('empty');
            return;
        }
        $holder.classList.remove('empty');

        $holder.innerHTML = `${carListDTO.map(() =>
            '<div data-component="CarCard"></div>'    
        ).join('')}`

        const $carCards = $holder.querySelectorAll('[data-component="CarCard"]');
        $carCards.forEach(($carCard, idx) => {
            new CarCard($carCard as HTMLElement, { carInfo: carListDTO[idx] });
        })
    }
}

export default MyPage;