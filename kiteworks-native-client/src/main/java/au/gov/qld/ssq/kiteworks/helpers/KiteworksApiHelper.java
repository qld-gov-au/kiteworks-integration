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
import com.kiteworks.client.model.RestDliFilesIdVersionsGet200Response;
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
            builder.getParent(), builder.getName(), builder.getNameColonContains(),
            builder.getReturnEntity(), builder.getExpireColonGt(), builder.getCreatedColonLte(),
            builder.getExpire(), builder.getModifiedColonLt(), builder.getExpireColonLt(), builder.getExpireColonLte(),
            builder.getCreated(), builder.getExpireColonGte(), builder.getIsPushed(), builder.getOffset(),
            builder.getMode(), builder.getUserIdColonIn(), builder.getCreatedColonGt(), builder.getModifiedColonGt(),
            builder.getCreatedColonGte(), builder.getDeleted(), builder.getUserId(), builder.getModifiedColonLte(),
            builder.getLimit(), builder.getModifiedColonGte(), builder.getOrderBy(), builder.getModified(), builder.getWith(),
            builder.getCreatedColonLt()
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
    public ApiResponse<RestDliFilesIdVersionsGet200Response> restFilesIdVersionsGetWithHttpInfo(RestFilesidVersionsGetWithHttpInfoRequestBuilder builder) throws ApiException {
        return filesApi.restFilesIdVersionsGetWithHttpInfo(
            builder.getId(), builder.getCreated(), builder.getCreatedColonGt(),
            builder.getCreatedColonGte(), builder.getCreatedColonLt(), builder.getCreatedColonLte(),
            builder.getOrderBy(), builder.getOffset(), builder.getLimit(), builder.getLocateId(),
            builder.getWith(), builder.getMode()
        );
    }

    // %s/files/%%s/versions/%%s/content
    public ApiResponse<File> restFilesIdVersionsVersionIdContentGetWithHttpInfo(String id, String versionId, String range) throws ApiException {
        return filesApi.restFilesIdVersionsVersionIdContentGetWithHttpInfo(id, versionId, range);
    }

    // %s/folders/%%s/activities?limit=%s
    public ApiResponse<ActivityList> restFoldersIdActivitiesGetWithHttpInfo(RestFoldersIdActivitiesGetRequestBuilder builder) throws ApiException {
        return foldersApi.restFoldersIdActivitiesGetWithHttpInfo(
            builder.getId(),builder.getTransactionId(), builder.getOrderBy(), builder.getMode(),
            builder.getFileId(), builder.getLimit(), builder.getWith(), builder.getOffset(),
            builder.getFilter(), builder.getEndDate(), builder.getType(), builder.getNested(),
            builder.getNoDayBack(),builder.getReturnEntity(), builder.getStartTime(),
            builder.getEndTime(), builder.getStartDate(), builder.getSearch()
        );
    }

}
