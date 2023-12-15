CREATE OR REPLACE FUNCTION sum_entries_and_exits(
    account_id INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP
)
RETURNS TABLE (total_entries DECIMAL, total_exits DECIMAL) AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN tc.transaction_type IN ('Income', 'Loan') THEN t.amount ELSE 0 END), 0) AS total_entries,
        COALESCE(SUM(CASE WHEN tc.transaction_type = 'Expense' THEN t.amount ELSE 0 END), 0) AS total_exits
    FROM transactions t
    LEFT JOIN transactionCategory tc ON t.category_id = tc.id
    WHERE t.account_id = account_id
      AND t.transaction_date BETWEEN start_date AND end_date;
END;
$$ LANGUAGE plpgsql;


