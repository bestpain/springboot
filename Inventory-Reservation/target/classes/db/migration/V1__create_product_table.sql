CREATE TABLE product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid BINARY(16) NOT NULL,
    name VARCHAR(255) NOT NULL,
    available_quantity INT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_product_uuid (uuid)
) AUTO_INCREMENT = 10000;