CREATE TABLE IF NOT EXISTS shoppingcart_product_templates (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(64),
    product_type VARCHAR(64) NOT NULL,
    product_name VARCHAR(128) NOT NULL,
    product_data JSON NOT NULL,
    CONSTRAINT shoppingcart_product_templates_template_name_uk UNIQUE (template_name),
    CONSTRAINT shoppingcart_product_templates_product_type_chk CHECK (
        product_type IN ('console_command')
    ),
    CONSTRAINT shoppingcart_product_templates_template_name_lowercase_chk CHECK (
        template_name = LOWER(template_name)
    )
);

CREATE TABLE IF NOT EXISTS shoppingcart_deliveries (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(16) NOT NULL,
    product_template_id BIGINT UNSIGNED,
    product_type VARCHAR(64),
    product_name VARCHAR(128),
    product_data JSON,
    server_name VARCHAR(64) DEFAULT 'global',
    delivered_at TIMESTAMP DEFAULT NULL,
    CONSTRAINT shoppingcart_deliveries_templated_or_single_chk CHECK (
            product_template_id IS NOT NULL
                 AND (product_type IS NULL
                          AND product_name IS NULL
                          AND product_data IS NULL)
            OR
            product_template_id IS NULL
                AND (product_type IS NOT NULL
                         AND product_name IS NOT NULL
                         AND product_data IS NOT NULL)
        ),
    CONSTRAINT shoppingcart_deliveries_product_type_chk CHECK (
         product_type IS NULL
         OR
         product_type IN ('console_command')
    ),
    CONSTRAINT shoppingcart_deliveries_server_name_lowercase_chk CHECK (
        server_name = LOWER(server_name)
    ),
    CONSTRAINT shoppingcart_deliveries_product_template_id_fk FOREIGN KEY
        (product_template_id) REFERENCES shoppingcart_product_templates (id)
);

CREATE OR REPLACE VIEW shoppingcart_all_deliveries AS (
     SELECT d.id, player_name,
            IF(product_template_id IS NULL, d.product_type, t.product_type) AS product_type,
            IF(product_template_id IS NULL, d.product_name, t.product_name) AS product_name,
            IF(product_template_id IS NULL, d.product_data, t.product_data) AS product_data,
            server_name,
            delivered_at
     FROM shoppingcart_deliveries d
     LEFT JOIN shoppingcart_product_templates t
               ON d.product_template_id = t.id
);