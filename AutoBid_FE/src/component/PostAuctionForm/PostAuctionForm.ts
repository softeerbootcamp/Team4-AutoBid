import Component from "../../core/component";
import {ModalState, modalStateSelector} from "../../store/modal";
import "./postauctionform.css";
import {requestPostAuction} from "../../api/auction";

const getPriceStr = (price: number) => {
    if (price <= 0)
        return '';

    let ret: string[] = [];

    const uk = Math.floor(price / 10000);
    if (uk) {
        ret.push(`${uk}억`);
        price -= uk * 10000;
    }

    const chun = Math.floor(price / 1000);
    if (chun) {
        ret.push(`${chun}천`);
        price -= chun * 1000;
    }

    const beck = Math.floor(price / 100);
    if (beck) {
        ret.push(`${beck}백`);
        price -= beck * 100;
    }

    const sip = Math.floor(price / 10);
    if (sip) {
        ret.push(`${sip}십`);
        price -= sip * 10;
    }

    const man = price;
    if (man) {
        ret.push(`${man}`);
    }

    return `${ret.join(' ')}${ret.length > 1 ? '만' : ''}원`;
}

class PostAuctionForm extends Component<ModalState> {
    stateSelector(globalState: any): ModalState | undefined {
        return globalState[modalStateSelector];
    }

    template(): InnerHTML["innerHTML"] {
        return `
        <h2 class="post-auction-form__title">경매 등록하기</h2>
        <div class="post-auction-form__add-image__container">
            <div class="post-auction-form__images-container">
                <button class="post-auction-form__image-container__add-btn"><i class="fa-solid fa-camera"></i></button>
                <input type="file" class="post-auction-form__file-upload-input" accept="image/*" multiple="multiple"/>
                <div class="post-auction-form__images-container__actual-img-holder"></div>
            </div>
        </div>
        <p class="post-auction-form__label">경매 제목</p>
        <input type="text" class="post-auction-form__input-text post-auction-form__auction-title__input">
        <p class="post-auction-form__label">차종</p>
        <select class="post-auction-form__car-select">
            <option value="test_id_1">아반떼</option>
            <option value="test_id_2">소나타</option>
            <option value="test_id_3">그랜져</option>
            <option value="test_id_4">람보르기니 아벤타도르</option>
        </select>
        <div class="post-auction-form__time-container">
            <div class="post-auction-form__time-container__item">
                <p class="post-auction-form__label">경매 시작 시간</p>
                <input type="datetime-local" class="post-auction-form__input-time post-auction-form__auction-start-time__input">
            </div>
            <div class="post-auction-form__time-container__item">
                <p class="post-auction-form__label">경매 종료 시간</p>
                <input type="datetime-local" class="post-auction-form__input-time post-auction-form__auction-end-time__input">
            </div>
        </div>
        <p class="post-auction-form__label">경매 시작 가격</p>
        <div class="post-auction-form__price-container">
            <input type="number" class="post-auction-form__input-text post-auction-form__auction-start-price__input">
            만원
            <p class="post-auction-form__auction-start-price__str"></p>
        </div>
        <button type="submit" class="post-auction-form__auction-submit__button">등록</button>
    `;
    }

    initialize() {
        this.addEvent('click', '.post-auction-form__image-container__add-btn', () => {
            this.openFileSelector();
        });
        this.addEvent('change', '.post-auction-form__file-upload-input', () => {
            this.fetchFiles();
            this.addImages();
        });
        this.addEvent('change', '.post-auction-form__auction-start-price__input', ({ target }) => {
            const value = (target as HTMLInputElement).value;
            console.log(parseInt(value));
            this.updatePriceStr(parseInt(value));
        });
    }

    private files: FileList|null = null;

    openFileSelector() {
        const uploadInput = document.querySelector('.post-auction-form__file-upload-input') as HTMLInputElement;
        uploadInput.click();
    }

    fetchFiles() {
        const uploadInput = document.querySelector('.post-auction-form__file-upload-input') as HTMLInputElement;
        this.files = uploadInput.files;
    }

    addImages() {
        const $imageHolder = document.querySelector(".post-auction-form__images-container__actual-img-holder")!;
        $imageHolder.innerHTML = '';

        if (!this.files) return;
        [...this.files].forEach((file, idx) => {
            const reader = new FileReader();
            reader.onload = () => {
                console.log(idx);
                $imageHolder.innerHTML += `
                    <div class="post-auction-form__image-container">
                        <button class="post-auction-form__image-delete"><i class="fa-solid fa-circle-xmark"></i></button>
                        <img class="post-auction-form__image" src="${reader.result}" alt="">
                    </div>`
            }
            reader.readAsDataURL(file);
        });
    }

    updatePriceStr(value: number) {
        const $priceStr = this.$target.querySelector('.post-auction-form__auction-start-price__str') as HTMLElement;
        $priceStr.innerText = getPriceStr(value);
    }

    getauctionDetails() {
        const fileList = (document.querySelector('.post-auction-form__file-upload-input') as HTMLInputElement).files!;
        let auctionTitle = (document.querySelector('.post-auction-form__auction-title__input') as HTMLInputElement).value;
        let carId = (document.querySelector('.post-auction-form__car-type__car-list') as HTMLSelectElement).value as unknown as number;
        let auctionStartTime = (document.querySelector('.post-auction-form__auction-start-time__input') as HTMLInputElement).value;
        let auctionEndTime = (document.querySelector('.post-auction-form__auction-end-time__input') as HTMLInputElement).value;
        let auctionStartPrice = ((document.querySelector('.post-auction-form__auction-start-price__input') as HTMLInputElement).value as unknown) as number;

        requestPostAuction({
            fileList, carId, auctionTitle, auctionStartTime, auctionEndTime, auctionStartPrice
        }).then((result) => {
            console.log(result);
        });
    }
}

export default PostAuctionForm;