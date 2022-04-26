-- This is the inital file and should not be edited, as it sets up migrations

CREATE TABLE IF NOT EXISTS migrations (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    batch INTEGER NOT NULL DEFAULT 1,
    migrated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);