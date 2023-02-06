import Component from "../../core/component";
import "./header.css";

class Header extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <div class="header__container">
            <h2 class="header__container__title">AutoBid</h2>
            <div class="header__container__menu">
                <button class="header__container__btn user-my-car">내 차 목록</button>
                <button class="header__container__btn user-my-bid">경매 목록</button>
                <button class="header__container__btn user-profile">
                    <i class="fas fa-user fa-lg"></i>
                </button>
            </div>
        </div>
        `
    }
}

export default Header;