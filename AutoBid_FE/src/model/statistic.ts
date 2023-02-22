export type AuctionStatistic = {
    totalSold: number, minPrice: number, maxPrice: number, statisticsHistogram: Histogram
};

export type Histogram = {
    intervalPrice: number,
    contents: number[]
}

export type AuctionStatisticDTO = AuctionStatistic;