package com.mahaexam.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public  class Attachment {
    private Long id;
    private Long emailRequestId;
    private byte[] data;
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEmailRequestId() { return emailRequestId; }
    public void setEmailRequestId(Long emailRequestId) { this.emailRequestId = emailRequestId; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}