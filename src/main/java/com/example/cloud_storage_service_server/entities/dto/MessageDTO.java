package com.example.cloud_storage_service_server.entities.dto;

public class MessageDTO {
    private String text;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public static MessageDTO create(String text){
        MessageDTO dto = new MessageDTO();
        dto.setText(text);
        return dto;
    }
}
