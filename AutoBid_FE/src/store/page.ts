import {Reducer} from "redux";
import GlobalStore, {registerReducer} from "../core/store";

enum PageActionType {
    AUCTION = 'page/AUCTION',
    MYPAGE = 'page/MYPAGE'
}

// const initialState:

export const setAuction = () => {
    GlobalStore.get().dispatch({type: PageActionType.AUCTION});
}

export const setMypage = () => {
    GlobalStore.get().dispatch({type: PageActionType.MYPAGE});
}

// const page: Reducer<PageActionType> = (state = )