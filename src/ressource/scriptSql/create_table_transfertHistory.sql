CREATE TABLE IF NOT EXISTS transferHistory (
       id_transfer SERIAL PRIMARY KEY,
       debit_transaction_id INT NOT NULL,
       credit_transaction_id INT NOT NULL,
       transfer_date DATE NOT NULL
);
