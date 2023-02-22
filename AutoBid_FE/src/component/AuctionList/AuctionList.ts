import Component from "../../core/component";
import AuctionCard from "../AuctionCard/AuctionCard";
import {QUERY_INITIAL, queryStateSelector, selectStatus} from "../../store/query";
import {AuctionQuery} from "../../model/query";
import {Auction, AuctionStatus} from "../../model/auction";
import {requestAuctionList} from "../../api/auction";
import "./auctionlist.css"
import {ARTICLE_PER_PAGE} from "../../core/util";
import PagingView from "../PagingView/PagingView";

class AuctionList extends Component<AuctionQuery> {
    private auctionList: Auction[] = [];
    private pages: number = 0;

    stateSelector(globalState: any): AuctionQuery | undefined {
        return globalState[queryStateSelector];
    }

    onStateChanged(prevLocalState: AuctionQuery) {
        const query = this.state || QUERY_INITIAL;
        this.updateBidList(query);
    }

    template(): InnerHTML["innerHTML"] {
        const query = this.state || QUERY_INITIAL;
        return `
        <div class="header-button-container">
            <button class="header-button-container__header-button bid-status-all
                ${query.auctionStatus === AuctionStatus.ALL ? ' header-button--selected' : ''}">
                전체 경매
            </button>
            <button class="header-button-container__header-button bid-status-progress
                ${query.auctionStatus === AuctionStatus.PROGRESS ? ' header-button--selected' : ''}">
                진행 중 경매
            </button>
            <button class="header-button-container__header-button bid-status-before
                ${query.auctionStatus === AuctionStatus.BEFORE ? ' header-button--selected' : ''}">
                진행 전 경매
            </button>
            <button class="header-button-container__header-button bid-status-complete
                ${query.auctionStatus === AuctionStatus.COMPLETED ? ' header-button--selected' : ''}">
                종료된 경매
            </button>
        </div>
        ${this.auctionList && this.auctionList.length ? 
        `<div class="card-container">
            ${this.auctionList.map(() => `<div data-component="AuctionCard"></div>`).join('')}
        </div>`
        : '<div class="no-content">조회된 경매 없음</div>'}
        <div data-component="PagingView"></div>
        `;
    }

    initialize() {
        const queryState = this.state || QUERY_INITIAL;
        this.updateBidList(queryState);

        this.addEvent('click', '.bid-status-all',
            () => { selectStatus(AuctionStatus.ALL) });
        this.addEvent('click', '.bid-status-progress',
            () => { selectStatus(AuctionStatus.PROGRESS) });
        this.addEvent('click', '.bid-status-before',
            () => { selectStatus(AuctionStatus.BEFORE) });
        this.addEvent('click', '.bid-status-complete',
            () => { selectStatus(AuctionStatus.COMPLETED) });
    }

    mounted() {
        const auctionList = this.auctionList;
        const $auctionCards = this.$target.querySelectorAll('[data-component="AuctionCard"]');
        $auctionCards.forEach(($auctionCard, idx) => {
            new AuctionCard($auctionCard as HTMLElement, { auction: auctionList[idx] });
        });

        const pages = this.pages;
        const { page } = this.state || QUERY_INITIAL;
        if (pages) {
            const $pagingView = this.$target.querySelector('[data-component="PagingView"]') as HTMLElement;
            new PagingView($pagingView, { currentPage: page, numberOfPages: pages, pagesPerShow: 10 });
        }
    }

    updateBidList(query: AuctionQuery) {
        if (query.minPrice > query.maxPrice)
            return;
        requestAuctionList(query).then(auctionListData => {
            if (auctionListData) {
                const { auctionInfoList, totalAuctionNum } = auctionListData;
                this.auctionList = auctionInfoList;
                this.pages = Math.ceil(totalAuctionNum / ARTICLE_PER_PAGE);
            } else {
                this.auctionList = [];
                this.pages = 0;
            }
            this.render();
        });
    }
}

export default AuctionList;