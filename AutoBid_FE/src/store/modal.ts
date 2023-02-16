import {Auction} from "../model/auction";
import GlobalStore, {registerReducer} from "../core/store";
import {Reducer} from "redux";

export enum ModalView {
    NONE,
    POSTING,
    SHOWING
}

export enum ModalActionType {
    CLOSE='modal/CLOSE',
    POP_POSTING='modal/POP_POSTING',
    POP_SHOWING='modal/POP_SHOWING'
}

export type ModalState = {
    pop: boolean, view: ModalView, auction: Auction|undefined
};

export const MODAL_INITIAL: ModalState = {
    pop: false, view: ModalView.POSTING, auction: undefined
};

export const popShowingAuctionModal = (auction: Auction) => {
    GlobalStore.get().dispatch({ type: ModalActionType.POP_SHOWING, auction });
};

export const popPostingAuctionModal = () => {
    GlobalStore.get().dispatch({ type: ModalActionType.POP_POSTING });
};

export const closeModal = () => {
    GlobalStore.get().dispatch({ type: ModalActionType.CLOSE });
};

const modal: Reducer<ModalState> = (state = MODAL_INITIAL, action) => {
    switch (action.type) {
        case ModalActionType.CLOSE:
            return { ...state,
                pop: false,
                view: ModalView.NONE,
                auction: undefined
            };
        case ModalActionType.POP_SHOWING:
            return { ...state,
                pop: true,
                view: ModalView.SHOWING,
                auction: action.auction,
            };
        case ModalActionType.POP_POSTING:
            return { ...state,
                pop: true,
                view: ModalView.POSTING,
                auction: undefined
            };
        default:
            return state;
    }
}

export const modalStateSelector = registerReducer(modal);

