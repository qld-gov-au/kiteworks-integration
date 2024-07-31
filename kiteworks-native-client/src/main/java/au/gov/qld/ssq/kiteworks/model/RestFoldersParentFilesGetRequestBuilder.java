package au.gov.qld.ssq.kiteworks.model;

import java.util.List;

public class RestFoldersParentFilesGetRequestBuilder {
    private String parent;
    private String userId;
    private Boolean returnEntity;
    private String modifiedColonLte;
    private String modifiedColonLt;
    private Boolean deleted;
    private String createdColonLt;
    private String orderBy;
    private Integer offset;
    private Integer limit;
    private String nameColonContains;
    private String createdColonGte;
    private String mode;
    private String modifiedColonGte;
    private String created;
    private String createdColonLte;
    private String modifiedColonGt;
    private String expireColonLte;
    private String modified;
    private List<String> userIdColonIn;
    private String expireColonLt;
    private String expireColonGte;
    private String createdColonGt;
    private String with;
    private String name;
    private Boolean isPushed;
    private String expireColonGt;
    private String expire;

    public RestFoldersParentFilesGetRequestBuilder withParent(String parent) {
        this.parent = parent;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withReturnEntity(Boolean returnEntity) {
        this.returnEntity = returnEntity;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedColonLte(String modifiedColonLte) {
        this.modifiedColonLte = modifiedColonLte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedColonLt(String modifiedColonLt) {
        this.modifiedColonLt = modifiedColonLt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedColonLt(String createdColonLt) {
        this.createdColonLt = createdColonLt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withNameColonContains(String nameColonContains) {
        this.nameColonContains = nameColonContains;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedColonGte(String createdColonGte) {
        this.createdColonGte = createdColonGte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedColonGte(String modifiedColonGte) {
        this.modifiedColonGte = modifiedColonGte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreated(String created) {
        this.created = created;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedColonLte(String createdColonLte) {
        this.createdColonLte = createdColonLte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedColonGt(String modifiedColonGt) {
        this.modifiedColonGt = modifiedColonGt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireColonLte(String expireColonLte) {
        this.expireColonLte = expireColonLte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withModified(String modified) {
        this.modified = modified;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withUserIdColonIn(List<String> userIdColonIn) {
        this.userIdColonIn = userIdColonIn;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireColonLt(String expireColonLt) {
        this.expireColonLt = expireColonLt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireColonGte(String expireColonGte) {
        this.expireColonGte = expireColonGte;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedColonGt(String createdColonGt) {
        this.createdColonGt = createdColonGt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withWith(String with) {
        this.with = with;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withIsPushed(Boolean isPushed) {
        this.isPushed = isPushed;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireColonGt(String expireColonGt) {
        this.expireColonGt = expireColonGt;
        return this;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpire(String expire) {
        this.expire = expire;
        return this;
    }

    // Add build method to return the built object
    public RestFoldersParentFilesGetRequestBuilder build() {
        return this;
    }

    // Getter methods for each field (if needed)
    public String getParent() {
        return parent;
    }

    public String getUserId() {
        return userId;
    }

    public Boolean getReturnEntity() {
        return returnEntity;
    }

    public String getModifiedColonLte() {
        return modifiedColonLte;
    }

    public String getModifiedColonLt() {
        return modifiedColonLt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public String getCreatedColonLt() {
        return createdColonLt;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getNameColonContains() {
        return nameColonContains;
    }

    public String getCreatedColonGte() {
        return createdColonGte;
    }

    public String getMode() {
        return mode;
    }

    public String getModifiedColonGte() {
        return modifiedColonGte;
    }

    public String getCreated() {
        return created;
    }

    public String getCreatedColonLte() {
        return createdColonLte;
    }

    public String getModifiedColonGt() {
        return modifiedColonGt;
    }

    public String getExpireColonLte() {
        return expireColonLte;
    }

    public String getModified() {
        return modified;
    }

    public List<String> getUserIdColonIn() {
        return userIdColonIn;
    }

    public String getExpireColonLt() {
        return expireColonLt;
    }

    public String getExpireColonGte() {
        return expireColonGte;
    }

    public String getCreatedColonGt() {
        return createdColonGt;
    }

    public String getWith() {
        return with;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsPushed() {
        return isPushed;
    }

    public String getExpireColonGt() {
        return expireColonGt;
    }

    public String getExpire() {
        return expire;
    }
}
