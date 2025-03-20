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

INSERT INTO employeerole (rootid, tenantid, roleid, employeeid) 
VALUES (-1, -1, -1, -1);
