package com.platform.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.platform.server.BaseSession;
import com.platform.service.StorageService;
import com.platform.util.FileUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil file storage impl
 */
@Service
@Qualifier("nfs")
public class NFStorageService implements StorageService {

	@Value("${app.nfs.path}")
	private String nfsPath;

	@Value("${info.app.name}")
	private String appName;

	private File defaultDirectory;

	@PostConstruct
	public void init() throws IOException {
		this.defaultDirectory = Files.createDirectories(Paths.get(nfsPath + File.separator + appName)).toFile();
	}

	@Override
	public File readFile(Optional<?> filePath) throws IOException {
		if (!StringUtils.isAllBlank(filePath.get().toString())) {
			File srcFile = new File(filePath.get().toString());
			File tempFile = FileUtil.createTempFile(FileUtil.getFileName(srcFile.getName()),
					FileUtil.getFileExtension(srcFile.getName()));
			FileUtils.copyFile(srcFile, tempFile);
			return tempFile;
		}
		return null;
	}

	@Override
	public String saveFile(File file) throws IOException {
		File destFile = new File(defaultDirectory, BaseSession.getTenantUniqueName() + File.separator + file.getName());
		FileUtils.copyFile(file, destFile);
		return destFile.getPath();
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		File destFile = new File(
				defaultDirectory.getPath() + File.separator + BaseSession.getTenantUniqueName() + File.separator + dir,
				file.getName());
		FileUtils.copyFile(file, destFile);
		return destFile.getPath();
	}

	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteFile(Optional<?> filePath) throws IOException {
		if (filePath.isPresent()) {
			return FileUtil.deleteDirectoryOrFile(new File((String) filePath.get()));
		}
		return false;
	}

}