package com.effynet.blogger.comments.domain.entities;

public interface CommentEvent {
    void publishCommentCreated(Comment comment);
}
