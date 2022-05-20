CREATE OR REPLACE PROCEDURE addPage(contentId INT, html TEXT, employeeId INT)
AS $$
DECLARE
    logid pages.id%TYPE;
BEGIN
    INSERT INTO pages (html) VALUES (html) RETURNING id INTO logid;
    INSERT INTO contains (log_id, pages_id) VALUES (logid, contentId);
    INSERT INTO log (id, timestamp, edited_by) VALUES (logid, NOW(), employeeId);
END; $$
LANGUAGE plpgsql;

SELECT html FROM pages WHERE id = (
    SELECT pages_id
    FROM contains
    WHERE log_id = (
        SELECT log.id
        FROM log
        WHERE id = ?
        ORDER BY timestamp FETCH FIRST ROW ONLY
        )
);

SELECT html FROM pages WHERE id = (
    SELECT pages_id
    FROM contains
    WHERE log_id = (
        SELECT log.id
        FROM log
        WHERE id IN (?)
        ORDER BY timestamp LIMIT ?
    )
);


