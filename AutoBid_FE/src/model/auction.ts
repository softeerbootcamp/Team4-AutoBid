import {CarInfo} from "./car";

export enum AuctionStatus {
    ALL = 'ALL',
    PROGRESS = 'PROGRESS',
    BEFORE = 'BEFORE',
    COMPLETED = 'COMPLETED'
}

export type Auction = {
    auctionId: number, images: string[],
    title: string, carInfo: CarInfo,
    startPrice: number,
    endPrice: number,
    status: AuctionStatus,
    startTime: string, endTime: string
};

export type AuctionDTO = {
    auction: Auction
};

export type AuctionListDTO = {
    auctionInfoList: Auction[], totalAuctionNum: number
};