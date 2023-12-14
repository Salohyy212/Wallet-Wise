CREATE TABLE IF NOT EXISTS transaction (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    date_time TIMESTAMP NOT NULL,
    account_id int,
    type VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
    );



INSERT INTO transaction (label, amount, date_time, account_id, type)
VALUES ('Online Purchase', 500.00, CURRENT_TIMESTAMP, 9, 'debit'),
       ('Salary', 3000.00, CURRENT_TIMESTAMP, 10, 'credit');

ALTER TABLE transaction
ADD COLUMN category_id INT,
ADD FOREIGN KEY (category_id) REFERENCES transactionCategory(id);
