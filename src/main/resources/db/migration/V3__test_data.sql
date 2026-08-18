INSERT INTO pracownik
    (nazwa, wiek, typ_pracownika, role,
     zdjecie, data_dolaczenia, data_rozpoczecia_pracy)
VALUES
    ('Juan Sanchez', 30, 'FIZYCZNY', 'SZEF',
     'zdjecie1.jpg', CURRENT_DATE, CURRENT_DATE);

INSERT INTO app_user
    (login, password, enabled, role, pracownik_id)
VALUES
    (
        'juan',
        '$2a$12$HJsNmddD/lNIEVu2/pmM2eiUFR1mW/kfhakBl8uki70oxxUoFRWGu',
        true,
        'ADMIN',
        (SELECT id_pracownik
         FROM pracownik
         WHERE nazwa = 'Juan Sanchez')
    );