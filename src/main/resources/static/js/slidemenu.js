document.addEventListener('DOMContentLoaded', function () {
    const openBtn = document.getElementById('openBtn');
    const closeBtn = document.getElementById('closeBtn');
    const slideMenu = document.getElementById('slideMenu');
    const smm = document.getElementById('smmm')

    openBtn.addEventListener('click', function () {
        slideMenu.style.left = '0';
        smm.style.opacity = '1';
    });

    closeBtn.addEventListener('click', function () {
        slideMenu.style.left = '-300px';
        smm.style.opacity = '0';
    });
});