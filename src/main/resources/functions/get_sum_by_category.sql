-- Create a function to get the sum of amounts in each category within a date range
CREATE OR REPLACE FUNCTION get_sum_by_category(
    selected_account_id INT,
    start_datetime TIMESTAMP,
    end_datetime TIMESTAMP
)
    RETURNS TABLE
            (
                category_name VARCHAR(50),
                sum_amount    DOUBLE PRECISION
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT c.category_name,
               COALESCE(SUM(t.amount), 0) AS sum_amount
        FROM category c
                 LEFT JOIN
             "transaction" t
             ON c.category_id = t.category_id AND t.transaction_date_time BETWEEN start_datetime AND end_datetime AND
                t.account_id = selected_account_id
        GROUP BY c.category_name;
END;
$$;