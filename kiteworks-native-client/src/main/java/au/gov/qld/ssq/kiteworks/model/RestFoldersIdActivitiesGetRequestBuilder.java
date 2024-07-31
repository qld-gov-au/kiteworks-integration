package au.gov.qld.ssq.kiteworks.model;

public class RestFoldersIdActivitiesGetRequestBuilder {
    private String id;
    private String filter;
    private String type;
    private Integer startTime;
    private String with;
    private Integer offset;
    private Integer endTime;
    private Boolean returnEntity;
    private String endDate;
    private String search;
    private String orderBy;
    private String mode;
    private String startDate;
    private String transactionId;
    private String fileId;
    private Integer limit;
    private Boolean nested;
    private Integer noDayBack;

    public RestFoldersIdActivitiesGetRequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withWith(String with) {
        this.with = with;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withReturnEntity(Boolean returnEntity) {
        this.returnEntity = returnEntity;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withSearch(String search) {
        this.search = search;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withNested(Boolean nested) {
        this.nested = nested;
        return this;
    }

    public RestFoldersIdActivitiesGetRequestBuilder withNoDayBack(Integer noDayBack) {
        this.noDayBack = noDayBack;
        return this;
    }

    // Add build method to return the built object
    public RestFoldersIdActivitiesGetRequestBuilder build() {
        return this;
    }

    // Getter methods for each field (if needed)
    public String getId() {
        return id;
    }

    public String getFilter() {
        return filter;
    }

    public String getType() {
        return type;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public String getWith() {
        return with;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public Boolean getReturnEntity() {
        return returnEntity;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSearch() {
        return search;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getMode() {
        return mode;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getLimit() {
        return limit;
    }

    public Boolean getNested() {
        return nested;
    }

    public Integer getNoDayBack() {
        return noDayBack;
    }
}
