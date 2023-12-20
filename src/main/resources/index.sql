CREATE DATABASE wallet_by_hei;

\c wallet_by_hei

-- Creating currency table and types
CREATE TYPE currency_name AS ENUM ('Euro', 'Ariary');
CREATE TYPE currency_code AS ENUM ('EUR', 'MGA');
CREATE TABLE IF NOT EXISTS currency
(
    currency_id   SERIAL PRIMARY KEY,
    currency_name currency_name NOT NULL,
    currency_code currency_code NOT NULL
);

-- Creating table account and type
CREATE TYPE account_type AS ENUM ('BANK', 'CASH', 'MOBILE MONEY');
CREATE TABLE IF NOT EXISTS account
(
    account_id   SERIAL PRIMARY KEY,
    account_name VARCHAR(255) NOT NULL,
    account_type account_type NOT NULL,
    currency_id  INT          NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currency (currency_id)
);

-- Creating category table
CREATE TABLE IF NOT EXISTS category
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

-- Creating tables transaction & category and type
CREATE TYPE transaction_type AS ENUM ('CREDIT', 'DEBIT');
CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id   SERIAL PRIMARY KEY,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_type transaction_type NOT NULL,
    amount           DOUBLE PRECISION NOT NULL,
    label            VARCHAR(255)     NOT NULL,
    account_id       INT              NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

-- Add category_id column to the "transaction" table if it doesn't exist
DO $$
    BEGIN
        BEGIN
            ALTER TABLE "transaction"
                ADD COLUMN category_id INT NOT NULL;
        EXCEPTION
            WHEN duplicate_column THEN
            -- Ignore if the column already exists
        END;
    END $$;

-- Add foreign key constraint if it doesn't exist
DO $$
    BEGIN
        BEGIN
            ALTER TABLE "transaction"
                ADD CONSTRAINT fk_transaction_category
                    FOREIGN KEY (category_id) REFERENCES category(category_id);
        EXCEPTION
            WHEN duplicate_object THEN
            -- Ignore if the constraint already exists
        END;
    END $$;