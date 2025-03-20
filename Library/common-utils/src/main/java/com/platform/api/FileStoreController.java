package com.platform.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.entity.FileStore;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.service.FileStoreService;
import com.platform.user.permissions.Permissions;
import com.platform.util.BasicUtil;
import com.platform.util.FileUtil;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("admin/file")
@ValidateUserToken
public class FileStoreController {

	@Autowired
	private FileStoreService storageService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/download/{fileId}")
	public void getFile(@PathVariable Long fileId, HttpServletResponse response) throws IOException {
		File downloadFile = null;
		try (OutputStream os = response.getOutputStream()) {
			downloadFile = storageService.getFileById(fileId);
			Assert.notNull(downloadFile, "File is not available");
			byte[] isr = Files.readAllBytes(downloadFile.toPath());
			ByteArrayOutputStream out = new ByteArrayOutputStream(isr.length);
			out.write(isr, 0, isr.length);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			// Use 'inline' for preview and 'attachement' for download in browser.
			response.addHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
			out.writeTo(os);
		} finally {
			response.flushBuffer();
			FileUtil.deleteDirectoryOrFile(downloadFile);
		}
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<FileStore> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("internalFile") boolean internalFile) throws IllegalStateException, IOException {
		GenericResponse<FileStore> response = new GenericResponse<>();
		return response
				.setStatus(Response.Status.OK).setData(storageService
						.uploadClientFileToFileStore(FileUtil.generateFileFromMutipartFile(file), internalFile))
				.build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<FileStore>> getFileDetails(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "20") int pageSize) throws IOException {
		GenericResponse<Page<FileStore>> response = new GenericResponse<>();
		String sortBy = "fileName";
		String sortDir = "ASC";
		Page<FileStore> page = storageService
				.getAllClientUploadedFilesStored(BasicUtil.getPageable(sortBy, sortDir, pageNumber, pageSize));
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/utilizedlimit", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Long> getUserUtilizedLimit() throws IOException {
		GenericResponse<Long> response = new GenericResponse<>();
		Long val = storageService.getTotalSizeUtilizedByUser();
		return response.setStatus(Response.Status.OK).setData(val==null? 0L : val).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteFile(@RequestParam("fileId") Long fileId) throws IOException {
		storageService.deleteFile(fileId);
	}

}
