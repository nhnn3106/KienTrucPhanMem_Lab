CREATE TABLE table_user_01 (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	email VARCHAR(200) NOT NULL UNIQUE,
	created_at TIMESTAMP NOT NULL
);

CREATE TABLE table_user_02 (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	email VARCHAR(200) NOT NULL UNIQUE,
	created_at TIMESTAMP NOT NULL
);

CREATE TABLE vertical_user (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	email VARCHAR(200) NOT NULL UNIQUE,
	created_at TIMESTAMP NOT NULL
);

CREATE TABLE functional_user (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	email VARCHAR(200) NOT NULL UNIQUE,
	created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_table_user_01_email ON table_user_01(email);
CREATE INDEX idx_table_user_02_email ON table_user_02(email);
CREATE INDEX idx_vertical_user_email ON vertical_user(email);
CREATE INDEX idx_functional_user_email ON functional_user(email);
