ALTER TABLE IF EXISTS categories
DROP CONSTRAINT categories_requirements_id_fkey,
ADD CONSTRAINT categories_requirements_id_fkey
	FOREIGN KEY (requirements_id)
	REFERENCES requirements_values(id)
	ON DELETE CASCADE;


