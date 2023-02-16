import Component from "../../core/component";
import {requestParticipationList, requestMyList, requestUserCarList} from "../../api/mypage";
import {Auction} from "../../model/auction";
import AuctionList from "../AuctionList/AuctionList";

class MyPage extends Component<any> {
    private participationList: Auction[] = [];
    private myCarList: Auction[] = [];
    private userCarList: Auction[] = [];

    initialize() {
        this.updateParticipationList();
        this.updateCarList();
        this.updateUserCarList();
    }

    updateParticipationList() {
        requestParticipationList().then(auctionListData => {
            if (auctionListData) {
                const {auctionInfoList} = auctionListData;
                this.participationList = auctionInfoList;
            } else {
                this.participationList = [];
            }
            this.render();
        });
    }

    updateCarList() {
        requestMyList().then(auctionListData => {
            if (auctionListData) {
                const {auctionInfoList} = auctionListData;
                this.myCarList = auctionInfoList;
            } else {
                this.myCarList = [];
            }
            this.render();
        });
    }

    updateUserCarList() {
        requestMyList().then(auctionListData => {
            if (auctionListData) {
                const {auctionInfoList} = auctionListData;
                this.userCarList = auctionInfoList;
            } else {
                this.userCarList = [];
            }
            this.render();
        });
    }

    template(): InnerHTML["innerHTML"] {
        return `
    <div class="my-page__section-holder my-page__main__my-car-list__holder">
        <h3>참여 경매</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__my-car-list">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
                ${this.setCards(this.participationList)}
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
    <div class="my-page__section-holder my-page__main__participating-bids__holder">
        <h3>등록 경매</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__participating-bids">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
                ${this.setCards(this.myCarList)}
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
    <div class="my-page__section-holder my-page__main__registered-bids__holder">
        <h3>내 차 목록</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__registered-bids">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
                ${this.setCards(this.userCarList)}
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
        `
    }

    setCards(auctionList: Auction[]): InnerHTML['innerHTML'] {
        if (auctionList && auctionList.length) {
            return `
                <div class="my-page__card-list__holder">
                    ${auctionList.map(() => {
                        `<div data-component="AuctionCard"></div>`
                    }).join('')}
                </div>
            `
        }
        return `
            <div class="no-content">조회된 경매 없음</div>
        `
    }

    mounted() {
        super.mounted();
    }
}