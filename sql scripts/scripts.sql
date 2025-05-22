CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role VARCHAR(45) NOT NULL
);

CREATE TABLE status (
                        id SERIAL PRIMARY KEY,
                        status VARCHAR(45) NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(45) NOT NULL UNIQUE,
                       email VARCHAR(45) NOT NULL,
                       password VARCHAR(45),
                       name VARCHAR(45),
                       last_name VARCHAR(45),
                       phone VARCHAR(45),
                       role_id INT NOT NULL,
                       status_id INT NOT NULL,
                       FOREIGN KEY (role_id) REFERENCES roles (id),
                       FOREIGN KEY (status_id) REFERENCES status (id)
);

CREATE INDEX role_id_idx ON users (role_id);
CREATE INDEX status_id_idx ON users (status_id);

CREATE TABLE order_status (
                              id SERIAL PRIMARY KEY,
                              status VARCHAR(45)
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        "order" VARCHAR(45) NOT NULL UNIQUE,
                        date DATE NOT NULL,
                        order_status_id INT NOT NULL,
                        summary_price DECIMAL NOT NULL,
                        user_id INT,
                        FOREIGN KEY (order_status_id) REFERENCES order_status (id),
                        FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX order_status_id_idx ON orders (order_status_id);
CREATE INDEX orders_user_id_idx ON orders (user_id);

CREATE TABLE product_manufacture (
                                     id SERIAL PRIMARY KEY,
                                     manufacture VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE product_types (
                               id SERIAL PRIMARY KEY,
                               type VARCHAR(50) NOT NULL UNIQUE,
                               discount INT DEFAULT 0
);

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          vendor VARCHAR(35) NOT NULL UNIQUE,
                          name VARCHAR(50),
                          manufacture_id INT NOT NULL,
                          type_id INT NOT NULL,
                          description TEXT NOT NULL,
                          image BYTEA,
                          price DECIMAL,
                          number_in_stock INT,
                          FOREIGN KEY (manufacture_id) REFERENCES product_manufacture (id),
                          FOREIGN KEY (type_id) REFERENCES product_types (id)
);

CREATE INDEX manufacture_id_idx ON products (manufacture_id);
CREATE INDEX type_id_idx ON products (type_id);

CREATE TABLE purchases (
                           id SERIAL PRIMARY KEY,
                           order_id INT NOT NULL,
                           product_id INT NOT NULL,
                           FOREIGN KEY (order_id) REFERENCES orders (id),
                           FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE INDEX purchases_order_id_idx ON purchases (order_id);
CREATE INDEX purchases_product_id_idx ON purchases (product_id);
