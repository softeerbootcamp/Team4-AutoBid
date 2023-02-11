import Component from "../core/component";
import Header from "./Header/Header";
import AuctionList from "./AuctionList/AuctionList";
import "./app.css";
import QuerySidebar from "./QuerySidebar/QuerySidebar";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <header data-component="Header"></header>
        <div class="main-container">
            <div data-component="QuerySidebar"></div>
            <div data-component="AuctionList"></div>
        </div>
        `;
    }

    mounted() {
        const $header = this.$target.querySelector('[data-component="Header"]') as HTMLElement;
        new Header($header, {});

        const $auctionList = document.querySelector('[data-component="AuctionList"]') as HTMLElement;
        new AuctionList($auctionList, {});

        const $querySidebar = document.querySelector('[data-component="QuerySidebar"]') as HTMLElement;
        new QuerySidebar($querySidebar, {});
    }
}

export default App;