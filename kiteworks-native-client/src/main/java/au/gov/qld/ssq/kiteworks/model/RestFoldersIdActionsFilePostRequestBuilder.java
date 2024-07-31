package au.gov.qld.ssq.kiteworks.model;

import java.io.File;
import java.time.LocalDate;

public class RestFoldersIdActionsFilePostRequestBuilder {
    private String id;
    private File body;
    private Boolean returnEntity;
    private String mode;
    private LocalDate clientCreated;
    private LocalDate clientModified;
    private Boolean disableAutoVersion;
    private Boolean note;

    public RestFoldersIdActionsFilePostRequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withBody(File body) {
        this.body = body;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withReturnEntity(Boolean returnEntity) {
        this.returnEntity = returnEntity;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withClientCreated(LocalDate clientCreated) {
        this.clientCreated = clientCreated;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withClientModified(LocalDate clientModified) {
        this.clientModified = clientModified;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withDisableAutoVersion(Boolean disableAutoVersion) {
        this.disableAutoVersion = disableAutoVersion;
        return this;
    }

    public RestFoldersIdActionsFilePostRequestBuilder withNote(Boolean note) {
        this.note = note;
        return this;
    }

    // Add build method to return the built object
    public RestFoldersIdActionsFilePostRequestBuilder build() {
        return this;
    }

    // Getter methods for each field (if needed)
    public String getId() {
        return id;
    }

    public File getBody() {
        return body;
    }

    public Boolean getReturnEntity() {
        return returnEntity;
    }

    public String getMode() {
        return mode;
    }

    public LocalDate getClientCreated() {
        return clientCreated;
    }

    public LocalDate getClientModified() {
        return clientModified;
    }

    public Boolean getDisableAutoVersion() {
        return disableAutoVersion;
    }

    public Boolean getNote() {
        return note;
    }
}
