import Component from "../../core/component";
import {closeModal, ModalState, modalStateSelector} from "../../store/modal";
import "./postauctionform.css";
import Toast from "../../core/toast";
import {AuctionForm} from "../../model/auction";
import {requestMyCarList} from "../../api/my";
import {CarState} from "../../model/car";
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
            <option value="">차량을 선택하세요</option>
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
        <button class="post-auction-form__auction-submit__button">등록</button>
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
            this.updatePriceStr(parseInt(value));
        });
        this.addEvent('click', '.post-auction-form__auction-submit__button', () => {
            this.post();
        });
    }

    mounted() {
        requestMyCarList().then(carList => {
            if (!carList) return;
            const notForSaleCarList = carList.filter(carInfo => carInfo.state === CarState.NOT_FOR_SALE);
            if (!notForSaleCarList.length) return;
            const $carSelect = this.$target.querySelector('.post-auction-form__car-select') as HTMLSelectElement;
            $carSelect.innerHTML += notForSaleCarList.map(carInfo => `
                <option value="${carInfo.carId}">${carInfo.name} ${carInfo.sellName}</option>
            `).join('');
        });
    }

    private files: FileList|null = null;

    openFileSelector() {
        const uploadInput = this.$target.querySelector('.post-auction-form__file-upload-input') as HTMLInputElement;
        uploadInput.click();
    }

    fetchFiles() {
        const uploadInput = this.$target.querySelector('.post-auction-form__file-upload-input') as HTMLInputElement;
        this.files = uploadInput.files;
    }

    addImages() {
        const $imageHolder = this.$target.querySelector(".post-auction-form__images-container__actual-img-holder")!;
        $imageHolder.innerHTML = '';

        if (!this.files) return;
        [...this.files].forEach((file, idx) => {
            const reader = new FileReader();
            reader.onload = () => {
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

    validateAndGetForm(): AuctionForm|null {
        if (!this.files || !this.files.length) {
            Toast.show('사진이 등록되지 않았습니다', 1000);
            return null;
        }

        const $auctionTitleInputText
            = this.$target.querySelector('.post-auction-form__auction-title__input') as HTMLInputElement;
        const auctionTitle = $auctionTitleInputText.value;
        if (!auctionTitle || !auctionTitle.length) {
            Toast.show('제목이 비어있습니다', 1000);
            $auctionTitleInputText.focus();
            return null;
        }

        const $carSelect
            = this.$target.querySelector('.post-auction-form__car-select') as HTMLSelectElement;
        const carId = $carSelect.value;
        const carIdNumber = parseInt(carId);
        if (!carId || !carId.length || isNaN(carIdNumber)) {
            Toast.show('차량을 선택하지 않았습니다', 1000);
            $carSelect.focus();
            return null;
        }

        const $startTimeInput
            = this.$target.querySelector('.post-auction-form__auction-start-time__input') as HTMLInputElement;
        const startTime = $startTimeInput.value;
        if (!startTime || !startTime.length) {
            Toast.show('올바르지 않은 시작 시간입니다.', 1000);
            $startTimeInput.focus();
            return null;
        }
        if (new Date(startTime).getTime() <= Date.now()) {
            Toast.show('시작 시간은 현재보다 이전일 수 없습니다', 1000);
            $startTimeInput.focus();
            return null;
        }

        const $endTimeInput
            = this.$target.querySelector('.post-auction-form__auction-end-time__input') as HTMLInputElement;
        const endTime = $endTimeInput.value;
        if (!endTime || !endTime.length) {
            Toast.show('올바르지 않은 종료 시간입니다.', 1000);
            $endTimeInput.focus();
            return null;
        }
        if (new Date(endTime).getTime() <= new Date(startTime).getTime()) {
            Toast.show('종료 시간은 시작 시간보다 이전일 수 없습니다', 1000);
            $endTimeInput.focus();
            return null;
        }

        const $startPriceInput
            = this.$target.querySelector('.post-auction-form__auction-start-price__input') as HTMLInputElement;
        const startPrice = $startPriceInput.value;
        if (!startPrice || !startPrice.length) {
            Toast.show('올바르지 않은 가격입니다.', 1000);
            $startPriceInput.focus();
            return null;
        }
        const priceNumber = parseInt(startPrice);
        if (isNaN(priceNumber) || priceNumber <= 0) {
            Toast.show('가격은 자연수로 입력되어야 합니다', 1000);
            $startPriceInput.focus();
            return null;
        }

        return {
            auctionEndTime: endTime.replace('T', ' '),
            auctionStartPrice: priceNumber,
            auctionStartTime: startTime.replace('T', ' '),
            auctionTitle: auctionTitle,
            carId: carIdNumber,
            multipartFileList: this.files
        };
    }

    post() {
        const auctionForm = this.validateAndGetForm();
        if (auctionForm) {
            requestPostAuction(auctionForm, true).then(result => {
                if (result) {
                    Toast.show('경매를 등록했습니다', 1000);
                    closeModal();
                } else {
                    Toast.show('경매를 등록에 실패했습니다', 1000);
                }
            });
        }
    }
}

export default PostAuctionForm;