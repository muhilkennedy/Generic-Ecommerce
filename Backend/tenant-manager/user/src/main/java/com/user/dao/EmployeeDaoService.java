package com.user.dao;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.service.BaseDaoService;
import com.platform.util.EncryptionUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.repository.EmployeeRepository;

/**
 * @author muhil
 */
@Service
public class EmployeeDaoService implements BaseDaoService {
	
	private static final String EMPLOYEE_CACHE_NAME = "employee";

	@Autowired
	private EmployeeRepository empRepository;

	@Override
	@CachePut(value = EMPLOYEE_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		return empRepository.save((Employee) obj);
	}

	@Override
	@CachePut(value = EMPLOYEE_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return empRepository.saveAndFlush((Employee) obj);
	}

	@Override
	@Cacheable(value = EMPLOYEE_CACHE_NAME, key = "#rootId", unless="#result == null")
	public BaseEntity findById(Long rootId) {
		return empRepository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = EMPLOYEE_CACHE_NAME, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		empRepository.delete((Employee) obj);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return empRepository.findAll(pageable);
	}

	@Override
	@CacheEvict(value = EMPLOYEE_CACHE_NAME, key = "#obj.rootid")
	public void deleteById(Long rootId) {
		empRepository.deleteById(rootId);
	}

	@Cacheable(value = EMPLOYEE_CACHE_NAME, key = "#uniqueName", unless="#result == null")
	public Employee findByUniqueName(String uniqueName) {
		return empRepository.findByUniqueName(uniqueName);
	}
	
	@Cacheable(value = EMPLOYEE_CACHE_NAME, key = "#emailId", unless="#result == null")
	public Employee findByEmailId(String emailId) {
		return empRepository.findByEmailId(emailId);
	}
	
	public Employee findUserForLogin(User user) throws NoSuchAlgorithmException {
		return empRepository.findEmployeeForLogin(user.getEmailid(),
				StringUtils.isAllEmpty(user.getMobile()) ? PlatformUtil.EMPTY_STRING
						: EncryptionUtil.hash_SHA256(user.getMobile()));
	}

}
