import Component from "../../core/component";
import {requestParticipationList, requestMyList, requestUserCarList} from "../../api/mypage";
import {Auction} from "../../model/auction";

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
            
        `
    }

    mounted() {
        super.mounted();
    }
}