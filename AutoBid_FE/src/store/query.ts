import {Reducer} from "redux";
import GlobalStore, {registerReducer} from "../core/store";
import {AuctionQuery} from "../model/query";
import {AuctionStatus} from "../model/auction";
import {CarType} from "../model/car";

export const QUERY_INITIAL: AuctionQuery = {
    auctionStatus: AuctionStatus.ALL, carType: CarType.ALL,
    minPrice: 0, maxPrice: 10000, page: 1
};

enum QueryActionType {
    PAGE = 'query/PAGINATE',
    STATUS = 'query/STATUS',
    TYPE = 'query/TYPE',
}

export const selectPage = (page: number) => {
    GlobalStore.get().dispatch({ type: QueryActionType.PAGE, page: page });
}
export const selectStatus = (auctionStatus: AuctionStatus) => {
    GlobalStore.get().dispatch({ type: QueryActionType.STATUS, auctionStatus: auctionStatus });
}
export const selectCarType = (carType: CarType) => {
    GlobalStore.get().dispatch({ type: QueryActionType.TYPE, carType: carType });
}

const query: Reducer<AuctionQuery> = (state = QUERY_INITIAL, action) => {
    switch (action.type) {
        case QueryActionType.PAGE:
            return { ...state,
                page: action.page
            };
        case QueryActionType.STATUS:
            return { ...state,
                auctionStatus: action.auctionStatus
            };
        case QueryActionType.TYPE:
            return { ...state,
                carType: action.carType
            }
        default:
            return state;
    }
};

export const queryStateSelector = registerReducer(query);