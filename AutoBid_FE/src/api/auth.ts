import dotenv from "dotenv";
import {asyncTaskWrapper, popupCenter} from "../core/util";
import {UserInfo} from "../model/user";

dotenv.config();
const AUTH_CLIENT_ID = process.env.AUTH_CLIENT_ID as string;
const AUTH_URI = process.env.AUTH_URI as string;
const AUTH_REDIRECT_URI = process.env.AUTH_REDIRECT_URI as string;
const API_BASE_URL = process.env.API_BASE_URL as string;
const LOGIN_ENDPOINT = process.env.LOGIN_ENDPOINT as string;
const LOGOUT_ENDPOINT = process.env.LOGOUT_ENDPOINT as string;
const USER_ENDPOINT = process.env.USER_ENDPOINT as string;

const getAuthUri = () => {
    const authUri = new URL(AUTH_URI);
    authUri.searchParams.set('response_type', 'code');
    authUri.searchParams.set('client_id', AUTH_CLIENT_ID);
    authUri.searchParams.set('redirect_uri', AUTH_REDIRECT_URI);
    return authUri;
};

export const requestCode = asyncTaskWrapper(() => {
    const authUri = getAuthUri();
    const popup = popupCenter({url: authUri, title: 'Hyundai OAuth', w: 500, h: 600});
    return new Promise((resolve: (code: string|null) => any) => {
        const targetUrl = new URL(AUTH_REDIRECT_URI);
        const checker = setInterval(() => {
            try {
                const redirectionUrl = new URL(popup.location.href);
                if (targetUrl.pathname === redirectionUrl.pathname) {
                    const code = redirectionUrl.searchParams.get('code');
                    if (code) resolve(code);
                    popup.close();
                }
            } catch (e) {} finally {
                if (!popup || popup.closed) {
                    clearInterval(checker);
                    console.error('Authentication canceled');
                    resolve(null);
                }
            }
        }, 100);
    });
});

export const requestLiveSession = asyncTaskWrapper(async (code: string): Promise<UserInfo | null> => {
    try {
        const res = await fetch(`${API_BASE_URL}${LOGIN_ENDPOINT}`, {
            method: 'POST',
            headers: { 'X-Auth-Code': code },
            credentials: 'include'
        });
        if (res.ok)
            return await res.json() as UserInfo;
        return null;
    } catch (e) {
        console.error(e);
        return null;
    }
});

export const requestCurrentUser = asyncTaskWrapper(async (): Promise<UserInfo | null> => {
    try {
        const res = await fetch(`${API_BASE_URL}${USER_ENDPOINT}`, {
            method: 'GET',
            credentials: 'include'
        });
        if (res.ok)
            return await res.json() as UserInfo;
        return null;
    } catch (e) {
        console.error(e);
        return null;
    }
});

export const requestInvalidateSession = asyncTaskWrapper(async (): Promise<boolean> => {
    try {
        const res = await fetch(`${API_BASE_URL}${LOGOUT_ENDPOINT}`, {
            method: 'DELETE',
            credentials: 'include'
        });
        return res.ok;
    } catch (e) {
        console.error(e);
        return false;
    }
});

