package com.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.platform.entity.BaseEntity;

/**
 * @author Muhil
 * Has to be implemented by all DAO.
 *
 */
public interface BaseDaoService {
	
	public BaseEntity save(BaseEntity obj);

	public BaseEntity saveAndFlush(BaseEntity obj);

	public BaseEntity findById(Long rootId);

	public void delete(BaseEntity obj);

	default public List<?> findAll(){
		throw new UnsupportedOperationException();
	}

	public Page<?> findAll(Pageable pageable);

	void deleteById(Long rootId);

}
