/* Tasks */
CREATE TABLE IF NOT EXISTS task (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    title VARCHAR(64),
    description VARCHAR(512),
    tasktype VARCHAR(24),
    status VARCHAR(16),
    ownerid BIGINT,
    startdate TIMESTAMP,
    enddate TIMESTAMP,
    broadcast BOOLEAN
);

CREATE TABLE IF NOT EXISTS taskassignee (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    status VARCHAR(16),
    assigneeid BIGINT,
    taskid BIGINT,
    completedon TIMESTAMP,
    CONSTRAINT fk_taskassignee_task FOREIGN KEY (taskid) REFERENCES task (rootid)
);

CREATE TABLE IF NOT EXISTS taskdocument (
    rootid BIGSERIAL PRIMARY KEY,
    tenantid BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    timecreated BIGINT DEFAULT 0,
    timeupdated BIGINT DEFAULT 0,
    modifiedby BIGINT DEFAULT 0,
    createdby BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    filestoreid BIGINT,
    taskid BIGINT,
    CONSTRAINT fk_taskdocument_filestore FOREIGN KEY (filestoreid) REFERENCES filestore (rootid),
    CONSTRAINT fk_taskdocument_task FOREIGN KEY (taskid) REFERENCES task (rootid)
);
