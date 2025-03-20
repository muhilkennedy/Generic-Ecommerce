package com.platform.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.platform.entity.FileStore;
import com.platform.storage.StoreType;

/**
 * @author muhil 
 */
public interface FileStoreService extends BaseService{

	File getFileById(Long id) throws IOException;

	FileStore getFileStoreById(Long id);

	Page<FileStore> getAllFilesStored(Pageable pageable);

	Page<FileStore> getAllClientUploadedFilesStored(Pageable pageable);

	FileStore uploadClientFileToFileStore(File file, boolean aclRestricted) throws IOException;

	Long getTotalSizeUtilizedByUser() throws IOException;

	FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted) throws IOException;

	FileStore uploadToFileStore(File file, boolean aclRestricted) throws IOException;

	FileStore uploadToFileStore(File file, boolean aclRestricted, String directory) throws IOException;

	FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted, String directory) throws IOException;

	FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted, String directory, boolean clientFile)
			throws IOException;

	FileStore updateFileStore(FileStore fileStore, File file) throws IOException;

	FileStore updateFileStore(FileStore fileStore, File file, String directory) throws IOException;

	void deleteFile(Long fileId) throws IOException;

    String getMediaUrl (Long id) throws FileNotFoundException;

}
