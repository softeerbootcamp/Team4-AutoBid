import { registerReducer } from "../core/store";
import { Action, Reducer } from "redux";

enum UserActionType {
    LOGIN = 'user/LOGIN',
    LOGOUT = 'user/LOGOUT'
}

export interface UserAction extends Action<UserActionType> { username: string }
export type UserState = { isLogin: boolean, username: string };

const initialState: UserState = { isLogin: false, username: "" };

export const login = (username: string) => ({ type: UserActionType.LOGIN, username });
export const logout = () => ({ type: UserActionType.LOGOUT, username: "" });

const user: Reducer<UserState, UserAction> = (state = initialState, action: UserAction) => {
    switch (action.type) {
        case UserActionType.LOGIN:
            return { ...state,
                isLogin: true,
                username: action.username
            }
        case UserActionType.LOGOUT:
            return { ...state,
                isLogin: false,
            }
        default:
            return state;
    }
};

export const userStateSelector = registerReducer(user);