import {CarInfo} from "./car";

export enum AuctionStatus {
    ALL = 'ALL',
    PROGRESS = 'PROGRESS',
    BEFORE = 'BEFORE',
    COMPLETED = 'COMPLETED'
}

export type Auction = {
    id: number, images: string[],
    title: string, carInfo: CarInfo,
    startPrice: number,
    currPrice: number,
    endPrice: number,
    status: AuctionStatus,
    startTime: string, endTime: string
};

export type AuctionDTO = {
    auction: Auction
};

export type AuctionListDTO = {
    auctionList: Auction[], pages: number
};