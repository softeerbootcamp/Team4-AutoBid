import GlobalStore from "./store";
import {Unsubscribe} from "redux";

class Component<State, Props = {}> {
    $target: HTMLElement;
    props: Props;
    state: State|undefined;
    #unsubscribe: Unsubscribe|undefined;

    constructor($target: HTMLElement, props: Props) {
        this.$target = $target;
        this.props = props;
        this.#initState();
        this.initialize();
        this.render();
    }

    initialize(): void {}
    onStateChanged(prevLocalState: State): void {}
    stateSelector(globalState: any): State|undefined { return undefined; }
    mounted() {}
    template(): InnerHTML['innerHTML'] { return ''; }

    addEvent(type: string, selectors: string, listener: EventListener) {
        const children = [ ...this.$target.querySelectorAll(selectors) ];
        const isTarget = ({ target }: Event) => {
            const targetElement = target as Element;
            return children.includes(targetElement) || (targetElement).closest(selectors);
        }
        this.$target.addEventListener(type, e => {
            if (!isTarget(e)) return false;
            return listener(e);
        });
    }

    #initState() {
        const store = GlobalStore.get();
        const globalState = store.getState();
        const localState = this.stateSelector(globalState);
        if (!localState) return;
        this.state = localState;
        this.#unsubscribe = store.subscribe(() => {
            if (!this.$target.isConnected && this.#unsubscribe) {
                this.#unsubscribe();
                return;
            }
            const globalState = store.getState();
            const newLocalState = this.stateSelector(globalState);
            if (this.state && this.state !== newLocalState) {
                const prevLocalState = this.state;
                this.state = newLocalState;
                this.onStateChanged(prevLocalState);
            }
        });
    }

    render() {
        this.$target.innerHTML = this.template();
        this.mounted();
    }
}

export default Component;