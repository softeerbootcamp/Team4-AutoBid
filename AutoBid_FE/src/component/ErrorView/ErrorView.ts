import Component from "../../core/component";
import {Error} from "../../model/error";
import "./errorview.css"

class ErrorView extends Component<any, Error> {
    template(): InnerHTML["innerHTML"] {
        return `
        <h4 class="error-view__title">${this.props.title}</h4>
        <span class="error-view__msg">${this.props.message}</span>
        `;
    }
}

export default ErrorView;