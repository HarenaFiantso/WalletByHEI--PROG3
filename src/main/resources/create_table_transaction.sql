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