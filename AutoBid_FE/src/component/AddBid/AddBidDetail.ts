import Component from "../../core/component";
import {ModalState, modalStateSelector} from "../../store/modal";
import {disconnectSocketSession} from "../../api/live";
import {AddBid} from "../../model/addBid";
import "./addbiddetail.css";

class AddBidDetail extends Component<ModalState> {
    stateSelector(globalState: any): ModalState | undefined {
        return globalState[modalStateSelector];
    }

    // TODO: add-bid__car-type__container should be filled with owner's cars
    template(): InnerHTML["innerHTML"] {
        return `
        <div class="add-bid">
            <button class="add-bid__close-button">
                <i class="fa-solid fa-times"></i>
            </button>
            <h2 class="add-bid__title">경매 등록하기</h2>
            <hr>
            <div class="add-bid__add-image__container">
                <button class="add-bid__swap-button-left"><i
                        class="fas fa-chevron-left fa-lg slider_1"></i>
                </button>
                <div class="add-bid__images-container">
                    <label class="add-bid__image-container">
                        <i class="add-bid__image fa-solid fa-camera"></i>
                    </label>
                    <input type="file" class="add-bid__file-upload-input" accept="image/*" multiple="multiple"/>
                </div>
                <button class="add-bid__swap-button-right">
                    <i class="fas fa-chevron-right fa-lg slider-1"></i>
                </button>
            </div>
            <hr>
            <p class="add-bid__label add-bid__bid-title__label">경매 제목</p>
            <label>
                <input type="text" class="add-bid__bid-title__input">
            </label>
            <hr>
            <p class="add-bid__label add-bid__car-type__label">차종</p>
            <label class="add-bid__car-type__container">
                <select class="add-bid__car-type__car-list">
                    <option value="test_id_1">아반떼</option>
                    <option value="test_id_2">소나타</option>
                    <option value="test_id_3">그랜져</option>
                    <option value="test_id_4">람보르기니 아벤타도르</option>
                </select>
            </label>
            <hr>
            <p class="add-bid__label add-bid__bid_start-time__label">경매 시작 시간</p>
            <label>
                <input type="datetime-local" class="add-bid__bid-start-time__input">
            </label>
            <p class="add-bid__label add-bid__bid-end-time__label">경매 종료 시간</p>
            <label>
                <input type="datetime-local" class="add-bid__bid-end-time__input">
            </label>
            <hr>
            <p class="add-bid__label add-bid__bid-start-price__label">경매 시작 가격</p>
            <label>
                <input type="number" class="add-bid__bid-start-price__input">
            </label>
            <hr>
            <button type="submit" class="add-bid__bid-submit__button">등록</button>
        </div>
    `;
    }

    initialize() {
    }

    mounted() {
        const uploadInput = document.querySelector('.add-bid__file-upload-input') as HTMLInputElement;
        let files;

        // uploading images to page
        this.addEvent('click', '.add-bid__image', async () => {
            uploadInput.click();
        });
        uploadInput.onchange = () => {
            files = uploadInput.files;
            if (files == null) return;
            this.addImages(files).then(this.setImageDeleteButton).then();
        }

        this.addEvent('click', '.add-bid__bid-submit__button', async () => {
            console.log("CLICKED");
        });

        const slideContainer = document.querySelector(".add-bid__images-container")!;
        const imageWidth = 68;
        this.addEvent('click', '.add-bid__swap-button-left', () => {
            slideContainer.scrollLeft -= imageWidth;
        });
        this.addEvent('click', '.add-bid__swap-button-right', () => {
            slideContainer.scrollLeft += imageWidth;
        });
    }

    async addImages(files: FileList) {
        const imagesContainer = document.querySelector(".add-bid__images-container")!;

        if (files == null) return;
        for (let file of files) {
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                imagesContainer.innerHTML += `<div class="add-bid__image-container">
            <button class="add-bid__image-delete"><i class="fa-solid fa-circle-xmark"></i></button>
            <img class="add-bid__image" src="${reader.result}" alt="">
            </div>`;
            }
        }
    }

    cameraEvent(files: FileList) {
        const imagesContainer = document.querySelector(".add-bid__images-container")!;

        if (files == null) return;
        for (let file of files) {
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                imagesContainer.innerHTML += `<div class="add-bid__image-container">
                <button class="add-bid__image-delete"><i class="fa-solid fa-circle-xmark"></i></button>
                <img class="add-bid__image" src="${reader.result}" alt="">
                </div>`;
            }
        }
    }

    setImageDeleteButton() {
        const deleteButtons = document.getElementsByClassName('add-bid__image-delete');
        for (let button of deleteButtons) {
            button.addEventListener("click", () => {
                let parent = button.parentElement!;
                let imagesContainer = button.closest(".add-bid__images-container")!;
                imagesContainer.removeChild(parent);
            })
        }
    }

    submitEvent() {

    }
}

export default AddBidDetail;