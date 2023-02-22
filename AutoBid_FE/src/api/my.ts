import dotenv from "dotenv";
import {asyncTaskWrapper} from "../core/util";
import {AuctionListDTO} from "../model/auction";
import {CarListDTO} from "../model/car";
dotenv.config();

const API_BASE_URL = process.env.API_BASE_URL || 'https://www.autobid.site';
const PARTICIPATION_ENDPOINT = process.env.PARTICIPATION_ENDPOINT || '/auction/my/participation';
const MY_ENDPOINT = process.env.MY_ENDPOINT || '/auction/my';
const USER_CAR_ENDPOINT = process.env.USER_CAR_ENDPOINT || '/user-cars';


export const requestMyParticipationAuctionList = asyncTaskWrapper(
    async (test = false): Promise<AuctionListDTO | null> => {
        try {
            const listResult = await fetch(`${API_BASE_URL}${PARTICIPATION_ENDPOINT}`, {
                credentials: "include",
                headers: { ...(test ? { Authorization: 'Bearer random' } : {}) }
            });
            if (listResult.ok)
                return await listResult.json() as AuctionListDTO;
            return null;
        } catch (e) {
            console.error(e);
            return null;
        }
    }
);

export const requestMyAuctionList = asyncTaskWrapper(
    async (test = false): Promise<AuctionListDTO | null> => {
        try {
            const listResult = await fetch(`${API_BASE_URL}${MY_ENDPOINT}`, {
                credentials: "include",
                headers: { ...(test ? { Authorization: 'Bearer random' } : {}) }
            });
            if (listResult.ok)
                return await listResult.json() as AuctionListDTO;
            return null;
        } catch (e) {
            console.error(e);
            return null;
        }
    }
);

export const requestMyCarList = asyncTaskWrapper(
    async (test = false): Promise<CarListDTO | null> => {
        try {
            const listResult = await fetch(`${API_BASE_URL}${USER_CAR_ENDPOINT}`, {
                credentials: "include",
                headers: { ...(test ? { Authorization: 'Bearer random' } : {}) }
            });
            if (listResult.ok)
                return await listResult.json() as CarListDTO;
            return null;
        } catch (e) {
            console.error(e);
            return null;
        }
    }
);