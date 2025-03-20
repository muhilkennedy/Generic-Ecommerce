package com.platform.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.entity.ConfigType;
import com.platform.repository.ConfigTypeRepository;
import com.platform.service.BaseDaoService;

/**
 * @author Muhil
 */
@Service
public class ConfigTypeDaoService implements BaseDaoService {

	@Autowired
	private ConfigTypeRepository configRepository;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return configRepository.save((ConfigType) obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return configRepository.saveAndFlush((ConfigType) obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return configRepository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		configRepository.delete((ConfigType) obj);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(Long rootId) {
		configRepository.deleteById(rootId);
	}

	public List<ConfigType> findAllConfigsbyType(String type) {
		return configRepository.findConfigsByType(type);
	}

}
