package com.suyash.Base64UseCases.models.usecase5;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("assests")
public class StoredAssets {

    @Id
    private String id;

    private String filename;

    private String mimeType;

    private String base64Data;

    private Instant createdAt;

    public StoredAssets() {
    }

    public StoredAssets(String id, String filename, String mimeType, String base64Data, Instant createdAt) {
        this.id = id;
        this.filename = filename;
        this.mimeType = mimeType;
        this.base64Data = base64Data;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
