/* Common Audit table */
CREATE TABLE IF NOT EXISTS auditlog (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    auditid VARCHAR(128) UNIQUE,
    message VARCHAR(2048),
    operation VARCHAR(32),
    affectedentity VARCHAR(64),
    affectedrootid BIGINT DEFAULT 0
);
