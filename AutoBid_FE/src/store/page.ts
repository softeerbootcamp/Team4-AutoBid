import {Reducer} from "redux";
import GlobalStore, {registerReducer} from "../core/store";

enum PageActionType {
    SHOW_MAIN = 'page/SHOW_MAIN',
    SHOW_MY = 'page/SHOW_MY'
}

export enum Page {
    MAIN,
    MY
}

export type PageState = { page: Page };
export const PAGE_INITIAL: PageState = { page: Page.MAIN };

export const showMain = () => {
    GlobalStore.get().dispatch({ type: PageActionType.SHOW_MAIN });
}

export const showMy = () => {
    GlobalStore.get().dispatch({ type: PageActionType.SHOW_MY });
}

const page: Reducer<PageState> = ((state = PAGE_INITIAL, action) => {
    switch (action.type) {
        case PageActionType.SHOW_MAIN:
            return { ...state,
                page: Page.MAIN
            };
        case PageActionType.SHOW_MY:
            return { ...state,
                page: Page.MY
            };
        default:
            return state;
    }
});

export const pageStateSelector = registerReducer(page);