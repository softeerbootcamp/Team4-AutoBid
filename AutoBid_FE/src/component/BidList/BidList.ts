import Component from "../../core/component";
import BidCard, {Bid} from "../BidCard/BidCard";
import {BidStatus, QUERY_INITIAL, QueryState, queryStateSelector, selectStatus} from "../../store/query";
import {requestBidList} from "../../api/bid";
import "./bidlist.css"


class BidList extends Component<QueryState> {
    private bidList: Bid[] = [];

    stateSelector(globalState: any): QueryState | undefined {
        return globalState[queryStateSelector];
    }

    onStateChanged(prevLocalState: QueryState) {
        const queryState = this.state || QUERY_INITIAL;
        this.updateBidList(queryState);
    }

    template(): InnerHTML["innerHTML"] {
        const queryState = this.state || QUERY_INITIAL;
        return `
        <div class="header-button-container">
            <button class="header-button-container__header-button bid-status-all
                ${queryState.bidStatus === BidStatus.ALL ? ' header-button--selected' : ''}">전체 경매</button>
            <button class="header-button-container__header-button bid-status-progress
                ${queryState.bidStatus === BidStatus.PROGRESS ? ' header-button--selected' : ''}">진행 중 경매</button>
            <button class="header-button-container__header-button bid-status-before
                ${queryState.bidStatus === BidStatus.BEFORE ? ' header-button--selected' : ''}">진행 전 경매</button>
            <button class="header-button-container__header-button bid-status-complete
                ${queryState.bidStatus === BidStatus.COMPLETE ? ' header-button--selected' : ''}">종료된 경매</button>
        </div>
        ${this.bidList?.map(() => `
        <div data-component="BidCard"></div>
        `).join('')}
        `
    }

    initialize() {
        const queryState = this.state || QUERY_INITIAL;
        this.updateBidList(queryState);
        this.addEvent('click', '.bid-status-all', () => { selectStatus(BidStatus.ALL) });
        this.addEvent('click', '.bid-status-progress', () => { selectStatus(BidStatus.PROGRESS) });
        this.addEvent('click', '.bid-status-before', () => { selectStatus(BidStatus.BEFORE) });
        this.addEvent('click', '.bid-status-complete', () => { selectStatus(BidStatus.COMPLETE) });
    }

    mounted() {
        const bidList = this.bidList;
        const $bidCards = this.$target.querySelectorAll('[data-component="BidCard"]');
        $bidCards.forEach(($bidCard, idx) => {
            new BidCard($bidCard as HTMLElement, {
                bid: bidList[idx],
                onClick: () => {
                    console.log(idx);
                }
            });
        });
    }

    updateBidList(query: QueryState) {
        requestBidList(query).then(bidList => {
            this.bidList = bidList;
            this.render();
        });
    }
}

export default BidList;