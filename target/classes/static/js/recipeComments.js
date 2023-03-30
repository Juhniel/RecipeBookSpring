// document.addEventListener("DOMContentLoaded", function () {
//     const commentForm = document.getElementById("commentForm");
//
//     commentForm.addEventListener("submit", function (event) {
//         event.preventDefault();
//
//         const formData = new FormData(commentForm);
//         const recipeId = formData.get("recipeId");
//         const content = formData.get("comment"); // Change this line
//
//         fetch("/addComment", {
//             method: "POST",
//             body: new URLSearchParams(formData),
//             headers: {
//                 "Content-Type": "application/x-www-form-urlencoded"
//             }
//         }).then(response => {
//             if (response.ok) {
//                 window.location.href = "/recipe/" + recipeId;
//             } else {
//                 // Handle errors, display a message or alert, etc.
//             }
//         }).catch(error => {
//             console.error("Error posting comment:", error);
//         });
//     });
// });


// NEW COMMENT JS

document.addEventListener("DOMContentLoaded", function () {
    const commentForm = document.getElementById("commentForm");

    commentForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(commentForm);
        const recipeId = formData.get("recipeId");
        const content = formData.get("comment");

        fetch("/addComment", {
            method: "POST",
            body: new URLSearchParams(formData),
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    const commentList = document.querySelector('.comment-list ul');
                    const newComment = document.createElement('li');

                    newComment.innerHTML = `
                    <div class="comment-header">
                        <div>
                            <strong class="username">${data.comment.user.username}</strong>
                            <!-- Add social icons here, if needed -->
                        </div>
                        <div class="date-time">
                        <span>${data.comment.formattedDate}</span><br>
                            <span>${data.comment.formattedTime}</span>
                        </div>
                    </div>
                    <p class="comment-content">${data.comment.content}</p>
                  `;
                    commentList.appendChild(newComment);
                    commentForm.reset();
                    console.log("yes")
                } else {
                    // Handle errors, display a message or alert, etc.
                    console.log("nope");
                }
            })
            .catch(error => {
                console.error("Error posting comment:", error);
            });
    });
});