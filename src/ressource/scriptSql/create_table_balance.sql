CREATE TABLE IF NOT EXISTS balance (
       id SERIAL PRIMARY KEY,
       account_id INT NOT NULL,
       balance DOUBLE PRECISION NOT NULL,
       last_update TIMESTAMP NOT NULL,
       FOREIGN KEY (account_id) REFERENCES account(id)
 );
