-- CREATE USER postgres WITH PASSWORD 'postgres';
CREATE DATABASE kedop_missing_person;
GRANT ALL PRIVILEGES ON DATABASE kedop_missing_person TO postgres;
\connect kedop_missing_person;
CREATE SCHEMA IF NOT EXISTS missing_person;

