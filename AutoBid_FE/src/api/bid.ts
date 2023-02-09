import {QueryState} from "../store/query";

export const requestBidList = async (query: QueryState) => {
    return [
        {
            images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
            title: '현대 아반떼MD 프리미어 1.6 GDi',
            tags: ['1인신조', '완전무사고', '여성운전자'],
            carInfo: {
                year: 2018,
                distance: 142852,
                type: '가솔린',
                region: '대전'
            },
            price: 1090
        },
        {
            images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
            title: '현대 아반떼MD 프리미어 1.6 GDi',
            tags: ['1인신조', '완전무사고', '여성운전자'],
            carInfo: {
                year: 2018,
                distance: 142852,
                type: '가솔린',
                region: '대전'
            },
            price: 1090
        },
        {
            images: ['https://picsum.photos/500', 'https://picsum.photos/600', 'https://picsum.photos/700', 'https://picsum.photos/800', 'https://picsum.photos/900'],
            title: '현대 아반떼MD 프리미어 1.6 GDi',
            tags: ['1인신조', '완전무사고', '여성운전자'],
            carInfo: {
                year: 2018,
                distance: 142852,
                type: '가솔린',
                region: '대전'
            },
            price: 1090
        }
    ]
};