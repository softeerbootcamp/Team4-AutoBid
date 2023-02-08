// CODE FOR MULTIPLE CARD HANDLER
const slidesContainers = document.getElementsByClassName("card-item__img-container");
const leftButtons = document.getElementsByClassName("card-item__swap-button-left");
const rightButtons = document.getElementsByClassName("card-item__swap-button-right");
const cardWidth = document.querySelector(".card-container__card-item")!.clientWidth;

// TODO: find better way than for looping
for (let i = 0; i < slidesContainers.length; i++) {
    leftButtons[i].addEventListener("click", () => {
        slidesContainers[i].scrollLeft -= cardWidth;
    });
    rightButtons[i].addEventListener("click", () => {
        slidesContainers[i].scrollLeft += cardWidth;
    });
}

// BELOW CODE IS FOR SINGLE CARD HANDLER
// TODO: refactoring/renaming required for single card handler
// const slidesContainer = document.querySelector(".card-item__img-container");
// const slide = document.querySelector(".card-item__img");
// const prevButton = document.querySelector(".card-item__swap-button-left");
// const nextButton = document.querySelector(".card-item__swap-button-right");
//
// prevButton.addEventListener("click", () => {
//     const slideWidth = slide.clientWidth;
//     slidesContainer.scrollLeft -= slideWidth;
// });
//
// nextButton.addEventListener("click", () => {
//     const slideWidth = slide.clientWidth;
//     slidesContainer.scrollLeft += slideWidth;
// });