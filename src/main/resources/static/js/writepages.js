document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;

    function showNextPage() {
        document.getElementById(`page${currentPage}`).style.display = 'none';
        currentPage++;
        document.getElementById(`page${currentPage}`).style.display = 'block';
    }

    document.getElementById('goToPage2').addEventListener('click', function () {
        showNextPage();
    });
});