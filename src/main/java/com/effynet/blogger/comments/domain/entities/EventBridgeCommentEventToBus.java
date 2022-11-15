package com.effynet.blogger.comments.domain.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

import java.util.Optional;

public class EventBridgeCommentEventToBus implements CommentEventToBus {

    private EventBridgeClient eventBridgeClient;

    public EventBridgeCommentEventToBus(EventBridgeClient eventBridgeClient) {
        this.eventBridgeClient = eventBridgeClient;
    }
    @Override
    public String publishCommentCreated(Comment comment){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {

            PutEventsRequestEntry putEventsRequestEntry = PutEventsRequestEntry.builder()
                    .eventBusName("effynet-blogger-eventbus")
                    .source("effynet.blogger.comments")
                    .detailType("Comment Created")
                    .detail(gson.toJson(comment))
                    .build();

            PutEventsRequest putEventsRequest = PutEventsRequest.builder()
                    .entries(putEventsRequestEntry)
                    .build();

            PutEventsResponse putEventsResponse = this.eventBridgeClient.putEvents(putEventsRequest);

            PutEventsResultEntry putEventsResultEntry = putEventsResponse.entries().stream().findFirst().get();

            if (putEventsResultEntry.eventId() == null) {
                throw EventBridgeException.builder()
                        .message("Error sent event to EventBridge: " + putEventsResultEntry.errorCode() + " " + putEventsResultEntry.errorMessage())
                        .build();
            }

            return putEventsResultEntry.eventId();
        } catch (EventBridgeException e) {
            throw e;
        }
    }

    private EventBridgeClient getEventBridgeClient() {
        EventBridgeClient client = EventBridgeClient.builder()
                .region(Region.US_EAST_1)
                .build();

        return client;
    }
}
