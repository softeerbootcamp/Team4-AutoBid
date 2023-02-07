// CODE FOR MULTIPLE CARD HANDLER
const slidesContainers = document.getElementsByClassName("main__card-list__grid__item__img-group");
const slides = document.getElementsByClassName("main__card-list__grid__item__img");
const prevButtons = document.getElementsByClassName("grid__item__img-swap-btn-group__btn-left");
const nextButtons = document.getElementsByClassName("grid__item__img-swap-btn-group__btn-right");

for (let i = 0; i < slidesContainers.length; i++) {
    const slideWidth = slides[i].clientWidth * slidesContainers[i].children.length;
    prevButtons[i].addEventListener("click", () => {
        slidesContainers[i].scrollLeft -= slideWidth;
    });
    nextButtons[i].addEventListener("click", () => {
        slidesContainers[i].scrollLeft += slideWidth;
    });
}

// BELOW CODE IS FOR SINGLE CARD HANDLER
// const slidesContainer = document.querySelector(".main__card-list__grid__item__img-group");
// const slide = document.querySelector(".main__card-list__grid__item__img");
// const prevButton = document.querySelector(".grid__item__img-swap-btn-group__btn-left");
// const nextButton = document.querySelector(".grid__item__img-swap-btn-group__btn-right");
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