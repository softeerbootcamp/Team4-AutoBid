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
                tags: ['1인신조', '완전무사고', '여성운전자'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 1,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                price: 1090,
                status: 1
            },
            {
                id: 2,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                tags: ['1인신조', '완전무사고', '여성운전자'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 2,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN'  as CarType,
                    sellName: 'Avante'
                },
                price: 1090,
                status: 1
            },
            {
                id: 3,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                tags: ['1인신조', '완전무사고', '여성운전자'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 3,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                price: 1090,
                status: 1
            },
            {
                id: 4,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                tags: ['1인신조', '완전무사고', '여성운전자'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 4,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                price: 1090,
                status: 1
            },
            {
                id: 5,
                images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
                tags: ['1인신조', '완전무사고', '여성운전자'],
                title: '현대 아반떼MD 프리미어 1.6 GDi',
                carInfo: {
                    id: 5,
                    name: '내 차',
                    distance: 142852,
                    type: 'GN' as CarType,
                    sellName: 'Avante'
                },
                price: 1090,
                status: 1
            }
        ],
        pages: 5
    } as AuctionListDTO, 1000);
});