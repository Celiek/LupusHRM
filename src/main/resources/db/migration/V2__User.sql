CREATE TABLE app_user (
    id_user BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    role VARCHAR(50) NOT NULL,
    pracownik_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_user_pracownik
        FOREIGN KEY (pracownik_id)
        REFERENCES pracownik(id_pracownik)
        ON DELETE CASCADE
);