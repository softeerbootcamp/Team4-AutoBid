import dotenv from "dotenv";
import {AuctionQuery} from "../model/query";
import {AuctionListDTO} from "../model/auction";
import {ARTICLE_PER_PAGE, asyncTaskWrapper} from "../core/util";
import {AddBid} from "../model/addBid";

dotenv.config();
const API_BASE_URL = process.env.API_BASE_URL as string;
const LIST_ENDPOINT = process.env.LIST_ENDPOINT as string;
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
    async ({fileList, carId, auctionTitle, auctionStartTime, auctionEndTime, auctionStartPrice}: AddBid) => {
        const formData = new FormData();
        // formData.append('fileList', fileList);
        // formData.append('carId', carId);
        //
        // for (const file in fileList) {
        //     const exampleFile = fs.createReadStream()
        // }

        try {
            const result = await fetch(`${API_BASE_URL}${POST_AUCTION_ENDPOINT}`, {
                method: 'POST',
                body: formData
            });
            if (result.ok)
                return await result.json();
            return null;
        } catch (e) {
            console.error(e);
            return null;
        }
    }
)