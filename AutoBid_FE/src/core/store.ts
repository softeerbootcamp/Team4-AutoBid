import {combineReducers, createStore, Reducer, Store} from "redux";

const emptyReducer = () => {};
const reducers: Reducer<any, any>[] = [];

export const registerReducer = (reducer: Reducer<any, any>): string => {
    return (reducers.push(reducer) - 1).toString();
}

declare global {
    interface Window { __REDUX_DEVTOOLS_EXTENSION__?: any; }
}

class GlobalStore {
    constructor() {
        throw Error('Do not call constructor of GlobalStore. Use GlobalStore.get().');
    }

    static #store: Store|null = null;
    static get(): Store {
        if (GlobalStore.#store === null) {
            let combinedReducer: Reducer = reducers.length ?
                combineReducers(reducers) : emptyReducer;
            GlobalStore.#store = createStore(combinedReducer,
                window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
        }
        return GlobalStore.#store;
    }
}


export default GlobalStore;