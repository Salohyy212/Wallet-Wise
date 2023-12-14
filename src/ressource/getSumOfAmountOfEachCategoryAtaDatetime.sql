CREATE OR REPLACE FUNCTION sum_by_category(
    account_id int,
    start_datetime timestamp,
    end_datetime timestamp,
    category_name VARCHAR(255)
)
RETURNS TABLE (category_total numeric)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN c.category_name = category_name THEN amount ELSE 0 END), 0) AS category_total
    FROM transaction t
    LEFT JOIN transactionCategory c ON t.category_id = c.id
    WHERE t.account_id = p_account_id
      AND t.transaction_date BETWEEN start_datetime AND end_datetime;

END;
