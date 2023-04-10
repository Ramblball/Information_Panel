CREATE TABLE audit (
    created timestamp with time zone NOT NULL DEFAULT now(),
    updated timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE content (
    hide boolean NOT NULL DEFAULT FALSE,
    archived boolean NOT NULL DEFAULT FALSE
) INHERITS (audit);

CREATE TABLE authorities (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(15) NOT NULL,
    "view" boolean NOT NULL DEFAULT FALSE,
    "create" boolean NOT NULL DEFAULT FALSE,
    edit boolean NOT NULL DEFAULT FALSE,
    remove boolean NOT NULL DEFAULT FALSE,
    administrate boolean NOT NULL DEFAULT  FALSE
) INHERITS (audit);
INSERT INTO authorities (name, "view", "create", edit, remove, administrate)
    VALUES ('ADMIN', TRUE, TRUE, TRUE, TRUE, TRUE),
           ('USER', TRUE, TRUE, TRUE, TRUE, FALSE),
           ('VIEWER', TRUE, FALSE, FALSE, FALSE, FALSE);

CREATE TABLE "user" (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(127) UNIQUE NOT NULL,
    password varchar(127) NOT NULL,
    authorities_id integer NOT NULL REFERENCES authorities (id) ON DELETE CASCADE DEFAULT 2
) INHERITS (audit);
CREATE UNIQUE INDEX user_id_idx ON "user" USING btree(id);
INSERT INTO "user" (username, password, authorities_id)
    VALUES ('admin', '$2y$10$YC.QRMyos/.wn6.LIYE17O9fnQVJwkBkyYDY8k7v2FEO9/cYctFVm', 1);

CREATE TABLE album (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(127) NOT NULL,
    comment text NOT NULL DEFAULT '',
    end_time timestamp with time zone NOT NULL,
    creator_id bigint NOT NULL REFERENCES "user" (id) ON DELETE CASCADE
) INHERITS (content);
CREATE INDEX album_id_idx ON album USING btree(id);
CREATE VIEW archived_album
    AS SELECT id, name, comment, end_time, creator_id
    FROM album
    WHERE archived = TRUE;

CREATE TABLE file (
    file_name uuid NOT NULL PRIMARY KEY,
    original_name varchar(255) NOT NULL,
    file_format varchar(7) NOT NULL,
    file_type boolean NOT NULL,
    comment text NOT NULL DEFAULT '',
    "order" integer NOT NULL,
    removed boolean NOT NULL DEFAULT FALSE,
    album_id bigint REFERENCES album (id) ON DELETE SET NULL,
    creator_id bigint NOT NULL REFERENCES "user" (id) ON DELETE CASCADE
) INHERITS (content);
CREATE INDEX file_id_idx ON file USING btree(file_name);
CREATE VIEW archived_file
    AS SELECT file_name, original_name, file_format, file_type, comment, creator_id
    FROM file
    WHERE archived = TRUE;

CREATE TABLE notification (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "text" text NOT NULL,
    end_time timestamp with time zone NOT NULL,
    creator_id bigint NOT NULL REFERENCES "user" (id) ON DELETE CASCADE
) INHERITS (content);
CREATE INDEX notification_id_idx ON notification USING btree(id);
CREATE VIEW archived_notification
    AS SELECT id, "text", creator_id
    FROM notification
    WHERE archived = TRUE;

CREATE FUNCTION set_time_on_update()
    RETURNS TRIGGER AS $set_time_on_update$
    BEGIN
        NEW.updated = now();
        RETURN NEW;
    END;
$set_time_on_update$ LANGUAGE 'plpgsql';

CREATE FUNCTION rise_files_from_archive_on_album_rise()
    RETURNS TRIGGER AS $rise_files_from_archive_on_album_rise$
    BEGIN
        UPDATE file AS f
            SET archived = NEW.archived
            WHERE f.album_id = NEW.id AND f.archived = OLD.archived;
        RETURN NEW;
    END;
$rise_files_from_archive_on_album_rise$ LANGUAGE 'plpgsql';

CREATE TRIGGER update_audit_task_updated_on
    BEFORE UPDATE ON audit
    FOR EACH ROW
EXECUTE PROCEDURE set_time_on_update();

CREATE TRIGGER rise_files_album_updated_on
    AFTER UPDATE OF archived ON album
    FOR EACH ROW
EXECUTE PROCEDURE rise_files_from_archive_on_album_rise();
