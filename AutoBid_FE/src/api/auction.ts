import {AuctionQuery} from "../model/query";
import {AuctionListDTO} from "../model/auction";
import {CarType} from "../model/car";
import {asyncTaskWrapper, lazyReturn} from "../core/util";

export const requestAuctionList = asyncTaskWrapper(async (query: AuctionQuery): Promise<AuctionListDTO> => {
    console.log(query);
    return await lazyReturn({
        auctionList: [
            {
                id: 1,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 1,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                startPrice: 1090,
                currPrice: 2040,
                endPrice: 3010,
                status: 1,
                startTime: '2023-02-23 11:23',
                endTime: '2023-02-28 11:23'
            },
            {
                id: 2,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 2,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN'  as CarType,
                    sellName: 'Avante'
                },
                startPrice: 1090,
                currPrice: 2040,
                endPrice: 3010,
                status: 0,
                startTime: '2022-12-23 11:23',
                endTime: '2023-02-28 11:23'
            },
            {
                id: 3,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 3,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                startPrice: 1090,
                currPrice: 2040,
                endPrice: 3010,
                status: 2,
                startTime: '2022-12-23 11:23',
                endTime: '2022-12-23 11:23',
            },
            {
                id: 4,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 4,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                startPrice: 1090,
                currPrice: 2040,
                endPrice: 3010,
                status: 1,
                startTime: '2023-02-23 11:23',
                endTime: '2023-02-28 11:23'
            },
            {
                id: 5,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 5,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                startPrice: 1090,
                currPrice: 2040,
                endPrice: 3010,
                status: 1,
                startTime: '2023-02-23 11:23',
                endTime: '2023-02-28 11:23'
            }
        ],
        pages: 5
    } as AuctionListDTO, 1000);
});