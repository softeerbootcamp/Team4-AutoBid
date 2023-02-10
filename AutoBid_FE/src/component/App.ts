import Component from "../core/component";
import Header from "./Header/Header";
import AuctionList from "./AuctionList/AuctionList";
import "./app.css";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <header data-component="Header"></header>
        <div class="main-container">
            <div data-component="AuctionList"></div>
        </div>
        `;
    }

    mounted() {
        const $header = this.$target.querySelector('[data-component="Header"]') as HTMLElement;
        new Header($header, {});

        const $auctionList = document.querySelector('[data-component="AuctionList"]') as HTMLElement;
        new AuctionList($auctionList, {});
    }
}

export default App;