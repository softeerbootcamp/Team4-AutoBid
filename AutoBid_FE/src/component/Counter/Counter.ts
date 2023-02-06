import Component from "../../core/component";
import {CounterState, counterStateSelector, decrement, increment} from "../../store/counter";
import GlobalStore from "../../core/store";

class Counter extends Component<CounterState, any> {
    stateSelector(globalState: any): CounterState | undefined {
        return globalState[counterStateSelector];
    }

    template(): InnerHTML["innerHTML"] {
        const { count } = this.state || { count: 0 };
        return `
        <h1>${count}</h1>
        <button class="counter-increase-btn">증가</button>
        <button class="counter-decrease-btn">감소</button>
        `;
    }

    initialize() {
        const globalStore = GlobalStore.get();
        this.addEvent('click', '.counter-increase-btn', () => {
            globalStore.dispatch(increment(5));
        });
        this.addEvent('click', '.counter-decrease-btn', () => {
            globalStore.dispatch(decrement(5));
        });
    }

    onStateChanged(prevLocalState: CounterState) {
        const { count } = this.state || { count: 0 };
        console.log(count);
        this.render();
    }
}

export default Counter;