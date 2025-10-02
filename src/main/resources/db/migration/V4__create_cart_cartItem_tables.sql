CREATE TABLE carts (
   id INT AUTO_INCREMENT PRIMARY KEY,
   user_id INT ,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT,
    product_id INT,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);