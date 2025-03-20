package com.user.service;

import java.util.List;
import com.platform.service.BaseService;
import com.user.entity.Employee;
import com.user.entity.Role;

/**
 * @author Muhil 
 */
public interface EmployeeRolesService extends BaseService {

    Role createNewRole (String roleName, List<Long> permisionIds);

    Employee addRoleToEmployee (Long empId, List<Long> roleIds);

    Employee removeRolesForEmployee (Long empId, List<Long> roleIds);

    List<Role> getAllAvailableRoles ();

    List<Role> getEmployeeRoles (Long empId);

}
