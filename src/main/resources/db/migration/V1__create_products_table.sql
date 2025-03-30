-- V1__create_products_table.sql
CREATE TABLE products (
    prd_id BIGSERIAL PRIMARY KEY,
    prd_name VARCHAR(255) NOT NULL,
    prd_description TEXT,
    prd_price NUMERIC(10, 2) NOT NULL
);

CREATE INDEX idx_products_name ON products(prd_name);

COMMENT ON TABLE products IS 'Таблица для хранения информации о товарах';
COMMENT ON COLUMN products.prd_id IS 'Уникальный идентификатор товара';
COMMENT ON COLUMN products.prd_name IS 'Наименование товара';
COMMENT ON COLUMN products.prd_description IS 'Описание товара';
COMMENT ON COLUMN products.prd_price IS 'Цена товара с точностью до 2 знаков после запятой';
