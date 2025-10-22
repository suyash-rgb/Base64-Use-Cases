package com.suyash.Base64UseCases.DTOs.usecase3;

import lombok.Data;

@Data
public class FileAck {
    private String filename;
    private String status;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FileAck() {
    }

    public FileAck(String filename, String status) {
        this.filename = filename;
        this.status = status;
    }

}
