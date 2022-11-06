package com.effynet.blogger.comments.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class CommentService {

    List<Comment> comments = new ArrayList<>();

    public Comment createComment(Comment newComment, String postId) {
        String newId = java.util.UUID.randomUUID().toString();
        newComment.setId(newId);
        newComment.setPostId(postId);

        comments.add(newComment);

        return newComment;
    }

    public List<Comment> getCommentsByPost(String postId) {
        List<Comment> commentsByPost = comments.stream().filter(c -> c.getPostId().equals(postId)).toList();
        return commentsByPost;
    }
}
