-- V1__create_products_table.sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL
);

CREATE INDEX idx_products_name ON products(name);

COMMENT ON TABLE products IS 'Таблица для хранения информации о товарах';
COMMENT ON COLUMN products.id IS 'Уникальный идентификатор товара';
COMMENT ON COLUMN products.name IS 'Наименование товара';
COMMENT ON COLUMN products.description IS 'Описание товара';
COMMENT ON COLUMN products.price IS 'Цена товара с точностью до 2 знаков после запятой';
