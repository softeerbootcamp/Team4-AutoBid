export type CarListDTO = {
    carList: CarInfo[],
    length: number
};

export enum CarType {
    ALL = 'ALL',
    GN = 'GN',
    EV = 'EV',
    HEV = 'HEV',
    PHEV = 'PHEV',
    FCEV = 'FCEV'
}

export type CarInfo = {
    carId: number, distance: number,
    name: string, type: CarType, sellName: string
};

export const getCarTypeName = (carType: CarType) => {
    switch (carType) {
        case CarType.ALL:
            return '전체';
        case CarType.GN:
            return '내연기관';
        case CarType.EV:
            return '전기';
        case CarType.HEV:
            return '하이브리드';
        case CarType.PHEV:
            return 'PHEV';
        case CarType.FCEV:
            return '수소전기';
        default:
            return 'Unknown';
    }
};