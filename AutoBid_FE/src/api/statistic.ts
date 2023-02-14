import {AuctionStatisticDTO} from "../model/statistic";
import {asyncTaskWrapper, lazyReturn} from "../core/util";

export const requestAuctionStatistic = asyncTaskWrapper(async () => {
    return await lazyReturn({
        "totalSold": 14,
        "minPrice": 0,
        "maxPrice": 100000,
        "statisticsHistogram": {
            "intervalPrice": 500,
            "contents": [
                54,
                0,
                0,
                0,
                2,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                2
            ]
        }
    } as AuctionStatisticDTO, 1000);
});