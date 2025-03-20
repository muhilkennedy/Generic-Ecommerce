CREATE TABLE IF NOT EXISTS storetype (
    rootid BIGSERIAL PRIMARY KEY,
    stype VARCHAR(8)
);

/* This table is for storing file location only */
CREATE TABLE IF NOT EXISTS filestore (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    blobinfo BYTEA,
    mediaurl VARCHAR(1024),
    storeid INT NOT NULL,
    extension VARCHAR(64),
    aclrestricted BOOLEAN DEFAULT FALSE,
    filesize BIGINT,
    clientfile BOOLEAN DEFAULT false,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (storeid) REFERENCES storetype (rootid)
);

-- can be used to any customer store configurations
CREATE TABLE IF NOT EXISTS configstore (
    rootid BIGSERIAL PRIMARY KEY, 
    tenantid BIGINT NOT NULL, 
    active BOOLEAN DEFAULT TRUE, 
    timecreated BIGINT DEFAULT 0, 
    timeupdated BIGINT DEFAULT 0, 
    modifiedby BIGINT DEFAULT 0, 
    createdby BIGINT DEFAULT 0, 
    version BIGINT DEFAULT 0, 
    name VARCHAR(128), 
    value VARCHAR(1024), 
    type SMALLINT
);

/*default file stores*/
INSERT INTO storetype (rootid, stype) VALUES (1, 'GCS');
INSERT INTO storetype (rootid, stype) VALUES (2, 'NFS');
INSERT INTO storetype (rootid, stype) VALUES (3, 'AWS');
INSERT INTO storetype (rootid, stype) VALUES (4, 'AZURE');
INSERT INTO storetype (rootid, stype) VALUES (5, 'INTERNAL');
