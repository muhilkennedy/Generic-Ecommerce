/* Employee */
CREATE TABLE IF NOT EXISTS employee (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    locale VARCHAR(12) DEFAULT 'en',
    timezone VARCHAR(64),
    uniquename VARCHAR(50) NOT NULL,
    fname VARCHAR(50),
    lname VARCHAR(50),
    emailid VARCHAR(50),
    mobile VARCHAR(512),
    password VARCHAR(512),
    reportsto BIGINT,
    logintype VARCHAR(10),
    CONSTRAINT fk_tenant_employee FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT con_tenant_emp_email UNIQUE (tenantid, emailid),
    CONSTRAINT con_tenant_emp_mobile UNIQUE (tenantid, mobile),
    CONSTRAINT con_tenant_emp_uniquename UNIQUE (tenantid, uniquename)
);

CREATE TABLE IF NOT EXISTS employeeinfo (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    dob VARCHAR(16),
    gender VARCHAR(12),
    prooffileid BIGINT,
    profilepic VARCHAR(512),
    secondaryemail VARCHAR(50),
    designation VARCHAR(64),
    employeeid BIGINT,
    CONSTRAINT fk_prooffileid FOREIGN KEY (prooffileid) REFERENCES filestore (rootid),
    CONSTRAINT fk_tenant_employeeinfo FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT fk_employee_employeeinfo FOREIGN KEY (employeeid) REFERENCES employee (rootid)
);

/* Uncomment if employeeactivity table is needed
CREATE TABLE IF NOT EXISTS employeeactivity (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    lastactive BIGINT,
    lastlogin BIGINT,
    CONSTRAINT fk_tenant_employeeactivity FOREIGN KEY (tenantid) REFERENCES tenant (rootid)
);
*/

/* Customer */
CREATE TABLE IF NOT EXISTS customer (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    locale VARCHAR(12) DEFAULT 'en',
    timezone VARCHAR(64),
    uniquename VARCHAR(50) NOT NULL,
    fname VARCHAR(50),
    lname VARCHAR(50),
    emailid VARCHAR(50),
    mobile VARCHAR(512),
    password VARCHAR(512),
    logintype VARCHAR(15),
    CONSTRAINT fk_tenant_customer FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT con_tenant_cus_email UNIQUE (tenantid, emailid),
    CONSTRAINT con_tenant_cus_mobile UNIQUE (tenantid, mobile),
    CONSTRAINT con_tenant_cus_uniquename UNIQUE (tenantid, uniquename)
);

/*stores hashed values of encrypted fields to perform search*/
CREATE TABLE IF NOT EXISTS userhash (
	rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    uniquename VARCHAR(50) NOT NULL,
    mobile VARCHAR(300) NOT NULL,
    CONSTRAINT fk_tenant_customer FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
	CONSTRAINT con_tenant_usrhash_mobile UNIQUE (tenantid, mobile)
);

-- Store employee/user preferences as required
CREATE TABLE usersettings (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    uniquename VARCHAR(50) NOT NULL,
    preferences JSONB NOT NULL DEFAULT '{}',
    CONSTRAINT fk_tenant_usr_setting FOREIGN KEY (tenantid) REFERENCES tenant (rootid),
    CONSTRAINT con_tenant_usr_setting UNIQUE (tenantid, uniquename)
);

/* Initial Data Load */
INSERT INTO employee (rootid, tenantid, active, fname, lname, mobile, emailid, password, timecreated, timeupdated, createdby, modifiedby, uniquename, logintype)
VALUES
    (-1, -1, TRUE, 'Support', 'admin', 'nxXOGrB6XRXiXy2BuMmjqg==', 'superuser', '$2a$15$lc/un/P2saK1lpD.l2R8GOhdH0yvcrgot2gATMU.jEjwn4q7tGy9u', 0, 0, 0, 0, 'EMP-5c9c7025-08de-473d-bc8c-29f79c5541b6', 'INTERNAL');

INSERT INTO employeeinfo (rootid, tenantid, active, gender, employeeid)
VALUES
    (-1, -1, TRUE, 'MALE', -1);
    
INSERT INTO userhash (rootid, tenantid, uniquename, mobile, timeupdated, timecreated, createdby, modifiedby)
VALUES
	(-1, -1, 'EMP-5c9c7025-08de-473d-bc8c-29f79c5541b6', 'DLLMZ42eccor0clqdeXyd2ZuhG129oQTKK+dPz4i+OE=', 0,0,0,0);
