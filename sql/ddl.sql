/*
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS covers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS orders;
*/

CREATE TABLE IF NOT EXISTS covers(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS books(
	id BIGSERIAL PRIMARY KEY,
	isbn CHAR(17) UNIQUE NOT NULL,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100) NOT NULL,
	pages INTEGER,
	cover_id BIGINT REFERENCES covers,
	price DECIMAL(10, 2) DEFAULT 0.0 NOT NULL,
	deleted BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	lastName VARCHAR (50) NOT NULL,
	role_id BIGINT REFERENCES roles,
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(50) NOT NULL,
	birthday DATE,
	deleted BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE IF NOT EXISTS status (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS orders(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users NOT NULL,
    total_cost DECIMAL NOT NULL,
    timestamp TIMESTAMP DEFAULT current_timestamp,
    status_id BIGINT REFERENCES status
);

CREATE TABLE IF NOT EXISTS orderItem(
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    book_id BIGINT REFERENCES books NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL NOT NULL
);