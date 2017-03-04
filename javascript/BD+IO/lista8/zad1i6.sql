CREATE TABLE OSOBA1(
id INTEGER PRIMARY KEY,
name VARCHAR(100),
surname VARCHAR(100),
sex VARCHAR(100),
height INTEGER
);

CREATE SEQUENCE ids;

CREATE TABLE OSOBA2(
id SERIAL PRIMARY KEY, --sam sie inkrementuje
name VARCHAR(100),
surname VARCHAR(100),
sex VARCHAR(100),
height INTEGER
);


INSERT INTO OSOBA1 VALUES
(nextval('ids'), 'stachu', 'wilczynski', 'male', 181),
(nextval('ids'), 'kuba', 'schreiber', 'male', 178);

INSERT INTO OSOBA2(name,surname,sex,height) VALUES
( 'stachu', 'wilczynski', 'male', 181),
( 'kuba', 'schreiber', 'male', 178);


select * from osoba1 where id = 2;
select name, surname from osoba2;

DO
$do$
BEGIN 
FOR i IN 1..1000000 LOOP 
	insert into osoba2(name,surname,sex,height) values('osoba'|| CAST(i AS text),'nazwisko','male',180); 
END LOOP;
END
$do$;

select * from osoba2 LIMIT 50;
select * from osoba2 where surname = 'z klanu'; 
--bez indeksu: 188 ms
CREATE INDEX surname_idx
ON osoba2 (surname);
-- z indeksem: 1,6 ms