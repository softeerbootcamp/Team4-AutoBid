import { registerReducer } from "../core/store";
import { Action, Reducer } from "redux";

enum CounterActionType {
    INCREMENT = 'counter/INCREMENT',
    DECREMENT = 'counter/DECREMENT'
}

export interface CounterAction extends Action<CounterActionType> { diff: number }
export type CounterState = { count: number };

const initialState: CounterState = { count: 0 };

export const increment = (diff: number) => ({ type: CounterActionType.INCREMENT, diff });
export const decrement = (diff: number) => ({ type: CounterActionType.DECREMENT, diff });

const counter: Reducer<CounterState, CounterAction> = (state = initialState, action: CounterAction) => {
    switch (action.type) {
        case CounterActionType.INCREMENT:
            return { ...state,
                count: state.count + action.diff
            }
        case CounterActionType.DECREMENT:
            return { ...state,
                count: state.count - action.diff
            }
        default:
            return state;
    }
};

export const counterStateSelector = registerReducer(counter);