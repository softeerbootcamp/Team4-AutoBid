const cameraButton = document.querySelector(".fa-camera")!;
const uploadInput = document.querySelector(".add-bid__file-upload-input") as HTMLInputElement;
const imagesContainer = document.querySelector(".add-bid__images-container")!;

cameraButton.addEventListener("click", () => {
    uploadInput.click();
});

let files;
uploadInput.onchange = () => {
    files = uploadInput.files;

    if (files == null) return;
    addImages(files).then(setImageDeleteButton).then();

}

async function addImages(files: FileList) {
    if (files == null) return;
    for (let file of files) {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function () {
            imagesContainer.innerHTML += `<div class="add-bid__image-container">
            <button class="add-bid__image-delete"><i class="fa-solid fa-circle-xmark"></i></button>
            <img class="add-bid__image" src="${reader.result}" alt="">
            </div>`;
        }
    }
}

function setImageDeleteButton() {
    const deleteButtons = document.getElementsByClassName("add-bid__image-delete");
    for (let button of deleteButtons) {
        button.addEventListener("click", () => {
            let parent = button.parentElement!;
            let imagesContainer = button.closest(".add-bid__images-container")!;
            imagesContainer.removeChild(parent);
        });
    }
}

function setImageZoomButton() {
    const images = document.getElementsByClassName("add-bid__image");
    for (let image of images) {
        image.addEventListener("click", () => {
            // TODO: set a popup(new window) with original size image
        });
    }
}