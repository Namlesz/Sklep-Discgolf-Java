-- Generated by Oracle SQL Developer Data Modeler 20.4.1.406.0906
--   at:        2021-05-22 12:49:23 CEST
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE klienci (
    id              INTEGER NOT NULL,
    imie            VARCHAR2(15 CHAR) NOT NULL,
    nazwisko        VARCHAR2(20 CHAR) NOT NULL,
    data_urodzenia  DATE,
    email           VARCHAR2(25 CHAR) NOT NULL,
    miasto          VARCHAR2(20 CHAR) NOT NULL,
    ulica           VARCHAR2(20 CHAR) NOT NULL,
    nr_domu         VARCHAR2(5 CHAR) NOT NULL,
    nr_mieszkania   INTEGER,
    ranking_id      INTEGER NOT NULL,
    jwt_token       VARCHAR2(200 BYTE)
);

COMMENT ON TABLE klienci IS
    '.';

CREATE UNIQUE INDEX klienci_idx ON
    klienci (
        id
    ASC );

CREATE INDEX klienci_idx_nazwisko ON
    klienci (
        nazwisko
    ASC );

CREATE INDEX klienci_idx_email ON
    klienci (
        email
    ASC );

CREATE INDEX klienci_idx_miasto ON
    klienci (
        miasto
    ASC );

CREATE UNIQUE INDEX klienci_idx_ranking ON
    klienci (
        ranking_id
    ASC );

ALTER TABLE klienci ADD CONSTRAINT klienci_pk PRIMARY KEY ( id );

CREATE TABLE lista_turniejowa (
    klienci_id   INTEGER NOT NULL,
    turnieje_id  INTEGER NOT NULL
);

COMMENT ON TABLE lista_turniejowa IS
    '.';

ALTER TABLE lista_turniejowa ADD CONSTRAINT lista_turniejowa_pk PRIMARY KEY ( klienci_id,
                                                                              turnieje_id );

CREATE TABLE towary (
    id               INTEGER NOT NULL,
    kod_towaru       VARCHAR2(10 CHAR) NOT NULL,
    nazwa_towaru     VARCHAR2(250 CHAR) NOT NULL,
    jednostka_miary  VARCHAR2(10 CHAR) NOT NULL,
    ean              VARCHAR2(13 CHAR),
    vat              INTEGER NOT NULL,
    ocena            INTEGER
);

ALTER TABLE towary
    ADD CONSTRAINT jmname CHECK ( jednostka_miary IN ( 'karton', 'paczka', 'szt' ) );

ALTER TABLE towary
    ADD CONSTRAINT ocenazakres CHECK ( ocena BETWEEN 1 AND 10 );

COMMENT ON TABLE towary IS
    '.';

CREATE UNIQUE INDEX towary__idx ON
    towary (
        id
    ASC );

CREATE INDEX towary__nazwa ON
    towary (
        nazwa_towaru
    ASC );

CREATE INDEX towary__kodtowaru ON
    towary (
        kod_towaru
    ASC );

CREATE INDEX towary__ean ON
    towary (
        ean
    ASC );

ALTER TABLE towary ADD CONSTRAINT towary_pk PRIMARY KEY ( id );

CREATE TABLE turnieje (
    id     INTEGER NOT NULL,
    nazwa  VARCHAR2(20 CHAR) NOT NULL,
    data   DATE NOT NULL
);

COMMENT ON TABLE turnieje IS
    '.';

CREATE UNIQUE INDEX turnieje__idx ON
    turnieje (
        id
    ASC );

ALTER TABLE turnieje ADD CONSTRAINT turnieje_pk PRIMARY KEY ( id );

CREATE TABLE zamowienia (
    id                INTEGER NOT NULL,
    data_zamowiena    DATE NOT NULL,
    numer_zamowienia  VARCHAR2(20 CHAR) NOT NULL,
    klienci_id        INTEGER NOT NULL,
    status            VARCHAR2(20 CHAR)
);

ALTER TABLE zamowienia
    ADD CONSTRAINT "Status name" CHECK ( status IN ( 'odebrano', 'oplacone', 'przyjete', 'w drodze', 'w przygotowaniu',
                                                     'wyslano' ) );

COMMENT ON TABLE zamowienia IS
    '.';

CREATE UNIQUE INDEX zamowienia__idx ON
    zamowienia (
        id
    ASC );

CREATE INDEX zamowienia__klient ON
    zamowienia (
        klienci_id
    ASC );

