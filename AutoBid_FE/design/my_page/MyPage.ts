const slideContainer = document.getElementsByClassName("my-page__card-slider")!;
const cardHolders = document.getElementsByClassName("my-page__card-list__holder");
const leftButton = document.getElementsByClassName("my-page__swap-button-left") as HTMLCollectionOf<Element>;
const rightButton = document.getElementsByClassName("my-page__swap-button-right") as HTMLCollectionOf<Element>;
const cardWidth = document.querySelector(".card-container__card-item")!.clientWidth + 16;

for (let i = 0; i < slideContainer.length; i++) {
    leftButton[i].addEventListener("click", () => {
        cardHolders[i].scrollLeft -= cardWidth;
    });
    rightButton[i].addEventListener("click", () => {
        cardHolders[i].scrollLeft += cardWidth;
    });
}