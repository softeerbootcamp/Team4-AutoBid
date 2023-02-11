import {AuctionStatisticDTO} from "../model/statistic";
import {asyncTaskWrapper, lazyReturn} from "../core/util";

const lazyStatistic = lazyReturn({
    auctionStatistic: {
        nSold: 114,
        minPrice: 0,
        maxPrice: 11200,
        histogram: {
            binSize: 100,
            contents: [
                10,
                22,
                24,
                15,
                35,
                55,
                67,
                58,
                43,
                21,
                13,
                11,
                9
            ]
        }
    }
} as AuctionStatisticDTO, 1000);

export const requestAuctionStatistic = asyncTaskWrapper(async () => {
    return await lazyStatistic;
});