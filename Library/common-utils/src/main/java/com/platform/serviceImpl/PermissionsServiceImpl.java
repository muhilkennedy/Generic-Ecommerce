package com.platform.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.PermissionsDaoService;
import com.platform.entity.Permission;
import com.platform.service.PermissionsService;

/**
 * @author muhil
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {

	@Autowired
	private PermissionsDaoService daoService;

	@Override
	public Permission findById(Long rootId) {
		return daoService.findById(rootId).get();
	}

	@Override
	public List<Permission> getAllPermissions() {
		return daoService.findAll();
	}

}
