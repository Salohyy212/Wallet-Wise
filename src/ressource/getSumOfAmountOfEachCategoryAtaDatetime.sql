CREATE OR REPLACE FUNCTION sum_by_category(
 id_bankAccount int,
 start_datetime timestamp,
 end_datetime timestamp
)
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN category = 'Restaurant ' THEN amount ELSE 0 END),0) AS restaurant_total,
        COALESCE(SUM(CASE WHEN category = 'Salary' THEN amount ELSE 0 END),0) AS salary_total
    FROM transaction t
    LEFT JOIN categories c ON t.category_id = c.id
    WHERE t.account_id = p_account_id
    AND t.transaction_date BETWEEN start_datetime AND end_datetime;

END;
