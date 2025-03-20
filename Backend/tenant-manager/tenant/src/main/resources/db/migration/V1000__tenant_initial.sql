/* BASE TRANSACTION TABLES */
CREATE TABLE IF NOT EXISTS tenant (
    rootid BIGSERIAL PRIMARY KEY, 
    active BOOLEAN DEFAULT TRUE, 
    timecreated BIGINT DEFAULT 0, 
    timeupdated BIGINT DEFAULT 0, 
    modifiedby BIGINT DEFAULT 0, 
    createdby BIGINT DEFAULT 0, 
    version BIGINT DEFAULT 0, 
    locale VARCHAR(12) DEFAULT 'en_US', 
    timezone VARCHAR(32) DEFAULT 'IST', 
    name VARCHAR(50) NOT NULL, 
    uniquename VARCHAR(50) NOT NULL UNIQUE, 
    parent BIGINT,
    logo VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS tenantdetails (
    rootid BIGSERIAL PRIMARY KEY, 
    tenantid BIGINT NOT NULL, 
    active BOOLEAN DEFAULT TRUE, 
    timecreated BIGINT DEFAULT 0, 
    timeupdated BIGINT DEFAULT 0, 
    modifiedby BIGINT DEFAULT 0, 
    createdby BIGINT DEFAULT 0, 
    version BIGINT DEFAULT 0, 
    contact VARCHAR(15), 
    emailid VARCHAR(128), 
    street VARCHAR(252), 
    city VARCHAR(124), 
    pincode VARCHAR(20),
    state VARCHAR(64),
    businessemail VARCHAR(128),
    tagline VARCHAR(1024),
    logothumbnail VARCHAR(512),
    CONSTRAINT fk_tenant FOREIGN KEY (tenantid) REFERENCES tenant(rootid), 
    CONSTRAINT con_tenant_email UNIQUE (tenantid, emailid)
);

CREATE TABLE IF NOT EXISTS tenantsubscription (
    rootid BIGSERIAL PRIMARY KEY, 
    tenantid BIGINT NOT NULL, 
    active BOOLEAN DEFAULT TRUE, 
    timecreated BIGINT DEFAULT 0, 
    timeupdated BIGINT DEFAULT 0, 
    modifiedby BIGINT DEFAULT 0, 
    createdby BIGINT DEFAULT 0, 
    version BIGINT DEFAULT 0, 
    startdate TIMESTAMP, 
    enddate TIMESTAMP, 
    CONSTRAINT fk_tenant_subscription FOREIGN KEY (tenantid) REFERENCES tenant(rootid)
);

CREATE TABLE IF NOT EXISTS googleconfig (
    rootid BIGSERIAL PRIMARY KEY, 
    tenantid BIGINT NOT NULL, 
    active BOOLEAN DEFAULT TRUE, 
    timecreated BIGINT DEFAULT 0, 
    timeupdated BIGINT DEFAULT 0, 
    modifiedby BIGINT DEFAULT 0, 
    createdby BIGINT DEFAULT 0, 
    version BIGINT DEFAULT 0, 
    oauthclientid VARCHAR(128), 
    gcsbucket VARCHAR(1024),
    gcsconfig VARCHAR(1024),
    messagingconfig VARCHAR(1024),
    CONSTRAINT fk_tenant_config FOREIGN KEY (tenantid) REFERENCES tenant(rootid)
);

/* Initial data load */
/* INSERT INTO tenant (rootid, name, uniquename) VALUES (0, 'MKEN SYSTEM', 'SYSTEM'); */
INSERT INTO tenant (rootid, name, uniquename) VALUES 
    (-1, 'Admin Tenant (dev)', 'devTenant'),
    (-2, 'Client Dev Tenant (dev)', 'clientDevTenant');

/* dev email - noreplyeventemail@gmail.com */
INSERT INTO tenantdetails (rootid, tenantid, contact, emailid, street, city, pincode, tagline, businessemail) VALUES 
    (-1, -1, '1234567890', 'noreplyeventemail@gmail.com', 'street', 'mpm', '603104', 'Vazhka oru vattam da!', ''),
    (-2, -2, '1234567890', 'noreplyeventemail@gmail.com', 'street', 'mpm', '603104', 'Vazhka oru vattam da!', '');

INSERT INTO tenantsubscription (rootid, tenantid, active, timecreated, timeupdated, startdate, enddate) VALUES 
    (-1, -1, TRUE, 1, 1, '2023-01-23', '2050-01-23');
