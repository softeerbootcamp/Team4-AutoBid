import dotenv from "dotenv";
import {AuctionStatisticDTO} from "../model/statistic";
import {asyncTaskWrapper} from "../core/util";
import {AuctionStatus} from "../model/auction";
import {CarType} from "../model/car";

dotenv.config();
const API_BASE_URL = process.env.API_BASE_URL as string;
const STATISTIC_ENDPOINT = process.env.STATISTIC_ENDPOINT as string;

export const requestAuctionStatistic = asyncTaskWrapper(
    async (auctionStatus: AuctionStatus, carType: CarType): Promise<AuctionStatisticDTO|null> => {
        const statisticRes =
            await fetch(`${API_BASE_URL}${STATISTIC_ENDPOINT}?carType=${carType}&auctionStatus=${auctionStatus}`);
        if (statisticRes.ok)
            return await statisticRes.json() as AuctionStatisticDTO;
        return null;
    });