function addToFavourites(event) {
    event.preventDefault(); // prevent the default form submission

    let heartIcon;
    if (event.target.classList.contains("addToFavorites")) {
        heartIcon = event.target.querySelector("i");
    } else {
        heartIcon = event.target;
    }

    const recipeId = event.target.closest(".addToFavorites").getAttribute('data-recipe-id'); // get the recipe ID from the data-attribute
    fetch(`/favourites/add?recipeId=${recipeId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ recipeId: recipeId })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // update the heart icon based on the response
                if (heartIcon.classList.contains("fa-heart-o")) {
                    heartIcon.classList.remove("fa-heart-o");
                    heartIcon.classList.add("fa-heart");
                } else {
                    heartIcon.classList.remove("fa-heart");
                    heartIcon.classList.add("fa-heart-o");
                }
            } else {
                const loginPopup = document.querySelector("#overlay");
                loginPopup.style.display = "block";
            }
        })
        .catch(error => console.error(error));
}