CREATE INDEX zamowienia__numerzamowienia ON
    zamowienia (
        numer_zamowienia
    ASC );

CREATE INDEX zamowienia__data ON
    zamowienia (
        data_zamowiena
    ASC );

ALTER TABLE zamowienia ADD CONSTRAINT zamowienia_pk PRIMARY KEY ( id );

CREATE TABLE zamowienia_pozycje (
    id             INTEGER NOT NULL,
    ilosc          INTEGER NOT NULL,
    cena           NUMBER(10, 2) NOT NULL,
        wartosc        NUMBER(20, 2) AS ( ilosc * cena ) VIRTUAL NOT NULL,
    zamowienia_id  INTEGER NOT NULL,
    towary_id      INTEGER NOT NULL
);

COMMENT ON TABLE zamowienia_pozycje IS
    '.';

CREATE UNIQUE INDEX zamowienia_pozycje__idx ON
    zamowienia_pozycje (
        id
    ASC );

CREATE INDEX zamowienia_pozycje__zamowienia ON
    zamowienia_pozycje (
        zamowienia_id
    ASC );

CREATE UNIQUE INDEX zamowienia_pozycje__idxv1 ON
    zamowienia_pozycje (
        towary_id
    ASC );

ALTER TABLE zamowienia_pozycje ADD CONSTRAINT zamowienia_pozycje_pk PRIMARY KEY ( id );

ALTER TABLE lista_turniejowa
    ADD CONSTRAINT lista_turniejowa_klienci_fk FOREIGN KEY ( klienci_id )
        REFERENCES klienci ( id );

ALTER TABLE lista_turniejowa
    ADD CONSTRAINT lista_turniejowa_turnieje_fk FOREIGN KEY ( turnieje_id )
        REFERENCES turnieje ( id );

ALTER TABLE zamowienia
    ADD CONSTRAINT zamowienia_klienci_fk FOREIGN KEY ( klienci_id )
        REFERENCES klienci ( id );

ALTER TABLE zamowienia_pozycje
    ADD CONSTRAINT zamowienia_pozycje FOREIGN KEY ( zamowienia_id )
        REFERENCES zamowienia ( id );

ALTER TABLE zamowienia_pozycje
    ADD CONSTRAINT zamowienia_pozycje_towary_fk FOREIGN KEY ( towary_id )
        REFERENCES towary ( id );

CREATE OR REPLACE VIEW PozycjeZamowienia ( id, ilosc, cena, wartosc, kod_towaru, nazwa_towaru, ean, id3 ) AS
SELECT
    zamowienia_pozycje.id,
    zamowienia_pozycje.ilosc,
    zamowienia_pozycje.cena,
    zamowienia_pozycje.wartosc,
    towary.kod_towaru,
    towary.nazwa_towaru,
    towary.ean,
    zamowienia_pozycje.id3
FROM
         zamowienia_pozycje
    INNER JOIN towary ON towary.id = zamowienia_pozycje.id1 
;

CREATE OR REPLACE VIEW Zamowieniaklienta ( imie, nazwisko, email, data_zamowiena, numer_zamowienia, status, id ) AS
SELECT
    klienci.imie,
    klienci.nazwisko,
    klienci.email,
    zamowienia.data_zamowiena,
    zamowienia.numer_zamowienia,
    zamowienia.status,
    zamowienia.id
FROM
         klienci
    INNER JOIN zamowienia ON klienci.id = zamowienia.id2 
;

CREATE SEQUENCE klienci_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER klienci_id_trg BEFORE
    INSERT ON klienci
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := klienci_id_seq.nextval;
END;
/

CREATE SEQUENCE towary_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER towary_id_trg BEFORE
    INSERT ON towary
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := towary_id_seq.nextval;
END;
/

CREATE SEQUENCE turnieje_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER turnieje_id_trg BEFORE
    INSERT ON turnieje
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := turnieje_id_seq.nextval;
END;
/

CREATE SEQUENCE zamowienia_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER zamowienia_id_trg BEFORE
    INSERT ON zamowienia
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := zamowienia_id_seq.nextval;
END;
/

CREATE SEQUENCE zamowienia_pozycje_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER zamowienia_pozycje_id_trg BEFORE
    INSERT ON zamowienia_pozycje
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := zamowienia_pozycje_id_seq.nextval;
END;
/



-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                             6
-- CREATE INDEX                            17
-- ALTER TABLE                             14
-- CREATE VIEW                              2
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           5
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          5
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
