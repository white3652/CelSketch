document.addEventListener("DOMContentLoaded", function () {
    // 각 페이지에서 필요한 변수 선언
    let currentPage = 1;

    // 다음 페이지로 이동하는 함수
    function showNextPage() {
        document.getElementById(`page${currentPage}`).style.opacity = '0';
        setTimeout(function () {
            document.getElementById(`page${currentPage}`).style.display = 'none';
            currentPage++;
            document.getElementById(`page${currentPage}`).style.display = 'block';
            document.getElementById(`page${currentPage}`).style.opacity = '1';
            checkFormValidity(); // 페이지 이동 후에도 버튼 상태를 확인합니다.
        }, 500); // 투명도 트랜지션 시간과 일치해야 합니다.
    }

    // 페이지 이동 버튼 클릭 시 다음 페이지로 이동
    document.getElementById('goToPage2').addEventListener('click', function () {
        showNextPage();
    });

    // 초기에 첫 번째 페이지를 활성화
    document.getElementById('page1').classList.add('active');
    document.getElementById('page1').style.opacity = '1';

    // 페이지 이동 버튼 활성화 여부 확인
    // function checkFormValidity() {
    //     const submitButton = document.getElementById('submit');
    //     if (currentPage === 2) {
    //         submitButton.removeAttribute('disabled');
    //     } else {
    //         submitButton.setAttribute('disabled', 'disabled');
    //     }
    // }
});