export type AddBid = {
    multipartFileList: FormData,
    carId: number,
    auctionTitle: string,
    auctionStartTime: string,
    auctionEndTime: string,
    auctionStartPrice: number
};

export type AddBidDTO = {
    addBid: AddBid
}