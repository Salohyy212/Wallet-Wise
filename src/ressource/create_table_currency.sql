CREATE TABLE IF NOT EXISTS currency (
       id SERIAL PRIMARY KEY,
       name VARCHAR(150),
       symbol VARCHAR(50)
    );


INSERT INTO currency (name, symbol) VALUES
   ('Ariary','Ar'),
   ('Dollar','$');