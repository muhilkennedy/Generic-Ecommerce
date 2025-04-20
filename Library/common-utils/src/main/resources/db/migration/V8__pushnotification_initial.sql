/* This table is for storing firebase push notification details */
CREATE TABLE IF NOT EXISTS pushnotificationtoken (
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
    deviceinfo VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS pushnotificationtopic (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    tokenid BIGINT NOT NULL,
    topic VARCHAR(64),
    FOREIGN KEY (tokenid) REFERENCES pushnotificationtoken (rootid)
);