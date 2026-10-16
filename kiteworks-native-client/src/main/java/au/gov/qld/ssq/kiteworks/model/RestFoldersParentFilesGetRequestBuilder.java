package au.gov.qld.ssq.kiteworks.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RestFoldersParentFilesGetRequestBuilder {

    private UUID parent;

    private Boolean deleted;

    private String name;

    private String nameContains;

    private String userId;

    private List<String> userIdIn;

    private LocalDate created;

    private LocalDate createdGt;

    private LocalDate createdGte;

    private LocalDate createdLt;

    private LocalDate createdLte;

    private LocalDate modified;

    private LocalDate modifiedGt;

    private LocalDate modifiedGte;

    private LocalDate modifiedLt;

    private LocalDate modifiedLte;

    private LocalDate expire;

    private LocalDate expireGt;

    private LocalDate expireGte;

    private LocalDate expireLt;

    private LocalDate expireLte;

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

    public LocalDate getCreated() {
        return created;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public LocalDate getCreatedGt() {
        return createdGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedGt(LocalDate createdGt) {
        this.createdGt = createdGt;
        return this;
    }

    public LocalDate getCreatedGte() {
        return createdGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedGte(LocalDate createdGte) {
        this.createdGte = createdGte;
        return this;
    }

    public LocalDate getCreatedLt() {
        return createdLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedLt(LocalDate createdLt) {
        this.createdLt = createdLt;
        return this;
    }

    public LocalDate getCreatedLte() {
        return createdLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withCreatedLte(LocalDate createdLte) {
        this.createdLte = createdLte;
        return this;
    }

    public LocalDate getModified() {
        return modified;
    }

    public RestFoldersParentFilesGetRequestBuilder withModified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public LocalDate getModifiedGt() {
        return modifiedGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedGt(LocalDate modifiedGt) {
        this.modifiedGt = modifiedGt;
        return this;
    }

    public LocalDate getModifiedGte() {
        return modifiedGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedGte(LocalDate modifiedGte) {
        this.modifiedGte = modifiedGte;
        return this;
    }

    public LocalDate getModifiedLt() {
        return modifiedLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedLt(LocalDate modifiedLt) {
        this.modifiedLt = modifiedLt;
        return this;
    }

    public LocalDate getModifiedLte() {
        return modifiedLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withModifiedLte(LocalDate modifiedLte) {
        this.modifiedLte = modifiedLte;
        return this;
    }

    public LocalDate getExpire() {
        return expire;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpire(LocalDate expire) {
        this.expire = expire;
        return this;
    }

    public LocalDate getExpireGt() {
        return expireGt;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireGt(LocalDate expireGt) {
        this.expireGt = expireGt;
        return this;
    }

    public LocalDate getExpireGte() {
        return expireGte;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireGte(LocalDate expireGte) {
        this.expireGte = expireGte;
        return this;
    }

    public LocalDate getExpireLt() {
        return expireLt;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireLt(LocalDate expireLt) {
        this.expireLt = expireLt;
        return this;
    }

    public LocalDate getExpireLte() {
        return expireLte;
    }

    public RestFoldersParentFilesGetRequestBuilder withExpireLte(LocalDate expireLte) {
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
