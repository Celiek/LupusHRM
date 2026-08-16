CREATE TABLE pracownik (
    id_pracownik BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nazwa VARCHAR(255) NOT NULL,
    wiek INTEGER NOT NULL,
    typ_pracownika VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    uprawnienia VARCHAR(255) NOT NULL,
    zdjecie VARCHAR(255),
    data_dolaczenia DATE NOT NULL,
    data_rozpoczecia_pracy DATE NOT NULL
);

CREATE TABLE czas_pracy (
    id_czas_pracy BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pracownik_id BIGINT NOT NULL,
    data_pracy DATE NOT NULL,
    start_pracy TIME NOT NULL,
    stop_pracy TIME,

    CONSTRAINT fk_pracownik
        FOREIGN KEY (pracownik_id)
        REFERENCES pracownik(id_pracownik)
        ON DELETE CASCADE
);