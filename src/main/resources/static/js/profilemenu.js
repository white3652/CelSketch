var profilemenuBtn = document.querySelector('.profilemenu-btn');
var profilemenuContent = document.querySelector('.profilemenu-content');
var otherBtn = document.querySelector('.uploadmenu-btn');

profilemenuBtn.addEventListener('click', function (event) {
    event.stopPropagation();
    profilemenuContent.style.display = (profilemenuContent.style.display === 'block') ? 'none' : 'block';
});

otherBtn.addEventListener('click', function () {
    if (profilemenuContent.style.display === 'block') {
        profilemenuContent.style.display = 'none';
    }
});

document.addEventListener('click', function (event) {
    var targetElement = event.target;
    var isProfilemenuBtn = targetElement.matches('.profilemenu-btn') || targetElement.closest('.profilemenu-btn');
    var isProfilemenuContent = targetElement.matches('.profilemenu-content') || targetElement.closest('.profilemenu-content');

    if (!isProfilemenuBtn && !isProfilemenuContent) {
        if (profilemenuContent.style.display === 'block') {
            profilemenuContent.style.display = 'none';
        }
    }
});

document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/user/current')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('User not logged in');
            }
        })
        .then(data => {
            document.getElementById('username').textContent = data.userId;
            document.getElementById('hiddenUserId').value = data.userId;

            // 프로필 사진 설정
            var profileImage = document.querySelector('.profilemenu-btn');
            var subProfileImage = document.querySelector('#subprofile');
            var profileImageUrl = `/api/files/${data.profileFileName}`;
            profileImage.src = profileImageUrl;
            subProfileImage.src = profileImageUrl;

            // 회원탈퇴 버튼에 confirmDeletion 함수 추가
            var deleteButton = document.querySelector('.profilemenu-link[onclick^="confirmDeletion"]');
            if (deleteButton) {
                deleteButton.setAttribute('onclick', `confirmDeletion(event, '${data.userId}')`);
            }
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            window.location.href = "/user/login";
        });
});

function confirmDeletion(event, userId) {
    event.preventDefault();
    console.log("userId:", userId);
    if (confirm("정말로 탈퇴하시겠습니까?")) {
        fetch('/user/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId: userId })
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.success) {
                    window.location.href = "/user/logout";
                }
            })
            .catch(error => {
                alert("계정 삭제가 실패했습니다: " + error);
            });
    }
}
