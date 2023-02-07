import dotenv from "dotenv";

dotenv.config();
const AUTH_CLIENT_ID = process.env.AUTH_CLIENT_ID as string;
const AUTH_URI = process.env.AUTH_URI as string;
const AUTH_REDIRECT_URI = process.env.AUTH_REDIRECT_URI as string;
const API_BASE_URL = process.env.API_BASE_URL as string;
const SESSION_ENDPOINT = process.env.SESSION_ENDPOINT as string;

const getAuthUri = () => {
    const authUri = new URL(AUTH_URI);
    authUri.searchParams.set('response_type', 'code');
    authUri.searchParams.set('client_id', AUTH_CLIENT_ID);
    authUri.searchParams.set('redirect_uri', AUTH_REDIRECT_URI);
    return authUri;
};

export const requestCode = () => {
    const authUri = getAuthUri();
    const popup = window.open(authUri, 'Hyundai OAuth',
        'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no') as Window;
    return new Promise((resolve: (code: string) => any, reject) => {
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
                    reject('Authentication canceled');
                }
            }
        }, 100);
    });
};

export type UserInfo = { userId: number, username: string, email: string, phone: string };

export const requestLiveSession = async (code: string): Promise<UserInfo | null> => {
    const res = await fetch(`${API_BASE_URL}${SESSION_ENDPOINT}`, {
        method: 'POST',
        headers: { 'X-Auth-Code': code }
    });
    if (res.ok)
        return await res.json() as UserInfo;
    return null;
};

export const requestInvalidateSession = async (): Promise<boolean> => {
    const res = await fetch(`${API_BASE_URL}${SESSION_ENDPOINT}`, {
        method: 'DELETE'
    });
    return res.ok;
};

