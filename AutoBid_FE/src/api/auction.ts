import dotenv from "dotenv";
import {AuctionQuery} from "../model/query";
import {AuctionListDTO} from "../model/auction";
import {ARTICLE_PER_PAGE, asyncTaskWrapper} from "../core/util";

dotenv.config();
const API_BASE_URL = process.env.API_BASE_URL as string;
const LIST_ENDPOINT = process.env.LIST_ENDPOINT as string;

export const requestAuctionList = asyncTaskWrapper(
    async ({auctionStatus, carType, minPrice, maxPrice, page}: AuctionQuery): Promise<AuctionListDTO|null> => {
        const auctionListRes =
            await fetch(`${API_BASE_URL}${LIST_ENDPOINT}?carType=${carType}&auctionStatus=${auctionStatus}`
                + `&startPrice=${minPrice}&endPrice=${maxPrice}&page=${page}&size=${ARTICLE_PER_PAGE}`);
        if (auctionListRes.ok)
            return await auctionListRes.json() as AuctionListDTO;
        return null;
    });