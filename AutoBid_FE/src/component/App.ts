import Component from "../core/component";
import Header from "./Header/Header";
import AuctionList from "./AuctionList/AuctionList";
import "./app.css";
import QuerySidebar from "./QuerySidebar/QuerySidebar";
import {whoIam} from "../store/user";
import MyPage from "./MyPage/MyPage";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <header data-component="Header"></header>
        <div class="main-container">
            <div data-component="QuerySidebar" hidden></div>
            <div data-component="AuctionList" hidden></div>
            <div data-component="MyPage"></div>
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

        const $myPage = document.querySelector('[data-component="MyPage"]') as HTMLElement;
        new MyPage($myPage, {});

        whoIam().then(() => {console.log('session user initialized')});
    }
}

export default App;