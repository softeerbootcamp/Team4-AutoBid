var slideContainer = document.getElementsByClassName("my-page__main__card-slider");
var cardHolders = document.getElementsByClassName("my-page__card-list__holder");
var leftButton = document.getElementsByClassName("my-page__swap-button-left");
var rightButton = document.getElementsByClassName("my-page__swap-button-right");
var wholeCardWidth = document.querySelector(".card-container__card-item").clientWidth + 16;
var _loop_1 = function (i) {
    leftButton[i].addEventListener("click", function () {
        cardHolders[i].scrollLeft -= wholeCardWidth;
    });
    rightButton[i].addEventListener("click", function () {
        cardHolders[i].scrollLeft += wholeCardWidth;
    });
};
for (var i = 0; i < slideContainer.length; i++) {
    _loop_1(i);
}
