import Component from "../../core/component";
import {CarInfo, getCarTypeName} from "../../model/car";
import "./carcard.css"

class CarCard extends Component<any, { carInfo: CarInfo }> {
    template(): InnerHTML["innerHTML"] {
        const { carInfo } = this.props;
        return `
        <h2 class="car-card__title">${carInfo.name} ${carInfo.sellName}</h2>
        <h4 class="car-card__detail">${getCarTypeName(carInfo.type)} | ${carInfo.distance.toLocaleString()}km</h4>
        `
    }
}

export default CarCard;