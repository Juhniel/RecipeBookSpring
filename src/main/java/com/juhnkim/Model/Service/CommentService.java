package com.juhnkim.Model.Service;

import com.juhnkim.Model.CommentResponseDTO;
import com.juhnkim.Model.Entity.Comment;
import com.juhnkim.Model.Entity.Recipe;
import com.juhnkim.Model.Entity.User;
import com.juhnkim.Model.Repository.CommentRepository;
import com.juhnkim.Model.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public Comment addComment(User user, Long recipeId, String content) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Create a new Comment object
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setRecipe(recipe);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        // Save the comment
        return  commentRepository.save(comment);
    }

    public List<Comment> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipeRecipeId(recipeId);
    }

    public CommentResponseDTO commentToDTO(Comment comment, String formattedDate, String formattedTime) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setUsername(comment.getUser().getUsername());
        dto.setFormattedDate(formattedDate);
        dto.setFormattedTime(formattedTime);
        return dto;
    }
}
