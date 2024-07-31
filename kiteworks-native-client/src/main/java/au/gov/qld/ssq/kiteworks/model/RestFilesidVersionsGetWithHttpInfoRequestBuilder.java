package au.gov.qld.ssq.kiteworks.model;

import java.util.List;

public class RestFilesidVersionsGetWithHttpInfoRequestBuilder {
    private String id;
    private Integer created;
    private Integer createdColonGt;
    private Integer createdColonGte;
    private Integer createdColonLt;
    private Integer createdColonLte;
    private List<String> orderBy;
    private Integer offset;
    private Integer limit;
    private Integer locateId;
    private String with;
    private String mode;


    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withCreated(Integer created) {
        this.created = created;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withCreatedColonGt(Integer createdColonGt) {
        this.createdColonGt = createdColonGt;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withCreatedColonGte(Integer createdColonGte) {
        this.createdColonGte = createdColonGte;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withCreatedColonLt(Integer createdColonLt) {
        this.createdColonLt = createdColonLt;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withCreatedColonLte(Integer createdColonLte) {
        this.createdColonLte = createdColonLte;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withLocateId(Integer locateId) {
        this.locateId = locateId;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withWith(String with) {
        this.with = with;
        return this;
    }

    public RestFilesidVersionsGetWithHttpInfoRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }

    // Add build method to return the built object
    public RestFilesidVersionsGetWithHttpInfoRequestBuilder build() {
        return this;
    }

    // Getter methods for each field (if needed)

    public String getId() {
        return id;
    }

    public Integer getCreated() {
        return created;
    }

    public Integer getCreatedColonGt() {
        return createdColonGt;
    }

    public Integer getCreatedColonGte() {
        return createdColonGte;
    }

    public Integer getCreatedColonLt() {
        return createdColonLt;
    }

    public Integer getCreatedColonLte() {
        return createdColonLte;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getLocateId() {
        return locateId;
    }

    public String getWith() {
        return with;
    }

    public String getMode() {
        return mode;
    }
}

