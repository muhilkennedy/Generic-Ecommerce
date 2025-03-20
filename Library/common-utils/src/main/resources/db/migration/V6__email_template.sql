CREATE TABLE IF NOT EXISTS emailtemplate (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    title VARCHAR(64) UNIQUE,
    content BYTEA
);