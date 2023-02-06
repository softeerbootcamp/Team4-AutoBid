import Component from "../core/component";
import Counter from "./Counter/Counter";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        <div data-component="Counter"></div>
        `;
    }

    mounted() {
        const $counter = this.$target.querySelector('[data-component="Counter"]') as HTMLElement;
        new Counter($counter, {});
    }
}

export default App;