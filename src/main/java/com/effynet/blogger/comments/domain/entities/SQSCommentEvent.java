package com.effynet.blogger.comments.domain.entities;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SQSCommentEvent implements CommentEvent {
    @Override
    public void publishCommentCreated(Comment comment) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        try {

            final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
            messageAttributes.put("Service", MessageAttributeValue
                    .builder()
                    .dataType("String")
                    .stringValue("effynet-blogger-comments")
                    .build());
            messageAttributes.put("Event", MessageAttributeValue
                    .builder()
                    .dataType("String")
                    .stringValue("postcreated")
                    .build());

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl("https://sqs.us-east-1.amazonaws.com/187314433716/effynet-blogger-comments-commentcreated")
                    .messageBody(gson.toJson(comment))
                    .messageAttributes(messageAttributes)
                    .delaySeconds(5)
                    .build();

            SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
            System.out.println(response.messageId());
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        } finally {
            sqsClient.close();
        }

    }
}
