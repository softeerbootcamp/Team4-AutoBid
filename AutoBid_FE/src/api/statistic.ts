import {AuctionStatisticDTO} from "../model/statistic";

export const requestAuctionStatistic = async (): Promise<AuctionStatisticDTO> => {
    return new Promise(res => {
        setTimeout(() => res({
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
        }), 1000);
    });
};