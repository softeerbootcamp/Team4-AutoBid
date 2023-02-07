import Component from "../core/component";
import "./app.css";
import Header from "./Header/Header";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <header data-component="Header"></header>
        `;
    }

    mounted() {
        const $header = this.$target.querySelector('[data-component="Header"]') as HTMLElement;
        new Header($header, {});
    }
}

export default App;