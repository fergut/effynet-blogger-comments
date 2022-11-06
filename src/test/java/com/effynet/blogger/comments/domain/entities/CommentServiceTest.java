package com.effynet.blogger.comments.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCommentTest() {
        String postId = "1000";
        CommentService commentService = new CommentService();

        Comment newComment = new Comment();
        newComment.setContent("First comment");

        Comment comment =  commentService.createComment(newComment, postId);
        assertNotNull(comment.getId());
        assertEquals("First comment", comment.getContent());
        assertEquals(postId, comment.getPostId());

        List<Comment> comments = commentService.getCommentsByPost(postId);

        Comment firstComment = comments.stream().filter(c -> c.getId().equals(comment.getId()) && c.getPostId().equals(comment.getPostId()))
                .toList()
                .get(0);

        assertEquals("First comment", firstComment.getContent());
    }

    @Test
    void getCommentsByPostTest() {
        Comment comment;
        List<Comment> comments;
        CommentService commentService = new CommentService();

        String postId1 = "1000";

        comments = commentService.getCommentsByPost(postId1);
        assertEquals(0, comments.size());

        Comment firstCommentPost1 = new Comment();
        firstCommentPost1.setContent("First comment post 1");
        comment =  commentService.createComment(firstCommentPost1, postId1);

        comments = commentService.getCommentsByPost(postId1);
        assertEquals(1, comments.size());

        Comment secondCommentPost1 = new Comment();
        secondCommentPost1.setContent("Second comment post 1");
        comment =  commentService.createComment(secondCommentPost1, postId1);

        comments = commentService.getCommentsByPost(postId1);
        assertEquals(2, comments.size());

        String postId2 = "2000";

        Comment firstCommentPost2 = new Comment();
        firstCommentPost2.setContent("First comment post 2");
        comment =  commentService.createComment(firstCommentPost2, postId2);

        comments = commentService.getCommentsByPost(postId2);
        assertEquals(1, comments.size());
    }
}