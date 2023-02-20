const slideContainer = document.querySelector(".add-bid__images-container")!;
const leftButton = document.querySelector(".add-bid__swap-button-left")!;
const rightButton = document.querySelector(".add-bid__swap-button-right")!;
const imageWidth = document.querySelector(".add-bid__images-container")!.children[0].clientWidth;

leftButton.addEventListener("click", () => {
    slideContainer.scrollLeft -= imageWidth;
});

rightButton.addEventListener("click", () => {
    slideContainer.scrollLeft += imageWidth;
});