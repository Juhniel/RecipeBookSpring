let userIcon = document.getElementById("user-icon");
let loginForm = document.getElementById("loginForm");
let profileForm = document.getElementById("profileForm");

document.addEventListener("DOMContentLoaded", checkLoginStatus);
userIcon.addEventListener("click", toggleLoginForm);

function checkLoginStatus() {
    fetch('/checkSession')
        .then(response => response.json())
        .then(data => {
            if (data.sessionId) {
                window.isLoggedIn = true;
            } else {
                window.isLoggedIn = false;
            }
        })
        .catch(error => console.error(error));
}

function toggleLoginForm() {
    if (window.isLoggedIn) {
        if (profileForm.style.display === "none") {
            profileForm.style.display = "block";
        } else {
            profileForm.style.display = "none";
        }
    } else {
        if (loginForm.style.display === "none") {
            loginForm.style.display = "block";
        } else {
            loginForm.style.display = "none";
        }
    }
}

function toggleDropdown() {
    let dropdownMenu = document.getElementById("dropdown-menu");
    let logoImg = document.querySelector('.dropdown img');

    if (dropdownMenu.style.display === "none") {
        dropdownMenu.style.display = "block";
        logoImg.src = "https://recept.se/assets/images/menu/close.svg";
    } else {
        dropdownMenu.style.display = "none";
        logoImg.src = "https://recept.se/assets/images/menu/hamburger.svg";
    }
}

function toggleSearchField() {
    let searchField = document.querySelector('.search-field');
    if (searchField.style.display === 'block') {
        searchField.style.display = 'none';
    } else {
        searchField.style.display = 'block';
    }
}

