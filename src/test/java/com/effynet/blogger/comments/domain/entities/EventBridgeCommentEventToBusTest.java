package com.effynet.blogger.comments.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

import static org.junit.jupiter.api.Assertions.*;

class EventBridgeCommentEventToBusTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void publishCommentCreated() {
        EventBridgeClient eventBridgeClient = getEventBridgeClient();
        CommentEventToBus eventBridgeCommentEventToBus = new EventBridgeCommentEventToBus(eventBridgeClient);

        String commentId = java.util.UUID.randomUUID().toString();
        String postId = java.util.UUID.randomUUID().toString();

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setPostId(postId);
        comment.setContent(commentId + " " + postId);

        String eventId = eventBridgeCommentEventToBus.publishCommentCreated(comment);

        assertNotEquals("", eventId);
    }

    private EventBridgeClient getEventBridgeClient() {
        EventBridgeClient eventBridgeClient = EventBridgeClient.builder()
                .region(Region.US_EAST_1)
                .build();

        return eventBridgeClient;
    }
}