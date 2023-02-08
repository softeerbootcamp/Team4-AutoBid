var slideContainer = document.querySelector(".add-bid__images-container");
var leftButton = document.querySelector(".add-bid__swap-button-left");
var rightButton = document.querySelector(".add-bid__swap-button-right");
var imageWidth = document.querySelector(".add-bid__images-container").children[0].clientWidth;
leftButton.addEventListener("click", function () {
    slideContainer.scrollLeft -= imageWidth;
});
rightButton.addEventListener("click", function () {
    slideContainer.scrollLeft += imageWidth;
});
