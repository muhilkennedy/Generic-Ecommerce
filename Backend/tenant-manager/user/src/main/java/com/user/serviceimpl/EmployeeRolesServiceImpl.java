package com.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.platform.entity.BaseEntity;
import com.platform.entity.Permission;
import com.platform.repository.PermissionRepository;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;
import com.user.entity.Role;
import com.user.entity.RolePermission;
import com.user.repository.EmployeeRoleRepository;
import com.user.repository.RolePermissionRepository;
import com.user.repository.RolesRepository;
import com.user.service.EmployeeRolesService;
import com.user.service.EmployeeService;

/**
 * @author muhil
 */
@Service
public class EmployeeRolesServiceImpl implements EmployeeRolesService {

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Autowired
	private EmployeeRoleRepository employeeRoleRepository;
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public BaseEntity findById(Long rootId) {
		return rolesRepository.findById(rootId).get();
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return rolesRepository.findAll(pageable);
	}

	@Override
	public List<EmployeeRole> findAll() {
		return employeeRoleRepository.findAll();
	}

	public List<Permission> findAllPermissions() {
		return permissionRepository.findAll();
	}

	@Override
    public Role createNewRole (String roleName, List<Long> permisionIds)
    {
        Role role = new Role();
        role.setRolename(roleName);
        rolesRepository.save(role);
        List<RolePermission> rp = permisionIds.stream().map(
            id -> rolePermissionRepository.save(
                new RolePermission(id, role.getRootid()))).toList();
        role.setPermissions(rp);
        return role;
    }

	@Override
    public Employee addRoleToEmployee (Long empId, List<Long> roleIds)
    {
        Employee employee = (Employee)employeeService.findById(empId);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<EmployeeRole> er = roleIds.stream().map(
            id -> employeeRoleRepository.save(
                new EmployeeRole(employee.getRootid(), id))).toList();
        return employeeService.updateEmployeeRoles(employee, er);
    }
    
	@Override
    public Employee removeRolesForEmployee (Long empId, List<Long> roleIds)
    {
        Employee employee = (Employee)employeeService.findById(empId);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<EmployeeRole> erList = employee.getEmployeeRoles();
        erList.stream().filter(er -> roleIds.contains(er.getRoleid())).forEach(
            er -> employeeRoleRepository.delete(er));
        // set remaining active roles bac to employee object for cache save to
        // take effect
        employee.setEmployeeRoles(
            erList.stream().filter(er -> !roleIds.contains(er.getRoleid())).toList());
        return employeeService.updateEmployeeRoles(employee, erList);
    }
	
    @Override
    public List<Role> getAllAvailableRoles ()
    {
        return rolesRepository.findAll();
    }

    @Override
    public List<Role> getEmployeeRoles (Long empId)
    {
        Employee employee = (Employee)employeeService.findById(empId);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return employee.getEmployeeRoles().stream().map(er -> er.getRole()).toList();
    }

}
