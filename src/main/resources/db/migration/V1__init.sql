CREATE TABLE pracownik (
    id_pracownik BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nazwa VARCHAR(255) NOT NULL,
    wiek INTEGER NOT NULL,
    typ_pracownika VARCHAR(255) NOT NULL,
    uprawnienia VARCHAR(255) NOT NULL,
    zdjecie VARCHAR(255),
    data_dolaczenia DATE NOT NULL,
    data_rozpoczecia_pracy DATE NOT NULL
);

CREATE TABLE czas_pracy (
    id_czas_pracy BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pracownik BIGINT NOT NULL,
    start_pracy TIMESTAMP NOT NULL,
    stop_pracy TIMESTAMP NOT NULL,

    CONSTRAINT fk_pracownik
        FOREIGN KEY (id_pracownik)
        REFERENCES pracownik(id_pracownik)
        ON DELETE CASCADE
);