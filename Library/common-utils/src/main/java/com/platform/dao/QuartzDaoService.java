package com.platform.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.entity.QuartzJobInfo;
import com.platform.repository.QuartzJobInfoRepository;
import com.platform.service.BaseDaoService;

/**
 * @author Muhil
 */
@Service
public class QuartzDaoService implements BaseDaoService {
	
	private final String cacheName = "quartzjobinfo";

	@Autowired
	private QuartzJobInfoRepository jobRepository;

	@Override
	@CachePut(value = cacheName, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		return jobRepository.save((QuartzJobInfo) obj);
	}

	@Override
	@CachePut(value = cacheName, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return jobRepository.saveAndFlush((QuartzJobInfo) obj);
	}

	@Override
	@Cacheable(value = cacheName, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return jobRepository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = cacheName, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		jobRepository.delete((QuartzJobInfo) obj);
	}

	@Override
	public List<?> findAll() {
		return jobRepository.findAll();
	}

	@Override
	@CacheEvict(value = cacheName, key = "#rootId")
	public void deleteById(Long rootId) {
		jobRepository.deleteById(rootId);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
