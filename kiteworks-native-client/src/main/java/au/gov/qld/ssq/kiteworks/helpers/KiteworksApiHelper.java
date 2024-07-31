package au.gov.qld.ssq.kiteworks.helpers;

import au.gov.qld.ssq.kiteworks.model.RestFilesidVersionsGetWithHttpInfoRequestBuilder;
import au.gov.qld.ssq.kiteworks.model.RestFoldersIdActionsFilePostRequestBuilder;
import au.gov.qld.ssq.kiteworks.model.RestFoldersIdActivitiesGetRequestBuilder;
import au.gov.qld.ssq.kiteworks.model.RestFoldersParentFilesGetRequestBuilder;
import com.kiteworks.client.ApiException;
import com.kiteworks.client.ApiResponse;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.model.ActivityList;
import com.kiteworks.client.model.FolderChildrenFiles;
import com.kiteworks.client.model.Version;

import java.io.File;
import java.util.List;

public class KiteworksApiHelper {

    private FoldersApi foldersApi;
    private FilesApi filesApi;

    public KiteworksApiHelper(FoldersApi foldersApi, FilesApi filesApi) {
        this.foldersApi = foldersApi;
        this.filesApi = filesApi;
    }

    // Get a list of files in a folder
    // %s/rest/folders/{parent}/files
    public ApiResponse<FolderChildrenFiles> getFolderFileNames(RestFoldersParentFilesGetRequestBuilder builder) throws ApiException {

        return foldersApi.restFoldersParentFilesGetWithHttpInfo(
            builder.getParent(),
            builder.getUserId(),
            builder.getReturnEntity(),
            builder.getModifiedColonLte(),
            builder.getModifiedColonLt(),
            builder.getDeleted(),
            builder.getCreatedColonLt(),
            builder.getOrderBy(),
            builder.getOffset(),
            builder.getLimit(),
            builder.getNameColonContains(),
            builder.getCreatedColonGte(),
            builder.getMode(),
            builder.getModifiedColonGte(),
            builder.getCreated(),
            builder.getCreatedColonLte(),
            builder.getModifiedColonGt(),
            builder.getExpireColonLte(),
            builder.getModified(),
            builder.getUserIdColonIn(),
            builder.getExpireColonLt(),
            builder.getExpireColonGte(),
            builder.getCreatedColonGt(),
            builder.getWith(),
            builder.getName(),
            builder.getIsPushed(),
            builder.getExpireColonGt(),
            builder.getExpire()
        );
    }

    // %s/rest/files/%s (method DELETE)
    public void doFileDelete(String fileId) throws ApiException {
        filesApi.restFilesIdDeleteWithHttpInfo(fileId);
    }

    // %s/folders/%%s/actions/file
    public ApiResponse<Void> doUpload(RestFoldersIdActionsFilePostRequestBuilder builder) throws ApiException {
        return filesApi.restFoldersIdActionsFilePostWithHttpInfo(
            builder.getId(),
            builder.getBody(),
            builder.getReturnEntity(),
            builder.getMode(),
            builder.getClientCreated(),
            builder.getClientModified(),
            builder.getDisableAutoVersion(),
            builder.getNote()
        );
    }

    // %s/files/%%s/content
    /**
     * List versions
     * Returns a list of versions for a given file
     **/
    public ApiResponse<File> getFilesContent(String fileId) throws ApiException {
        return filesApi.restFilesIdContentGetWithHttpInfo(fileId);
    }

    // "%s/files/%%s/versions
    public ApiResponse<List<Version>> restFilesIdVersionsGetWithHttpInfo(RestFilesidVersionsGetWithHttpInfoRequestBuilder builder) throws ApiException {
        return filesApi.restFilesIdVersionsGetWithHttpInfo(
            builder.getId(),
            builder.getCreated(),
            builder.getCreatedColonGt(),
            builder.getCreatedColonGte(),
            builder.getCreatedColonLt(),
            builder.getCreatedColonLte(),
            builder.getOrderBy(),
            builder.getOffset(),
            builder.getLimit(),
            builder.getLocateId(),
            builder.getWith(),
            builder.getMode()
        );
    }

    // %s/files/%%s/versions/%%s/content
    public ApiResponse<Void> restFilesIdVersionsVersionIdContentGetWithHttpInfo(String id, String versionId, String range) throws ApiException {
        return filesApi.restFilesIdVersionsVersionIdContentGetWithHttpInfo(id, versionId, range);
    }

    // %s/folders/%%s/activities?limit=%s
    public ApiResponse<ActivityList> restFoldersIdActivitiesGetWithHttpInfo(RestFoldersIdActivitiesGetRequestBuilder builder) throws ApiException {
        return foldersApi.restFoldersIdActivitiesGetWithHttpInfo(
            builder.getId(),
            builder.getFilter(),
            builder.getType(),
            builder.getStartTime(),
            builder.getWith(),
            builder.getOffset(),
            builder.getEndTime(),
            builder.getReturnEntity(),
            builder.getEndDate(),
            builder.getSearch(),
            builder.getOrderBy(),
            builder.getMode(),
            builder.getStartDate(),
            builder.getTransactionId(),
            builder.getFileId(),
            builder.getLimit(),
            builder.getNested(),
            builder.getNoDayBack()
        );
    }

}
