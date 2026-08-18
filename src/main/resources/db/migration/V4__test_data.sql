INSERT INTO pracownik (nazwa, wiek, typ_pracownika, role, zdjecie, data_dolaczenia, data_rozpoczecia_pracy)
VALUES
('Andrzej Nowak', 30, 'FIZYCZNY', 'PRACOWNIK', 'zdjecie1.jpg', CURRENT_DATE, CURRENT_DATE),
('Rafał Boskowski', 20, 'FIZYCZNY', 'PRACOWNIK', 'zdjecie1.jpg', CURRENT_DATE, CURRENT_DATE),
('Marcel Duda', 33, 'FIZYCZNY', 'PRACOWNIK', 'zdjecie1.jpg', CURRENT_DATE, CURRENT_DATE),
('Michał Karmowski', 32, 'FIZYCZNY', 'PRACOWNIK', 'zdjecie1.jpg', CURRENT_DATE, CURRENT_DATE);


INSERT INTO czas_pracy (data_pracy, start_pracy, stop_pracy, pracownik_id)
SELECT '2026-08-14', '06:00:00', '18:00:00', id_pracownik
FROM pracownik
WHERE nazwa IN ('Andrzej Nowak', 'Rafał Boskowski', 'Marcel Duda', 'Michał Karmowski');

INSERT INTO czas_pracy (data_pracy, start_pracy, stop_pracy, pracownik_id)
SELECT '2026-08-13', '06:00:00', '18:00:00', id_pracownik
FROM pracownik
WHERE nazwa IN ('Andrzej Nowak', 'Rafał Boskowski', 'Marcel Duda', 'Michał Karmowski');

