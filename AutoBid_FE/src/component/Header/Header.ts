import Component from "../../core/component";
import "./header.css";
import {login, logout, UserState, userStateSelector, whoIam} from "../../store/user";
import {showMain, showMy} from "../../store/page";
import Toast from "../../core/toast";

class Header extends Component<UserState> {
    stateSelector(globalState: any): UserState | undefined {
        return globalState[userStateSelector];
    }

    template(): InnerHTML["innerHTML"] {
        const { isLogin, userName } = this.state || { isLogin: false, userName: "" };
        return `
        <div class="header__container">
            <button class="header__container__logo"></button>
            <div class="header__container__menu">
                <button class="header__container__content header__container__content--btn my-page-btn">내 차 목록</button>
                <button class="header__container__content header__container__content--btn my-page-btn">경매 목록</button>
                ${isLogin ?
                    `<span class="header__container__content header__container__content--username">${userName}님 안녕하세요</span>
                    <button class="header__container__content header__container__content--btn logout-btn">
                        <i class="fas fa-sign-out-alt fa-lg"></i>
                    </button>` 
                :
                    `<button class="header__container__content header__container__content--btn my-page-btn">
                        <i class="fas fa-user fa-lg"></i>
                    </button>`
                }
            </div>
        </div>
        `
    }

    initialize() {
        this.addEvent('click', '.my-page-btn', async () => {
            const userInfo = await whoIam() || await login();
            if (!userInfo) {
                Toast.show('마이페이지는 로그인 후 이용할 수 있습니다', 1000);
                return;
            }
            showMy();
        });
        this.addEvent('click', '.logout-btn', logout);
        this.addEvent('click', '.header__container__logo', showMain);
    }

    onStateChanged(prevLocalState: UserState) {
        this.render();
    }
}

export default Header;