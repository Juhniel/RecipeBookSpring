const form = document.getElementById('update-settings-form');
const username = document.getElementById('name');
const confirmUsername = document.getElementById('confirm_name');
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirm_password');

form.addEventListener('submit', function (event) {
    if (username.value.trim() !== '' || confirmUsername.value.trim() !== '') {
        username.required = true;
        confirmUsername.required = true;
    } else {
        username.required = false;
        confirmUsername.required = false;
    }

    if (password.value.trim() !== '' || confirmPassword.value.trim() !== '') {
        password.required = true;
        confirmPassword.required = true;
    } else {
        password.required = false;
        confirmPassword.required = false;
    }
});