import GlobalStore from "../core/store";
import {login, logout} from "../store/user";
import dotenv from "dotenv";

dotenv.config();
const AUTH_CLIENT_ID = process.env.AUTH_CLIENT_ID as string;
const AUTH_URI = process.env.AUTH_URI as string;
const AUTH_REDIRECT_URI = process.env.AUTH_REDIRECT_URI as string;
const API_BASE_URL = process.env.API_BASE_URL as string;
const LOGIN_ENDPOINT = process.env.LOGIN_ENDPOINT as string;

const getAuthUri = () => {
    const authUri = new URL(AUTH_URI);
    authUri.searchParams.set('response_type', 'code');
    authUri.searchParams.set('client_id', AUTH_CLIENT_ID);
    authUri.searchParams.set('redirect_uri', AUTH_REDIRECT_URI);
    return authUri;
};

const requestCode = () => {
    const authUri = getAuthUri();
    const popup = window.open(authUri, 'Hyundai OAuth',
        'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no');
    return new Promise((resolve: (code: string) => any, reject) => {
        const targetUrl = new URL(AUTH_REDIRECT_URI);
        const checker = setInterval(() => {
            if (!popup) {
                reject();
                return;
            }
            try {
                const redirectionUrl = new URL(popup.location.href);
                if (targetUrl.pathname === redirectionUrl.pathname) {
                    const code = redirectionUrl.searchParams.get('code');
                    if (code) resolve(code);
                    popup.close();
                }
            } catch (e) {} finally {
                if (popup.closed) {
                    clearInterval(checker);
                    reject('authentication canceled');
                }
            }
        }, 1000);
    });
};
const requestSession = (code: string) => {
    return fetch(`${API_BASE_URL}${LOGIN_ENDPOINT}?code=${code}`, {
        cache: 'no-cache'
    });
}

const AuthManager = {
    authRequiredFetch: async (work: () => Promise<Response>, denyCode: number): Promise<Response> => {
        const firstTry = await work();
        if (firstTry.status !== denyCode)
            return firstTry;
        GlobalStore.get().dispatch(logout());
        const authCode = await requestCode();
        const sessionResponse = await requestSession(authCode);
        // const { username } = await sessionResponse.json(); // TODO : username 받아와서 업데이트 시켜줘야함
        const { username } = { username: '테스트' };
        GlobalStore.get().dispatch(login(username));
        return await work();
    }
};

export default AuthManager;