package com.effynet.blogger.comments.domain.entities;

public class FakeCommentEventToBus implements CommentEventToBus {
    @Override
    public String publishCommentCreated(Comment comment) {
        return "";
    }
}
