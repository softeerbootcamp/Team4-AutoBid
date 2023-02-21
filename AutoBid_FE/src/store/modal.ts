import {Auction} from "../model/auction";
import GlobalStore, {registerReducer} from "../core/store";
import {Reducer} from "redux";
import {Error} from "../model/error";

export enum ModalView {
    NONE,
    POSTING,
    SHOWING,
    ERROR
}

export enum ModalActionType {
    CLOSE='modal/CLOSE',
    POP_POSTING='modal/POP_POSTING',
    POP_SHOWING='modal/POP_SHOWING',
    POP_ERROR='modal/POP_ERROR'
}

export type ModalState = {
    pop: boolean, view: ModalView, auction: Auction|undefined, error: Error|undefined
};

export const MODAL_INITIAL: ModalState = {
    pop: false, view: ModalView.NONE, auction: undefined, error: undefined
};

export const popShowingAuctionModal = (auction: Auction) => {
    GlobalStore.get().dispatch({ type: ModalActionType.POP_SHOWING, auction });
};

export const popPostingAuctionModal = () => {
    GlobalStore.get().dispatch({ type: ModalActionType.POP_POSTING});
};

export const popErrorModal = (error: Error) => {
    GlobalStore.get().dispatch({ type: ModalActionType.POP_ERROR, error });
}

export const closeModal = () => {
    GlobalStore.get().dispatch({ type: ModalActionType.CLOSE });
};

const modal: Reducer<ModalState> = (state = MODAL_INITIAL, action) => {
    switch (action.type) {
        case ModalActionType.CLOSE:
            return { ...state,
                pop: false
            };
        case ModalActionType.POP_SHOWING:
            return { ...MODAL_INITIAL,
                pop: true,
                view: ModalView.SHOWING,
                auction: action.auction,
            };
        case ModalActionType.POP_POSTING:
            return { ...MODAL_INITIAL,
                pop: true,
                view: ModalView.POSTING,
            };
        case ModalActionType.POP_ERROR:
            return { ...MODAL_INITIAL,
                pop: true,
                view: ModalView.ERROR,
                error: action.error
            }
        default:
            return state;
    }
}

export const modalStateSelector = registerReducer(modal);

