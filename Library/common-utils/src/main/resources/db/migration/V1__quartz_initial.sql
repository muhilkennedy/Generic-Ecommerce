-- creating quartz tables to be initalized once
-- https://github.com/quartz-scheduler/quartz/blob/main/quartz/src/main/resources/org/quartz/impl/jdbcjobstore/tables_postgres.sql

--DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
--DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
--DROP TABLE IF EXISTS QRTZ_LOCKS;
--DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_TRIGGERS;
--DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
--DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- internal table for job tracking
CREATE TABLE IF NOT EXISTS QRTZ_JOBINFO (
    rootid BIGSERIAL PRIMARY KEY,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    tenantid BIGINT NOT NULL,
    jobname VARCHAR(64),
    jobgroup VARCHAR(64),
    isrecurring BOOLEAN,
    CONSTRAINT quartz_job_id UNIQUE (jobname, jobgroup)
);

CREATE TABLE IF NOT EXISTS QRTZ_JOBHISTORY (
    rootid BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(128) UNIQUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    tenantid BIGINT NOT NULL,
	jobinfoid BIGINT NOT NULL,
    jobstatus VARCHAR(16),
    errorinfo VARCHAR(2048),
    FOREIGN KEY (jobinfoid) REFERENCES QRTZ_JOBINFO (rootid)
);