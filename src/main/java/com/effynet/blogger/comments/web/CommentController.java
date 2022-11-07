package com.effynet.blogger.comments.web;

import com.effynet.blogger.comments.domain.entities.Comment;
import com.effynet.blogger.comments.domain.entities.CommentEvent;
import com.effynet.blogger.comments.domain.entities.CommentService;
import com.effynet.blogger.comments.domain.entities.SQSCommentEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    CommentEvent commentEvent = new SQSCommentEvent();

    CommentService commentService = new CommentService(commentEvent);

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/posts/{id}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable String id, @RequestBody Comment request) {
        Comment comment = commentService.createComment(request, id);
        return ResponseEntity.created(null).body(comment);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path= "/posts/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable String id) {
        List<Comment> commentsByPost = commentService.getCommentsByPost(id);
        return ResponseEntity.ok(commentsByPost);
    }

}
