CREATE TABLE customers (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(30) NOT NULL UNIQUE,
  address VARCHAR(30),
  phone   VARCHAR(30)
);

-- 为 name 字段添加唯一约束
ALTER TABLE customers
  ADD CONSTRAINT name_uk UNIQUE (name);