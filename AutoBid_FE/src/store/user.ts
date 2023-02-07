import GlobalStore, { registerReducer } from "../core/store";
import {Reducer} from "redux";
import {requestCode, requestInvalidateSession, requestLiveSession, UserInfo} from "../api/auth";

enum UserActionType {
    LOGIN = 'user/LOGIN',
    LOGOUT = 'user/LOGOUT'
}

export type UserState = UserInfo & { isLogin: boolean };

const initialState: UserState = {
    isLogin: false,
    userId: -1,
    username: "",
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