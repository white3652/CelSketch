document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;

    function showNextPage() {
        document.getElementById(`page${currentPage}`).style.display = 'none';
        currentPage++;
        document.getElementById(`page${currentPage}`).style.display = 'block';
    }

    document.getElementById('goToPage2').addEventListener('click', function () {
        if (validatePage1()) {
            showNextPage();
        }
    });
});

function validatePage1() {
    let isValid = true;

    // Clear previous error messages
    document.getElementById('title-error').style.display = 'none';
    document.getElementById('content-error').style.display = 'none';

    // Validate title
    const title = document.getElementById('title-input').value;
    if (title.trim() === '') {
        document.getElementById('title-error').innerText = '제목을 입력해주세요.';
        document.getElementById('title-error').style.display = 'block';
        isValid = false;
    }

    // Validate content
    const content = document.getElementById('content-input').value;
    if (content.trim() === '') {
        document.getElementById('content-error').innerText = '내용을 입력해주세요.';
        document.getElementById('content-error').style.display = 'block';
        isValid = false;
    }

    return isValid;
}

function validatePage2() {
    let isValid = true;

    // Clear previous error messages
    document.getElementById('tag-error').style.display = 'none';
    document.getElementById('age-error').style.display = 'none';
    document.getElementById('ai-error').style.display = 'none';
    document.getElementById('public-error').style.display = 'none';
    document.getElementById('original-error').style.display = 'none';

    // Validate tag
    const tag = document.getElementById('tag-input').value;
    if (tag.trim() === '') {
        document.getElementById('tag-error').innerText = '태그를 입력해주세요.';
        document.getElementById('tag-error').style.display = 'block';
        isValid = false;
    }

    // Validate age
    const age = document.querySelector('input[name="age"]:checked');
    if (!age) {
        document.getElementById('age-error').innerText = '연령 제한을 선택해주세요.';
        document.getElementById('age-error').style.display = 'block';
        isValid = false;
    }

    // Validate AI
    const ai = document.querySelector('input[name="ai"]:checked');
    if (!ai) {
        document.getElementById('ai-error').innerText = 'AI 생성 여부를 선택해주세요.';
        document.getElementById('ai-error').style.display = 'block';
        isValid = false;
    }

    // Validate visibility
    const visibility = document.querySelector('input[name="pbc"]:checked');
    if (!visibility) {
        document.getElementById('public-error').innerText = '공개 범위를 선택해주세요.';
        document.getElementById('public-error').style.display = 'block';
        isValid = false;
    }

    // Validate original
    const original = document.querySelector('input[name="org"]:checked');
    if (!original) {
        document.getElementById('original-error').innerText = '오리지널 여부를 선택해주세요.';
        document.getElementById('original-error').style.display = 'block';
        isValid = false;
    }

    return isValid;
}