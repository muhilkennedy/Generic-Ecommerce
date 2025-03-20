package com.platform.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.entity.FileStore;
import com.platform.repository.FileStoreRepository;
import com.platform.service.BaseDaoService;

/**
 * @author Muhil
 */
@Service
public class FileStoreDaoService implements BaseDaoService {
	
	@Autowired
	private FileStoreRepository repository;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return repository.save((FileStore)obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return repository.saveAndFlush((FileStore)obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return repository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		repository.delete((FileStore)obj);
	}

	@Override
	public List<?> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long rootId) {
		repository.deleteById(rootId);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<FileStore> findAllClientFiles(long rootId, Pageable pageable) {
		return repository.findAllClientFiles(rootId, pageable);
	}
	
	public long findTotalFileSize(long userId) {
		return repository.findTotalFileSize(userId);
	}

}
