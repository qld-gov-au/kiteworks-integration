package au.gov.qld.ssq.kiteworks.model;

import java.util.UUID;

public class RestFoldersIdActivitiesGetRequestBuilder {
    private  UUID id;
    private  Integer noDayBack;
    private  Integer startTime;
    private  Integer endTime;
    private  String startDate;
    private  String endDate;
    private  String filter;
    private  String search;
    private  String type;
    private  String transactionId;
    private  String orderBy;
    private  Boolean nested;
    private  String fileId;
    private  Integer limit;
    private  Integer offset;
    private  String with;
    private  String mode;

    public UUID getId() {
        return id;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public Integer getNoDayBack() {
        return noDayBack;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withNoDayBack(Integer noDayBack) {
        this.noDayBack = noDayBack;
        return this;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getFilter() {
        return filter;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public String getSearch() {
        return search;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withSearch(String search) {
        this.search = search;
        return this;
    }

    public String getType() {
        return type;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Boolean getNested() {
        return nested;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withNested(Boolean nested) {
        this.nested = nested;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public String getWith() {
        return with;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withWith(String with) {
        this.with = with;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }
}
