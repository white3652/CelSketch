document.getElementById('delete-button').addEventListener('click', function() {
    var bIdx = this.getAttribute('data-bidx');
    if (confirm('정말 이 글을 삭제하시겠습니까?')) {
        fetch('/board/delete/' + bIdx, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('글 삭제에 실패했습니다.');
            }
        }).then(function(message) {
            alert(message);
            window.location.href = '/board';
        }).catch(function(error) {
            alert(error.message);
        });
    }
});