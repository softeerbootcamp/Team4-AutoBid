import Component from "../../core/component";
import AuctionCard from "../AuctionCard/AuctionCard";
import {QUERY_INITIAL, queryStateSelector, selectPage, selectStatus} from "../../store/query";
import {AuctionQuery} from "../../model/query";
import {Auction, AuctionStatus} from "../../model/auction";
import {requestAuctionList} from "../../api/auction";
import "./auctionlist.css"


class AuctionList extends Component<AuctionQuery> {
    private auctionList: Auction[] = [];
    private pages: number = 1;

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
        <div class="card-container">
        ${this.auctionList ? this.auctionList.map(() => `
            <div data-component="AuctionCard"></div>
        `).join('') : ''}        
        </div>
        <div class="pagination">
        ${[...Array(this.pages).keys()].map((_, idx) => `
            <button class="pagination__btn 
                ${idx + 1 === query.page ? 'pagination__btn--selected' : ''}"
                data-page="${idx + 1}">
                ${idx + 1}
            </button>
        `).join('')}
        </div>
        `
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

        this.addEvent('click', '.pagination__btn', this.pageButtonEvent.bind(this));
    }

    pageButtonEvent(e: Event) {
        const $pageButton = (e.target as Element).closest('.pagination__btn') as HTMLElement;
        const page = $pageButton.dataset.page as string;
        selectPage(parseInt(page));
    }

    mounted() {
        const auctionList = this.auctionList;
        const $auctionCards = this.$target.querySelectorAll('[data-component="AuctionCard"]');
        $auctionCards.forEach(($auctionCard, idx) => {
            new AuctionCard($auctionCard as HTMLElement, {
                auction: auctionList[idx],
                onClick: () => {
                    console.log(idx);
                }
            });
        });
    }

    updateBidList(query: AuctionQuery) {
        if (query.minPrice > query.maxPrice)
            return;
        requestAuctionList(query).then(({ auctionList, pages }) => {
            this.auctionList = auctionList;
            this.pages = pages;
            this.render();
        });
    }
}

export default AuctionList;