import {Reducer} from "redux";
import GlobalStore, {registerReducer} from "../core/store";

export enum BidStatus {
    ALL = -1,
    PROGRESS,
    BEFORE,
    COMPLETE
}

export enum CarType {
    ALL = -1,
    GN = '내연기관',
    EV = '전기',
    HEV = '하이브리드',
    PHEV = '플러그인하이브리드',
    FCEV = '수소전기'
}

export type QueryState = {
    bidStatus: BidStatus, carType: CarType, minPrice: number, maxPrice: number, page: number
};
export const QUERY_INITIAL: QueryState = {
    bidStatus: BidStatus.ALL, carType: CarType.ALL, minPrice: 0, maxPrice: 10000, page: 1
};

enum QueryActionType {
    PAGE = 'query/PAGINATE',
    STATUS = 'query/STATUS',
}

export const selectPage = (page: number) => {
    GlobalStore.get().dispatch({ type: QueryActionType.PAGE, page: page });
}
export const selectStatus = (bidStatus: BidStatus) => {
    GlobalStore.get().dispatch({ type: QueryActionType.STATUS, bidStatus: bidStatus });
}

const query: Reducer<QueryState> = (state = QUERY_INITIAL, action) => {
    switch (action.type) {
        case QueryActionType.PAGE:
            return { ...state,
                page: action.page
            };
        case QueryActionType.STATUS:
            return { ...state,
                bidStatus: action.bidStatus
            };
        default:
            return state;
    }
};

export const queryStateSelector = registerReducer(query);