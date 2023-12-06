CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    currency_id INT,
    last_update TIMESTAMP NOT NULL,
    type VARCHAR(150) NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(id)
);



INSERT INTO account (name, balance, currency_id, last_update, type) VALUES
   ('Bryan current account', 200000.00, 8, CURRENT_TIMESTAMP, 'Bank'),
   ('Elise savings account', 170000.00, 9, CURRENT_TIMESTAMP, 'Cash');
