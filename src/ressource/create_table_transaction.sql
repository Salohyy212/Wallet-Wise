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
VALUES ('Online Purchase', 500.00, '2023-12-01 10:30:00', 1, 'debit');

INSERT INTO transaction (label, amount, date_time, account_id, type)
VALUES ('Salary', 3000.00, '2023-12-02 15:45:00', 2, 'credit');
