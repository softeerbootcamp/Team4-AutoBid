import Component from "../../core/component";
import {AuctionQuery} from "../../model/query";
import {QUERY_INITIAL, queryStateSelector, selectCarType} from "../../store/query";
import {CarType, getCarTypeName} from "../../model/car";
import './querycartypegroup.css';

class QueryCarTypeGroup extends Component<AuctionQuery['carType']> {
    stateSelector(globalState: any): AuctionQuery["carType"] | undefined {
        return globalState[queryStateSelector].carType;
    }

    template(): InnerHTML["innerHTML"] {
        const currCarType = this.state || QUERY_INITIAL.carType;
        return `${
        Object.values(CarType).map(carType => `
        <button data-type="${carType}" class="query-car-type-group__btn
            ${carType === currCarType ? ' query-car-type-group__btn--checked' : ''}">
            ${getCarTypeIcon(carType)}
            <span class="query-car-type-group__btn__text">${getCarTypeName(carType)}</span>
        </button>
        `).join('')}`;
    }

    initialize() {
        this.addEvent('click', '.query-car-type-group__btn', ({ target }) => {
            const $button = (target as Element).closest('.query-car-type-group__btn') as HTMLElement;
            const carType = $button.dataset.type as CarType;
            selectCarType(carType);
        });
    }

    onStateChanged(prevLocalState: AuctionQuery["carType"]) {
        this.render();
    }
}

const getCarTypeIcon = (carType: CarType) => {
    switch (carType) {
        case CarType.GN:
            return `
            <i class="fa-solid fa-car fa-lg"></i>
            <i class="fa-solid fa-gas-pump"></i>
            `;
        case CarType.EV:
            return `
            <i class="fa-solid fa-car fa-lg"></i>
            <i class="fa-solid fa-plug-circle-bolt"></i>
            `;
        case CarType.HEV:
            return `
            <i class="fa-solid fa-gears fa-lg"></i>
            `;
        case CarType.PHEV:
            return `
            <i class="fa-solid fa-gear fa-lg"></i>
            <i class="fa-solid fa-plug-circle-bolt"></i>
            `;
        case CarType.FCEV:
            return `
            <i class="fa-solid fa-car fa-lg"></i>
            <i class="fa-solid fa-h fa-sm"></i>
            `;
        default:
            return `
            <i class="fas fa-check-circle fa-lg"></i>
            `;
    }
}

export default QueryCarTypeGroup;