CREATE TABLE IF NOT EXISTS CurrencyValue (
    id SERIAL PRIMARY KEY,
    id_currency_source VARCHAR(50) NOT NULL,
    id_dcurrency_destination VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    effective_date DATE NOT NULL
    );
