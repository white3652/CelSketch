document.addEventListener("DOMContentLoaded", function () {
    const backgroundContainer = document.querySelector(".background-container");
    const currentImage = document.querySelector(".current");
    const nextImage = document.querySelector(".next");

    const totalImages = 8;

    function getRandomImageName() {
        // 1부터 totalImages까지의 랜덤한 숫자를 생성합니다.
        const randomImageNumber = Math.floor(Math.random() * totalImages) + 1;
        // 생성된 숫자를 이미지 파일 이름에 포함시켜 경로를 반환합니다.
        return `/imagefile/${randomImageNumber}.jpg`;
    }

    function changeBackground() {
        const randomImageUrl = getRandomImageName();

        nextImage.src = randomImageUrl;
        fadeIn(nextImage);
    }

    function fadeIn(element) {
        let opacity = 0;
        element.style.opacity = opacity;
        element.style.transition = "opacity 1s";

        function animate() {
            opacity += 0.01;
            element.style.opacity = opacity;

            if (opacity < 1) {
                requestAnimationFrame(animate);
            } else {
                currentImage.src = nextImage.src;
                currentImage.style.opacity = 1;
                nextImage.style.opacity = 0;
            }
        }

        requestAnimationFrame(animate);
    }

    changeBackground();

    setInterval(changeBackground, 10000);
});