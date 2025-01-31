document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;

        // Basic password validation
        if (password.length < 6) {
            e.preventDefault();
            alert('Password must be at least 6 characters long');
            return;
        }

        // Add password strength validation if needed
        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasNumbers = /\d/.test(password);

        if (!(hasUpperCase && hasLowerCase && hasNumbers)) {
            e.preventDefault();
            alert('Password must contain at least one uppercase letter, one lowercase letter, and one number');
            return;
        }
    });
});