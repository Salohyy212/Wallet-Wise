CREATE TABLE IF NOT EXISTS account (
      id SERIAL PRIMARY KEY,
      name VARCHAR(150) NOT NULL,
      balance DOUBLE PRECISION NOT NULL,
      currency_id int,
      FOREIGN KEY (currency_id) REFERENCES currency(id)
    );


INSERT INTO account (name, balance, currency_id) VALUES
   ('Bryan account',200000.00, 1),
   ('Elise account',170000.00, 2);
