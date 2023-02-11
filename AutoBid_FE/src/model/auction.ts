import {CarInfo} from "./car";

export enum AuctionStatus {
    ALL = -1,
    PROGRESS,
    BEFORE,
    COMPLETE
}

export type Auction = {
    id: number, images: string[], tags: string[],
    title: string, carInfo: CarInfo, price: number, status: AuctionStatus
};

export type AuctionDTO = {
    auction: Auction
};

export type AuctionListDTO = {
    auctionList: Auction[], pages: number
};