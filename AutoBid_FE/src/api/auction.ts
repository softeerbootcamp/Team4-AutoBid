import dotenv from "dotenv";
import {AuctionQuery} from "../model/query";
import {AuctionForm, AuctionListDTO} from "../model/auction";
import {ARTICLE_PER_PAGE, asyncTaskWrapper} from "../core/util";

dotenv.config();
const API_BASE_URL = process.env.API_BASE_URL || 'https://www.autobid.site';
const LIST_ENDPOINT = process.env.LIST_ENDPOINT || '/auction/list';
const POST_AUCTION_ENDPOINT = process.env.POST_AUCTION_ENDPOINT || '/auction';

export const requestAuctionList = asyncTaskWrapper(
    async ({auctionStatus, carType, minPrice, maxPrice, page}: AuctionQuery): Promise<AuctionListDTO|null> => {
        try {
            const auctionListRes =
                await fetch(`${API_BASE_URL}${LIST_ENDPOINT}?carType=${carType}&auctionStatus=${auctionStatus}`
                    + `&startPrice=${minPrice}&endPrice=${maxPrice}&page=${page}&size=${ARTICLE_PER_PAGE}`);
            if (auctionListRes.ok)
                return await auctionListRes.json() as AuctionListDTO;
            return null;
        } catch (e) {
            console.error(e);
            return null;
        }
    });

export const requestPostAuction = asyncTaskWrapper(
    async ({multipartFileList, carId, auctionTitle, auctionStartTime, auctionEndTime, auctionStartPrice}: AuctionForm, test = false) => {
        const formData = new FormData();
        [...multipartFileList].forEach(file => {
            formData.append('multipartFileList', file);
        });
        formData.append('carId', carId.toString());
        formData.append('auctionTitle', auctionTitle);
        formData.append('auctionStartTime', auctionStartTime);
        formData.append('auctionEndTime', auctionEndTime);
        formData.append('auctionStartPrice', auctionStartPrice.toString());

        try {
            const result = await fetch(`${API_BASE_URL}${POST_AUCTION_ENDPOINT}`, {
                method: 'POST',
                headers: { ...(test ? { Authorization: 'Bearer random' } : {}) },
                body: formData
            });
            return result.ok;
        } catch (e) {
            console.error(e);
            return false;
        }
    }
)