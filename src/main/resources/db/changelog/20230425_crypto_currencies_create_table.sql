--liquibase formatted sql

--changeset Pavel Barzou:20230425_crypto_currencies_create_table logicalFilePath:db/changelogs/20230425_crypto_currencies_create_table.sql

CREATE TABLE IF NOT EXISTS crypto_currencies (
  id BIGSERIAL PRIMARY KEY,
  timestamp TIMESTAMP NOT NULL,
  symbol VARCHAR(10) NOT NULL,
  price DECIMAL(10,2) NOT NULL
);

--rollback DROP TABLE stocks;

COMMENT ON COLUMN crypto_currencies.id IS 'Unique identifier';
COMMENT ON COLUMN crypto_currencies.timestamp IS 'Time when the tick occurred';
COMMENT ON COLUMN crypto_currencies.symbol IS 'Crypto currency symbol';
COMMENT ON COLUMN crypto_currencies.price IS 'Price for the tick';