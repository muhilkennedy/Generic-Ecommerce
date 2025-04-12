/* Roles and Permissions */
CREATE TABLE IF NOT EXISTS role (
    rootid SERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    rolename VARCHAR(32) NOT NULL,
    CONSTRAINT fk_role_tenant FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT con_role UNIQUE (tenantid, rolename)
);

CREATE TABLE IF NOT EXISTS rolepermission (
    rootid SERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    roleid BIGINT,
    permissionid BIGINT,
    CONSTRAINT fk_rolepermission_tenant FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT fk_rolepermission_role FOREIGN KEY (roleid) REFERENCES role (rootid),
    CONSTRAINT fk_rolepermission_permission FOREIGN KEY (permissionid) REFERENCES permission (rootid)
);

CREATE TABLE IF NOT EXISTS employeerole (
    rootid SERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    roleid BIGINT,
    employeeid BIGINT,
    CONSTRAINT con_tenantid_emp_role UNIQUE (tenantid, employeeid, roleid),
    CONSTRAINT fk_employeerole_tenant FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT fk_employeerole_role FOREIGN KEY (roleid) REFERENCES role (rootid),
    CONSTRAINT fk_employeerole_employee FOREIGN KEY (employeeid) REFERENCES employee (rootid)
);

/* Initial Data Load */
INSERT INTO role (rootid, tenantid, rolename) 
VALUES (-1, -1, 'CustomerSupportAdmin');

INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) 
VALUES
    (-1, -1, -1, 19011996),
    (-2, -1, -1, 28072023),
    (-3, -1, -1, 22101999),
    (-4, -1, -1, 789),
    (-5, -1, -1, 5099);
    
INSERT INTO role (rootid, tenantid, rolename) 
VALUES (-2, -1, 'User Manager');

INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) 
VALUES
    (-6, -1, -2, 19011996),
    (-7, -1, -2, 28072023),
    (-8, -1, -2, 22101999);
    
INSERT INTO role (rootid, tenantid, rolename) 
VALUES (-3, -1, 'Dummy Role');

INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) 
VALUES
    (-9, -1, -3, 19011996),
    (-10, -1, -3, 28072023),
    (-11, -1, -3, 22101999);
    
INSERT INTO role (rootid, tenantid, rolename) 
VALUES (-4, -1, 'Product Manager');

INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) 
VALUES
    (-12, -1, -4, 19011996),
    (-13, -1, -4, 28072023),
    (-14, -1, -4, 22101999); 

INSERT INTO employeerole (rootid, tenantid, roleid, employeeid) 
VALUES (-1, -1, -1, -1);
