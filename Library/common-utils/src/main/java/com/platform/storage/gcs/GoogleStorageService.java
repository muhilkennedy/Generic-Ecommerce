package com.platform.storage.gcs;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.service.StorageService;
import com.platform.util.BasicUtil;
import com.platform.util.FileUtil;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil GCS impl
 */
@Service
@Qualifier("gcs")
public class GoogleStorageService implements StorageService {

	@Autowired
	private GoogleStorageFactory factory;

	@Override
	public File readFile(Optional<?> blobId) throws IOException {
		if (blobId.isPresent()) {
			if (blobId.get() instanceof BlobId) {
				BlobId bId = (BlobId) blobId.get();
				Blob blob = factory.storage().get(bId);
				if (blob != null) {
					File file = FileUtil.crreateFileinTempDirectory(blob.getName());
					blob.downloadTo(file.toPath());
					return file;
				}
				Log.platform.warn("GCS object {} not found", bId);
			}
		}
		return null;
	}

	@Override
	public String getFileUrl(Optional<?> blobId) {
		if (blobId.isPresent()) {
			if (blobId.get() instanceof BlobId) {
				BlobId bId = (BlobId) blobId.get();
				Blob blob = factory.storage().get(bId);
				if (blob != null) {
					return blob.getMediaLink();
				}
			}
		}
		return null;
	}

	@Override
	public String saveFile(File file) throws IOException {
		BlobId blobId = BlobId.of(factory.bucket(),
				BaseSession.getTenantUniqueName() + File.separator + file.getName());
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		BlobId blobId = BlobId.of(factory.bucket(),
				BaseSession.getTenantUniqueName().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public BlobId saveFile(File file, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(factory.bucket(),
				BaseSession.getTenantUniqueName() + File.separator + file.getName());
		uploadFile(blobId, file, isInternalOnly);
		return blobId;
	}

	@Override
	public BlobId saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		if (StringUtils.isAllBlank(dir)) {
			return saveFile(file, isInternalOnly);
		}
		BlobId blobId = BlobId.of(factory.bucket(),
				BaseSession.getTenantUniqueName().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		uploadFile(blobId, file, isInternalOnly);
		return blobId;
	}

	private Blob uploadFile(BlobId blobId, File file, boolean isInternalOnly) throws IOException {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		Blob blob = this.factory.storage().create(blobInfo, FileUtils.readFileToByteArray(file),
				BlobTargetOption.predefinedAcl(isInternalOnly ? PredefinedAcl.PRIVATE : PredefinedAcl.PUBLIC_READ));
		Log.platform.info("Uploded file to GCS : " + blob.getMediaLink());
		return blob;
	}

	@Override
	public boolean deleteFile(Optional<?> blobIdOrFilePath) throws IOException {
		boolean deleted = false;
		if (blobIdOrFilePath.isPresent()) {
			if (blobIdOrFilePath.get() instanceof BlobId) {
				deleted = this.factory.storage().delete((BlobId) blobIdOrFilePath.get());
			} else {
				String url = (String) blobIdOrFilePath.get();
				Map<String, String> params = BasicUtil.parseQueryParams(url);
				BlobId blobId = BlobId.of(factory.bucket(), getBlobName(url), Long.parseLong(params.get("generation")));
				deleted = this.factory.storage().delete(blobId);
			}
			Log.platform.info("File deletion status for GCP : {} : {}", blobIdOrFilePath.get(), deleted);
		}
		return deleted;
	}

	private String getBlobName(String url) throws MalformedURLException {
		Optional<String> opt = BasicUtil.parseUrlPath(url).stream()
				.filter(path -> path.contains(BaseSession.getTenantUniqueName() + PlatformUtil.SLASH_ENCODE))
				.findFirst();
		String filename = opt.get().replace(PlatformUtil.SLASH_ENCODE, PlatformUtil.FORWARD_SLASH_OPERATOR);
		filename = filename.replace(PlatformUtil.SPACE_ENCODE, PlatformUtil.EMPTY_SPACE);
		return filename;
	}

}
