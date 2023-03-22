const fileInput = document.getElementById('profile-pic');
const imagePreview = document.getElementById('imagePreview');

fileInput.addEventListener('change', function() {
    let file = this.files[0];
    let reader = new FileReader();

    reader.addEventListener('load', function() {
        imagePreview.setAttribute('src', reader.result);
        imagePreview.style.display = 'block';
    });
    reader.readAsDataURL(file);
});