import GlobalStore, { registerReducer } from "../core/store";
import {Reducer} from "redux";
import {requestCode, requestCurrentUser, requestInvalidateSession, requestLiveSession} from "../api/auth";
import {UserInfo} from "../model/user";

enum UserActionType {
    LOGIN = 'user/LOGIN',
    LOGOUT = 'user/LOGOUT'
}

export type UserState = UserInfo & { isLogin: boolean };

const initialState: UserState = {
    isLogin: false,
    id: -1,
    userName: "",
    email: "",
    phone: ""
};

export const login = async () => {
    const code = await requestCode();
    const userInfo = await requestLiveSession(code);
    if (!userInfo) return;
    GlobalStore.get().dispatch({ type: UserActionType.LOGIN, userInfo });
};

export const logout = async () => {
    const logoutResult = await requestInvalidateSession();
    if (!logoutResult) return;
    GlobalStore.get().dispatch({ type: UserActionType.LOGOUT });
};

export const whoIam = async () => {
    const userInfo = await requestCurrentUser();
    if (!userInfo) return;
    GlobalStore.get().dispatch({ type: UserActionType.LOGIN, userInfo });
}

const user: Reducer<UserState> = (state = initialState, action) => {
    switch (action.type) {
        case UserActionType.LOGIN:
            return { ...state,
                isLogin: true,
                ...(action.userInfo)
            }
        case UserActionType.LOGOUT:
            return initialState;
        default:
            return state;
    }
};

export const userStateSelector = registerReducer(user);