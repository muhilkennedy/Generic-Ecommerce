package com.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.platform.entity.BaseEntity;

/**
 * @author Muhil
 *
 */
public interface BaseService {

	public BaseEntity findById(Long rootId);
	
	Page<?> findAll(Pageable pageable);
	
	default List<?> findAll(){
		throw new UnsupportedOperationException();
	}
	
	default BaseEntity findByUniqueName(String uniqueName){
		return null;
	};
	
}
