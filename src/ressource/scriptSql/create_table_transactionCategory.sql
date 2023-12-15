CREATE TABLE IF NOT EXISTS  transactionCategory (
      id SERIAL PRIMARY KEY,
      category_name VARCHAR(255) UNIQUE NOT NULL
);

ALTER TABLE "transactionCategory"
ADD COLUMN "category_type" VARCHAR(50) CHECK ("category_type" IN ('Expense', 'Income', 'Loan')) NOT NULL;


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Food and Drinks', 'Expense'
WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Food and Drinks');


INSERT INTO transactionCategory ( "category_name", "category_type")
SELECT 'Online Shopping', 'Expense'
WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Online Shopping');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Housing', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Housing');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Transportation', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Transportation');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Vehicle', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Vehicle');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Leisure', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Leisure');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Multimedia, Electronics', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Multimedia, Electronics');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Financial Fees', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Financial Fees');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Investments', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Investments');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Income', 'Income'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Income');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Other', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Other');


INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Unknown', 'Expense'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Unknown');

INSERT INTO transactionCategory ("category_name", "category_type")
SELECT 'Salary', 'Income'
    WHERE NOT EXISTS (SELECT 1 FROM transactionCategory WHERE "category_name" = 'Salary');

