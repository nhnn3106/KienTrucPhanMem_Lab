CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT UNIQUE NOT NULL,
  role TEXT NOT NULL,
  status TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (name, email, role, status)
VALUES
  ('Nguyen Van A', 'nva@company.com', 'Admin', 'active'),
  ('Tran Thi B', 'ttb@company.com', 'Manager', 'active'),
  ('Le Minh C', 'lmc@company.com', 'Member', 'inactive')
ON CONFLICT (email) DO NOTHING;
