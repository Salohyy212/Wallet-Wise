CREATE TABLE IF NOT EXISTS transaction (
    id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    date Date,
    account_id int,
    FOREIGN KEY (account_id) REFERENCES account(id)
    );


INSERT INTO transaction (amount, date, account_id) VALUES
     (20000.00,'2023-11-29', 1),
     (10000.00,'2023-12-01', 2);