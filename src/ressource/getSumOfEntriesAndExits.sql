CREATE OR REPLACE FUNCTION sum_entries_and_exits(
    account_id INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP
)
RETURNS TABLE (total_entries DECIMAL, total_exits DECIMAL) AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN transaction_type = 'CREDIT' THEN amount ELSE 0 END), 0) AS total_entries,
        COALESCE(SUM(CASE WHEN transaction_type = 'DEBIT' THEN amount ELSE 0 END), 0) AS total_exits
    FROM transactions
    WHERE account_id = account_id
    AND transaction_date BETWEEN start_date AND end_date;
END;



