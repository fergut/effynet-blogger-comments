package com.effynet.blogger.comments.domain.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class CommentService {

    private CommentEvent commentEvent;
    private CommentEventToBus commentEventToBus;

    public CommentService(CommentEvent commentEvent, CommentEventToBus commentEventToBus){
        this.commentEvent = commentEvent;
        this.commentEventToBus = commentEventToBus;
    }

    List<Comment> comments = new ArrayList<>();

    public Comment createComment(Comment newComment, String postId) {
        String newId = java.util.UUID.randomUUID().toString();
        newComment.setId(newId);
        newComment.setPostId(postId);

        comments.add(newComment);

        System.out.println("Published to SQS");
        this.commentEvent.publishCommentCreated(newComment);

        System.out.println("Published to EventBridge");
        this.commentEventToBus.publishCommentCreated(newComment);

        System.out.println(newComment);

        return newComment;
    }

    public List<Comment> getCommentsByPost(String postId) {
        List<Comment> commentsByPost = comments.stream().filter(c -> c.getPostId().equals(postId)).toList();
        return commentsByPost;
    }

}
