export type AuctionStatistic = {
    nSold: number, minPrice: number, maxPrice: number, histogram: Histogram
};

export type Histogram = {
    binSize: number,
    contents: number[]
}

export type AuctionStatisticDTO = {
    auctionStatistic: AuctionStatistic
}