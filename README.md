# Bricks J(udge)

Program sędziowski obsługujący grę "Cegiełki".

## Zasady gry
* Mapa złożona jest z n x n kwadratowych pól, gdzie n jest liczbą nieparzystą
* Gracze na zmianę układają na mapie klocki 2x1 lub 1x2
* Wygrywa gracz, który zablokuje przeciwnikowi możliwość ruchu

## Protokół komunikacji
Komunikacja między algorytmem gracza, a programem sędziowskim odbywa się za pomocą standardowego systemu wejścia-wyjścia.

Podstawą do uruchomienia każdego algorytmu jest plik *.info*, zawierający w kolejnych liniach:
* Komendę uruchomieniową programu
* Pełną nazwę programu

Oznaczenia:
* S - sędzia
* P<sub>1</sub>/P<sub>2</sub> - program 1/program 2

### Inicjalizacja
* S -> P<sub>1</sub>: n_A<sub>1</sub>xB<sub>1</sub>_..._A<sub>n</sub>xB<sub>n</sub> *#Wysłanie komunikatu o ustawieniu planszy rozmiaru n z predefiniowanymi cegiełkami*
* P<sub>1</sub> -> S: ok

Następnie ta sama operacja dla P<sub>2</sub>

### Ruch
* S -> P<sub>1</sub>: start *#Pierwszy ruch*
* P<sub>1</sub> -> S: AxB_CxD *#Odpowiedź w koordynatów postawionej cegiełki*
* S -> P<sub>2</sub>: AxB_CxD
* P<sub>2</sub> -> S: A<sub>2</sub>xB<sub>2</sub>_C<sub>2</sub>xD<sub>2</sub>

Operacja powtarzana, aż do końca możliwych ruchów

## Ograniczenia
* Maksymalny na inicjalizację stały i ustalony
* Maksymalny na ruch stały i ustalony