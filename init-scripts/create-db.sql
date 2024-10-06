DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_database WHERE datname = 'sbsapocdb'
    ) THEN
        EXECUTE 'CREATE DATABASE sbsapocdb';
END IF;
END $$;