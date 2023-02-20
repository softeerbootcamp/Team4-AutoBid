const _slideContainers = document.getElementsByClassName("my-page__card-slider")!;
const _cardHolders = document.getElementsByClassName("my-page__card-list__holder");
const _leftButton = document.getElementsByClassName("my-page__swap-button-left") as HTMLCollectionOf<Element>;
const _rightButton = document.getElementsByClassName("my-page__swap-button-right") as HTMLCollectionOf<Element>;
const _cardWidth = document.querySelector(".card-container__card-item")!.clientWidth + 16;

for (let i = 0; i < _slideContainers.length; i++) {
    _leftButton[i].addEventListener("click", () => {
        _cardHolders[i].scrollLeft -= _cardWidth;
    });
    _rightButton[i].addEventListener("click", () => {
        _cardHolders[i].scrollLeft += _cardWidth;
    });
}