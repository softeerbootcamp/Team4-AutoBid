import Component from "../../core/component";
import {requestParticipationList, requestMyList, requestUserCarList} from "../../api/mypage";
import {Auction} from "../../model/auction";
import "./mypage.css";
import {CarInfo} from "../../model/car";
import AuctionCard from "../AuctionCard/AuctionCard";
import auctionList from "../AuctionList/AuctionList";

class MyPage extends Component<any> {
    private participationList: Auction[] = [];
    private myCarList: Auction[] = [];
    private userCarList: CarInfo[] = [];

    initialize() {
        this.updateParticipationList();
        this.updateCarList();
        // this.updateUserCarList();
    }

    updateParticipationList() {
        requestParticipationList().then(auctionListData => {
            if (auctionListData) {
                const {auctionInfoList} = auctionListData;
                this.participationList = auctionInfoList;
                // console.log(`participation ${this.participationList}`);
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

    // updateUserCarList() {
    //     requestUserCarList().then(auctionListData => {
    //         if (auctionListData) {
    //             const {carList} = auctionListData;
    //             console.log(auctionListData);
    //             console.log(carList);
    //             this.userCarList = carList;
    //         } else {
    //             this.userCarList = [];
    //         }
    //         this.render();
    //     });
    // }

    template(): InnerHTML["innerHTML"] {
        return `
    <div class="my-page__section-holder my-page__main__my-car-list__holder">
        <h3>참여 경매</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__my-car-list">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
                ${this.setCards(this.participationList, "participationCard")}
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
    <div class="my-page__section-holder my-page__main__participating-bids__holder">
        <h3>등록 경매</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__participating-bids">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
                ${this.setCards(this.myCarList, "myCard")}
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
    <div class="my-page__section-holder my-page__main__registered-bids__holder">
        <h3>내 차 목록</h3>
        <hr>
        <div class="my-page__card-slider my-page__main__registered-bids">
            <button class="my-page__swap-button-left"><i class="fas fa-chevron-left"></i></button>
            
            <button class="my-page__swap-button-right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
        `
    }

    // ${this.setCards(this.myCarList)}
    // ${this.setCards(this.userCarList)}

    setCards(auctionList: Auction[], componentName: String): InnerHTML['innerHTML'] {
        if (auctionList && auctionList.length) {
            return `
                <div class="my-page__card-list__holder">
                    ${auctionList.map(() => {
                        return `<div data-component="${componentName}"></div>`
                    }).join('')}
                </div>
            `
        }
        return `
            <div class="no-content">조회된 경매 없음</div>
        `
    }

    mounted() {
        const participationList = this.participationList;
        const $participationCards = this.$target.querySelectorAll('[data-component="participationCard"]');
        $participationCards.forEach(($participationCard, idx) => {
            new AuctionCard($participationCard as HTMLElement, {
                auction: participationList[idx],
                onClick: () => {
                    console.log(idx);
                }
            });
        });
        const myCarList = this.myCarList;
        const $myCarCards = this.$target.querySelectorAll('[data-component="myCard"]');
        $myCarCards.forEach(($myCarCards, idx) => {
            new AuctionCard($myCarCards as HTMLElement, {
                auction: myCarList[idx],
                onClick: () => {
                    console.log(idx);
                }
            });
        });
    }
}

export default MyPage;