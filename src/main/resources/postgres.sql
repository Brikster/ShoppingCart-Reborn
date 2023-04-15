CREATE TABLE IF NOT EXISTS shoppingcart_product_templates (
    id BIGSERIAL
        CONSTRAINT shoppingcart_product_templates_pk PRIMARY KEY,
    template_name VARCHAR(64)
        CONSTRAINT shoppingcart_product_templates_template_name_uk UNIQUE,
    product_type VARCHAR(64) NOT NULL,
    product_name VARCHAR(128) NOT NULL,
    product_data JSONB NOT NULL,
    CONSTRAINT shoppingcart_product_templates_product_type_chk CHECK (
        product_type IN ('console_command')
    ),
    CONSTRAINT shoppingcart_product_templates_template_name_lowercase_chk CHECK (
        template_name = LOWER(template_name)
    )
);

CREATE TABLE IF NOT EXISTS shoppingcart_deliveries (
    id BIGSERIAL
        CONSTRAINT shoppingcart_deliveries_pk PRIMARY KEY,
    player_name VARCHAR(16) NOT NULL,
    product_template_id BIGINT,
    product_type VARCHAR(64),
    product_name VARCHAR(128),
    product_data JSONB,
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
           CASE
               WHEN product_template_id IS NULL
                    THEN d.product_type
                    ELSE t.product_type
           END AS product_type,
           CASE
               WHEN product_template_id IS NULL
                   THEN d.product_name
               ELSE t.product_name
               END AS product_name,
           CASE
               WHEN product_template_id IS NULL
                   THEN d.product_data
               ELSE t.product_data
            END AS product_data,
        server_name,
        delivered_at
    FROM shoppingcart_deliveries d
        LEFT JOIN shoppingcart_product_templates t
            ON d.product_template_id = t.id
);
