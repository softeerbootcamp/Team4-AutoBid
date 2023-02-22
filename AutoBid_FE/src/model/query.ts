import {AuctionStatus} from "./auction";
import {CarType} from "./car";

export type AuctionQuery = {
    auctionStatus: AuctionStatus, carType: CarType,
    minPrice: number, maxPrice: number, page: number
}