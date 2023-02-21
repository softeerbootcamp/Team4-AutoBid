export type AddBid = {
    fileList: FileList,
    carId: number,
    auctionTitle: string,
    auctionStartTime: string,
    auctionEndTime: string,
    auctionStartPrice: number
};

export type AddBidDTO = {
    addBid: AddBid
}