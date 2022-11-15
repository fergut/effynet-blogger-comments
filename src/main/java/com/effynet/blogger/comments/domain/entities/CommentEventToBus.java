package com.effynet.blogger.comments.domain.entities;

public interface CommentEventToBus {
    String publishCommentCreated(Comment comment);
}
