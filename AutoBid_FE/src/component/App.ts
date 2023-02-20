import Component from "../core/component";
import Header from "./Header/Header";
import AuctionList from "./AuctionList/AuctionList";
import "./app.css";
import QuerySidebar from "./QuerySidebar/QuerySidebar";
import {whoIam} from "../store/user";
import MyPage from "./MyPage/MyPage";
import Modal from "./Modal/Modal";
import {Page, PAGE_INITIAL, PageState, pageStateSelector} from "../store/page";

class App extends Component<PageState> {
    stateSelector(globalState: any): PageState | undefined {
        return globalState[pageStateSelector];
    }

    onStateChanged(prevLocalState: PageState) {
        this.render();
    }

    template(): InnerHTML["innerHTML"] {
        const { page } = this.state || PAGE_INITIAL;
        return `
        <header data-component="Header"></header>
        <div class="main-container">
        ${page === Page.MAIN ? `
            <div data-component="QuerySidebar"></div>
            <div data-component="AuctionList"></div>
        ` : `
            <div data-component="MyPage"></div>
        `}
        </div>
        <div data-component="Modal"></div>
        `;
    }

    mounted() {
        const $header = this.$target.querySelector('[data-component="Header"]') as HTMLElement;
        new Header($header, {});

        const $auctionList = this.$target.querySelector('[data-component="AuctionList"]');
        if ($auctionList) {
            new AuctionList($auctionList as HTMLElement, {});
        }

        const $querySidebar = this.$target.querySelector('[data-component="QuerySidebar"]');
        if ($querySidebar) {
            new QuerySidebar($querySidebar as HTMLElement, {});
        }

        const $myPage = document.querySelector('[data-component="MyPage"]');
        if ($myPage) {
            new MyPage($myPage as HTMLElement, {});
        }
        
        const $modal = this.$target.querySelector('[data-component="Modal"]') as HTMLElement;
        new Modal($modal, {});

        whoIam().then(() => {console.log('session user initialized')});
    }
}

export default App;