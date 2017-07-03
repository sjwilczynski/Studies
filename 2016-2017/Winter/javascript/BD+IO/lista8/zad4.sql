CREATE TABLE OSOBA(
id SERIAL PRIMARY KEY,
name VARCHAR(100),
surname VARCHAR(100),
height INTEGER,
id_miejsce_pracy INTEGER references MIEJSCE_PRACY(id) 
);

CREATE TABLE MIEJSCE_PRACY(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(100),
    field VARCHAR(100),
);


INSERT INTO MIEJSCE_PRACY(name,field,age) VALUES
('UWr', 'adres1', 'education'),
('Factory', 'adres2', 'industry');

INSERT INTO OSOBA(name,surname,height, id_miejsce_pracy) VALUES
( 'stachu', 'wilczynski', 181, 1),
( 'kuba', 'schreiber', 178, 1);
