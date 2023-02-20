import dotenv from "dotenv";
import {asyncTaskWrapper} from "../core/util";
import {AuctionListDTO} from "../model/auction";
import {CarListDTO} from "../model/car";

dotenv.config();
const API_BASE_URL = process.env.API_BASE_URL as string;
const PARTICIPATION_ENDPOINT = process.env.PARTICIPATION_ENDPOINT as string;
const MY_ENDPOINT = process.env.MY_ENDPOINT as string;
const USER_CAR_ENDPOINT = process.env.USER_CAR_ENDPOINT as string;
const headers = {
    headers: {
        Authorization: 'Bearer random'
    }, method: 'GET'
};

export const requestParticipationList = asyncTaskWrapper(
    async (): Promise<AuctionListDTO | null> => {
        const listResult = await fetch(`${API_BASE_URL}${PARTICIPATION_ENDPOINT}`, headers);
        if (listResult.ok) return await listResult.json() as AuctionListDTO;
        return null;
    }
);

export const requestMyList = asyncTaskWrapper(
    async (): Promise<AuctionListDTO | null> => {
        const myListResult = await fetch(`${API_BASE_URL}${MY_ENDPOINT}`, headers);
        if (myListResult.ok) return await myListResult.json() as AuctionListDTO;
        return null;
    }
);

export const requestUserCarList = asyncTaskWrapper(
    async (): Promise<CarListDTO | null> => {
        const myListResult = await fetch(`${API_BASE_URL}${USER_CAR_ENDPOINT}`, headers);
        if (myListResult.ok) return await myListResult.json() as CarListDTO;
        return null;
    }
);