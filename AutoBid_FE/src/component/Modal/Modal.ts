import Component from "../../core/component";
import {closeModal, MODAL_INITIAL, ModalState, modalStateSelector, ModalView} from "../../store/modal";
import "./modal.css";

class Modal extends Component<ModalState> {
    stateSelector(globalState: any): ModalState | undefined {
        return globalState[modalStateSelector];
    }

    template(): InnerHTML["innerHTML"] {
        return `
        <div class="modal__content" data-component="${this.getDisplayComponentName()}"></div>
        <div class="modal__background"></div>
        `
    }

    initialize() {
        this.addEvent('click', '.modal__background', closeModal);
    }

    mounted() {
        this.mountContent();
        this.displayModal();
    }

    mountContent() {

    }

    getDisplayComponentName() {
        const { view } = this.state || MODAL_INITIAL;
        switch (view) {
            case ModalView.NONE:
                return '';
            case ModalView.POSTING:
                return 'PostingAuction';
            case ModalView.SHOWING:
                return 'ShowingAuction';
        }
    }

    onStateChanged(prevLocalState: ModalState) {
        const { view, auction } = this.state || MODAL_INITIAL;
        if (view !== prevLocalState.view || auction !== auction) {
            this.render();
            return;
        }
        this.displayModal();
    }

    displayModal() {
        const { pop } = this.state || MODAL_INITIAL;
        if (pop)
            this.popModal();
        else
            this.closeModal();
    }
    popModal() {
        this.$target.classList.add('modal--pop');
    }
    closeModal() {
        this.$target.classList.remove('modal--pop');
    }
}

export default Modal;