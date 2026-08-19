create table wyplaty (
    id_wyplaty BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data_wyplaty DATE NOT NULL,
    data_od DATE NOT NULL,
    data_do DATE NOT NULL,
    kwota_wyplaty NUMERIC(10,2) NOT NULL,
    pracownik_id BIGINT NOT NULL,
    CONSTRAINT fk_wyplaty_pracownik FOREIGN KEY (pracownik_id)
    REFERENCES pracownik(id_pracownik)
    ON DELETE CASCADE,
    CONSTRAINT chk_okres_wyplaty
        CHECK (data_od <= data_do)
);

create table zaliczki(
    id_zaliczki BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pracownik_id BIGINT NOT NULL,
    kwota NUMERIC(10,2),
    data_zaliczki DATE,
    CONSTRAINT fk_zaliczki_pracownik FOREIGN KEY(pracownik_id)
    REFERENCES pracownik(id_pracownik)
    ON DELETE CASCADE
);

