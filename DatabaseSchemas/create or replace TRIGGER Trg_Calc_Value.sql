create or replace TRIGGER Trg_Calc_Value AFTER
    INSERT OR UPDATE OF ilosc ON zamowienia_pozycje
    FOR EACH ROW
BEGIN
    update zamowienia_pozycje set wartosc = ilosc * cena;
END;