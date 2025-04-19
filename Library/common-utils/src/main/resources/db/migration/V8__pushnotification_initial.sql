/* This table is for storing firebase push notification details */
CREATE TABLE IF NOT EXISTS filestore (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    userid BIGINT NOT NULL,
    token VARCHAR(512),
    deviceinfo VARCHAR(128),
    topic VARCHAR(64)
);