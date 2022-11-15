package com.effynet.blogger.comments.web;

import com.effynet.blogger.comments.domain.entities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

import java.util.List;

@RestController
public class CommentController {

    CommentEvent commentEvent = new SQSCommentEvent();

    EventBridgeClient eventBridgeClient = getEventBridgeClient();
    CommentEventToBus eventBridgeCommentEventToBus = new EventBridgeCommentEventToBus(eventBridgeClient);

    CommentService commentService = new CommentService(commentEvent, eventBridgeCommentEventToBus);

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

    private EventBridgeClient getEventBridgeClient() {
        EventBridgeClient eventBridgeClient = EventBridgeClient.builder()
                .region(Region.US_EAST_1)
                .build();

        return eventBridgeClient;
    }

}
