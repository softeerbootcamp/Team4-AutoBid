import {CarInfo} from "./car";

export enum AuctionStatus {
    ALL = -1,
    PROGRESS,
    BEFORE,
    COMPLETE
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