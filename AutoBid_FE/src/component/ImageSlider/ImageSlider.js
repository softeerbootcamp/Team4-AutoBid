// CODE FOR MULTIPLE CARD HANDLER
var slidesContainers = document.getElementsByClassName("card-item__img-container");
var leftButtons = document.getElementsByClassName("card-item__swap-button-left");
var rightButtons = document.getElementsByClassName("card-item__swap-button-right");
var cardWidth = document.querySelector(".card-container__card-item").clientWidth;
var _loop_1 = function (i) {
    leftButtons[i].addEventListener("click", function () {
        slidesContainers[i].scrollLeft -= cardWidth;
    });
    rightButtons[i].addEventListener("click", function () {
        slidesContainers[i].scrollLeft += cardWidth;
    });
};
// TODO: find better way than for looping
for (var i = 0; i < slidesContainers.length; i++) {
    _loop_1(i);
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
