package com.nexife.nns.gotalk.Chat;

public class MessageObject {

    String messageId, senderId, messageText;

    public MessageObject(String messageId, String senderId, String messageText) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.messageText = messageText;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
