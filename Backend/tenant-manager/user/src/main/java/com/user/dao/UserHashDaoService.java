package com.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.service.BaseDaoService;
import com.user.entity.UserHash;
import com.user.repository.UserHashRepository;

/**
 *	@author Muhil 
 */
@Service
public class UserHashDaoService implements BaseDaoService {
	
	@Autowired
	private UserHashRepository userHashRepo;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return userHashRepo.save((UserHash)obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return userHashRepo.saveAndFlush((UserHash)obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return userHashRepo.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long rootId) {
		// TODO Auto-generated method stub
		
	}

	public BaseEntity findByUniqueName(String uniqueName) {
		return userHashRepo.findByUniqueName(uniqueName);
	}

}
