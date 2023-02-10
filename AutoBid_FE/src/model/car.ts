export type CarListDTO = {
    carList: CarInfo[]
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
    id: number, distance: number,
    name: string, type: CarType, sellName: string
};

export const getCarTypeName = (carType: CarType) => {
    switch (carType) {
        case CarType.GN:
            return '내연기관';
        case CarType.EV:
            return '전기';
        case CarType.HEV:
            return '하이브리드';
        case CarType.PHEV:
            return '플러그인하이브리드';
        case CarType.FCEV:
            return '수소전기';
        default:
            return 'Unknown';
    }
};