/* Common Audit table */
CREATE TABLE IF NOT EXISTS audit (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    auditid VARCHAR(64) UNIQUE,
    message VARCHAR(2048),
    operation VARCHAR(32)
);
