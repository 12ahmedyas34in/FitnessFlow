document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(e) {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(e) {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        if (!username || !password) {
            e.preventDefault();
            alert('Please fill in all fields');
            return;
        }
    });

    // Check for URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = document.querySelector('.alert-error');
        if (errorMessage) {
            errorMessage.style.display = 'block';
        }
    }
});
        if (!username || !password) {
            e.preventDefault();
            alert('Please fill in all fields');
            return;
        }
    });

    // Check for URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = document.querySelector('.alert-error');
        if (errorMessage) {
            errorMessage.style.display = 'block';
        }
    }
});