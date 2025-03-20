package com.platform.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.BlobId;
import com.platform.dao.FileStoreDaoService;
import com.platform.entity.BaseEntity;
import com.platform.entity.FileStore;
import com.platform.server.BaseSession;
import com.platform.service.FileStoreService;
import com.platform.service.StorageService;
import com.platform.storage.StoreType;
import com.platform.util.FileUtil;

/**
 * @author muhil
 */
@Service
public class FileStoreServiceImpl implements FileStoreService
{

    private static String CLIENT_FILESTORE_PATH = "/FILE_EXPLORER";

    @Autowired
    private FileStoreDaoService daoService;

    @Autowired
    @Qualifier("gcs")
    private StorageService gcsService;

    @Autowired
    @Qualifier("nfs")
    private StorageService nfsService;

    @Override
    public BaseEntity findById (Long rootId)
    {
        return daoService.findById(rootId);
    }

    @Override
    public File getFileById (Long id) throws IOException
    {
        FileStore fs = (FileStore)findById(id);
        if (fs != null) {
            if (fs.getStoretype().equals(StoreType.NFS.name())) {
                return nfsService.readFile(Optional.of(fs.getMediaurl()));
            }
            else if (fs.getStoretype().equals(StoreType.GCS.name())) {
                return gcsService.readFile(Optional.of(fs.getBlobInfo()));
            }
        }
        throw new FileNotFoundException();
    }

    @Override
    public String getMediaUrl (Long id) throws FileNotFoundException
    {
        FileStore fs = (FileStore)findById(id);
        if (fs != null) {
            return fs.getMediaurl();
        }
        throw new FileNotFoundException();
    }

    @Override
    public FileStore getFileStoreById (Long id)
    {
        return (FileStore)daoService.findById(id);
    }

    public List<FileStore> getAllFilesStored ()
    {
        return (List<FileStore>)daoService.findAll();
    }

    @Override
    public Page<FileStore> getAllFilesStored (Pageable pageable)
    {
        return (Page<FileStore>)daoService.findAll(pageable);
    }

    @Override
    public Page<FileStore> getAllClientUploadedFilesStored (Pageable pageable)
    {
        return daoService.findAllClientFiles(BaseSession.getUser().getRootid(), pageable);
    }

    @Override
    public FileStore uploadClientFileToFileStore (File file, boolean aclRestricted)
        throws IOException
    {
        // type can be toggled later in case of multiple impls.
        return uploadToFileStore(
            StoreType.GCS,
            file,
            aclRestricted,
            CLIENT_FILESTORE_PATH,
            true);
    }

    @Override
    public Long getTotalSizeUtilizedByUser () throws IOException
    {
        return daoService.findTotalFileSize(BaseSession.getUser().getRootid());
    }

    @Override
    public FileStore uploadToFileStore (StoreType type, File file, boolean aclRestricted)
        throws IOException
    {
        return uploadToFileStore(type, file, aclRestricted, null, false);
    }

    @Override
    public FileStore uploadToFileStore (File file, boolean aclRestricted)
        throws IOException
    {
        return uploadToFileStore(StoreType.GCS, file, aclRestricted, null, false);
    }

    @Override
    public FileStore uploadToFileStore (File file,
                                        boolean aclRestricted,
                                        String directory)
        throws IOException
    {
        return uploadToFileStore(StoreType.GCS, file, aclRestricted, directory, false);
    }

    @Override
    public FileStore uploadToFileStore (StoreType type,
                                        File file,
                                        boolean aclRestricted,
                                        String directory)
        throws IOException
    {
        return uploadToFileStore(type, file, aclRestricted, directory, false);
    }

    @Override
    public FileStore uploadToFileStore (StoreType type,
                                        File file,
                                        boolean aclRestricted,
                                        String directory,
                                        boolean clientFile)
        throws IOException
    {
        FileStore fs = new FileStore();
        switch (type) {
        case GCS:
            BlobId blobId = (BlobId)gcsService.saveFile(file, directory, aclRestricted);
            fs.setBlobInfo(blobId);
            fs.setMediaurl(gcsService.getFileUrl(Optional.of(blobId)));
            break;
        case NFS:
            fs.setMediaurl(nfsService.saveFile(file, directory));
            break;
        default:
            throw new UnsupportedOperationException();
        }
        fs.setAcl(aclRestricted);
        fs.setStoretype(type.name());
        fs.setFileName(file.getName());
        fs.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
        fs.setClientfile(clientFile);
        fs.setSize(file.length());
        fs.setStoreId(type.getValue());
        return (FileStore)daoService.save(fs);
    }

    @Override
    public FileStore updateFileStore (FileStore fileStore, File file) throws IOException
    {
        return updateFileStore(fileStore, file, null);
    }

    @Override
    public FileStore updateFileStore (FileStore fileStore, File file, String directory)
        throws IOException
    {
        Optional<StoreType> type = StoreType.findType(fileStore.getStoretype());
        switch (type.get()) {
        case GCS:
            BlobId blobId = (BlobId)gcsService.saveFile(
                file,
                directory,
                fileStore.isAcl());
            fileStore.setBlobInfo(blobId);
            break;
        case NFS:
            String path = nfsService.saveFile(file, directory);
            fileStore.setMediaurl(path);
            break;
        default:
            throw new UnsupportedOperationException();
        }
        fileStore.setFileName(file.getName());
        fileStore.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
        fileStore.setSize(file.length());
        return (FileStore)daoService.save(fileStore);
    }

    @Override
    public void deleteFile (Long fileId) throws IOException
    {
        FileStore fs = getFileStoreById(fileId);
        if (fs != null) {
            Optional<StoreType> type = StoreType.findType(fs.getStoretype());
            boolean status = false;
            switch (type.get()) {
            case GCS:
                status = gcsService.deleteFile(Optional.of(fs.getBlobInfo()));
                break;
            case NFS:
                status = nfsService.deleteFile(Optional.of(fs.getBlobInfo()));
                break;
            default:
                throw new UnsupportedOperationException();
            }
            if (status)
                daoService.delete(fs);
        }
    }

    @Override
    public Page<?> findAll (Pageable pageable)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
