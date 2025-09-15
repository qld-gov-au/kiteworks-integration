package au.gov.qld.ssq.kiteworks.model;

import java.util.List;
import java.util.UUID;

public class RestFoldersParentFilesGetRequestBuilder {

    private UUID parent;

    private Boolean deleted;

    private String name;

    private String nameContains;

    private String userId;

    private List<String> userIdIn;

    private String created;

    private String createdGt;

    private String createdGte;

    private String createdLt;

    private String createdLte;

    private String modified;

    private String modifiedGt;

    private String modifiedGte;

    private String modifiedLt;

    private String modifiedLte;

    private String expire;

    private String expireGt;

    private String expireGte;

    private String expireLt;

    private String expireLte;

    private Boolean isPushed;

    private String orderBy;

    private Integer limit;

    private Integer offset;

    private String with;

    private String mode;

    public UUID getParent() {
        return parent;
    }

    public RestFoldersParentFilesGetRequestBuilder withParent(UUID parent) {
        this.parent = parent;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public RestFoldersParentFilesGetRequestBuilder withDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestFoldersParentFilesGetRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public String getNameContains() {
        return nameContains;
    }

    public RestFoldersParentFilesGetRequestBuilder withNameContains(String nameContains) {
        this.nameContains = nameContains;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RestFoldersParentFilesGetRequestBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<String> getUserIdIn() {
        return userIdIn;
    }

    public RestFoldersParentFilesGetRequestBuilder withUserIdIn(List<String> userIdIn) {
        this.userIdIn = userIdIn;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreated(String created) {
        this.created = created;
        return this;
    }

    public String getCreatedGt() {
        return createdGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedGt(String createdGt) {
        this.createdGt = createdGt;
        return this;
    }

    public String getCreatedGte() {
        return createdGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedGte(String createdGte) {
        this.createdGte = createdGte;
        return this;
    }

    public String getCreatedLt() {
        return createdLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedLt(String createdLt) {
        this.createdLt = createdLt;
        return this;
    }

    public String getCreatedLte() {
        return createdLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedLte(String createdLte) {
        this.createdLte = createdLte;
        return this;
    }

    public String getModified() {
        return modified;
    }

    public RestFoldersParentFilesGetRequestBuilder withModified(String modified) {
        this.modified = modified;
        return this;
    }

    public String getModifiedGt() {
        return modifiedGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedGt(String modifiedGt) {
        this.modifiedGt = modifiedGt;
        return this;
    }

    public String getModifiedGte() {
        return modifiedGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedGte(String modifiedGte) {
        this.modifiedGte = modifiedGte;
        return this;
    }

    public String getModifiedLt() {
        return modifiedLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedLt(String modifiedLt) {
        this.modifiedLt = modifiedLt;
        return this;
    }

    public String getModifiedLte() {
        return modifiedLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedLte(String modifiedLte) {
        this.modifiedLte = modifiedLte;
        return this;
    }

    public String getExpire() {
        return expire;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpire(String expire) {
        this.expire = expire;
        return this;
    }

    public String getExpireGt() {
        return expireGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireGt(String expireGt) {
        this.expireGt = expireGt;
        return this;
    }

    public String getExpireGte() {
        return expireGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireGte(String expireGte) {
        this.expireGte = expireGte;
        return this;
    }

    public String getExpireLt() {
        return expireLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireLt(String expireLt) {
        this.expireLt = expireLt;
        return this;
    }

    public String getExpireLte() {
        return expireLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireLte(String expireLte) {
        this.expireLte = expireLte;
        return this;
    }

    public Boolean getPushed() {
        return isPushed;
    }

    public RestFoldersParentFilesGetRequestBuilder withPushed(Boolean pushed) {
        isPushed = pushed;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public RestFoldersParentFilesGetRequestBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public RestFoldersParentFilesGetRequestBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public RestFoldersParentFilesGetRequestBuilder withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public String getWith() {
        return with;
    }

    public RestFoldersParentFilesGetRequestBuilder withWith(String with) {
        this.with = with;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public RestFoldersParentFilesGetRequestBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }
}
