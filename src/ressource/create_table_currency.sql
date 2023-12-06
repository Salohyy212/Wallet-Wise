CREATE TABLE IF NOT EXISTS currency (
       id SERIAL PRIMARY KEY,
       name VARCHAR(150),
       code VARCHAR(255)
    );


INSERT INTO currency (name, code) VALUES
   ('Ariary','MGA'),
   ('Euro','EUR');