document.addEventListener('DOMContentLoaded', function() {
    function addUploadMenuListeners() {
        var menu = document.querySelector('.uploadmenu-content');
        var uploadButton = document.querySelector('.uploadmenu-btn');

        if (menu && uploadButton) {
            document.addEventListener('click', function(event) {
                if (event.target !== uploadButton && !uploadButton.contains(event.target) && event.target !== menu && !menu.contains(event.target)) {
                    menu.style.display = 'none';
                }
            });

            uploadButton.addEventListener('click', function() {
                toggleUploadMenu();
            });

            menu.querySelectorAll('.menu-item').forEach(function(item) {
                item.addEventListener('click', function() {
                    window.location.href = item.getAttribute('href');
                });
            });

            function toggleUploadMenu() {
                menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
            }
        } else {
            console.error('Upload button or menu not found in the DOM.');
        }
    }

    addUploadMenuListeners();

    var observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            mutation.addedNodes.forEach(function(node) {
                if (node.nodeType === 1) {
                    if (node.matches('.uploadmenu-content') || node.matches('.uploadmenu-btn') || node.querySelector('.uploadmenu-content') || node.querySelector('.uploadmenu-btn')) {
                        addUploadMenuListeners();
                    }
                }
            });
        });
    });

    observer.observe(document.body, {
        childList: true,
        subtree: true
    });
});