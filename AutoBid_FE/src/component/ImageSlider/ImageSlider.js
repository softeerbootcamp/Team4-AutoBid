const slidesContainer = document.querySelector(".main__card-list__grid__item__img-group");
const slide = document.querySelector(".main__card-list__grid__item__img");
const prevButton = document.querySelector(".grid__item__img-swap-btn-group__btn-left");
const nextButton = document.querySelector(".grid__item__img-swap-btn-group__btn-right");

prevButton.addEventListener("click", () => {
    const slideWidth = slide.clientWidth;
    slidesContainer.scrollLeft -= slideWidth;
    console.log("💪prev clicked");
    console.log(slideWidth);
});

nextButton.addEventListener("click", () => {
    const slideWidth = slide.clientWidth;
    slidesContainer.scrollLeft += slideWidth;
    console.log("💪next clicked");
    console.log(slideWidth);
});
