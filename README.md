# TPO4

Program jest aplikacją klient-serwer napisaną w języku Java, wykorzystującą gniazda (sockets), kanały (channels) i selektory (selectors) do obsługi wielu klientów jednocześnie. Aplikacja umożliwia komunikację pomiędzy klientami a serwerem poprzez subskrypcję i otrzymywanie wiadomości na tematy zainteresowań.

Serwer:
Serwer obsługuje połączenia od wielu klientów, przy użyciu selektorów monitorujących dostępność do odczytu i zapisu na gniazdach.
Po nawiązaniu połączenia, klient może zapisać się lub zrezygnować z subskrypcji na wybrane tematy, wysyłając odpowiednie komunikaty.
Serwer przechowuje listę subskrybentów dla każdego tematu.
Dodatkowy program administratora pozwala na zarządzanie tematami, w tym dodawanie nowych tematów, usuwanie istniejących tematów i informowanie klientów o zmianach.
Serwer odbiera wiadomości od administratora i przesyła je do odpowiednich subskrybentów zainteresowanych danym tematem.

Klient:
Klient może łączyć się z serwerem i subskrybować lub rezygnować z subskrypcji wybranych tematów.
Po subskrypcji, klient otrzymuje wiadomości na tematy zainteresowań od serwera.
Klient ma możliwość przeglądania otrzymanych wiadomości w prostym interfejsie użytkownika.

Interfejs użytkownika:
Aplikacja posiada proste GUI, które separuje interakcję użytkownika od logiki przetwarzania danych.
Interfejs użytkownika umożliwia klientom przeglądanie dostępnych tematów oraz subskrybowanie lub rezygnację z subskrypcji.
Dla administratora dostępne są funkcje zarządzania tematami, takie jak dodawanie i usuwanie tematów.

Odporność na sytuacje awaryjne:
Program jest odporny na różne sytuacje awaryjne, takie jak utrata połączenia z klientem lub administratorem, błędy sieciowe lub błędy w komunikacji.

W skrócie, program implementuje system komunikacji klient-serwer z wykorzystaniem gniazd, kanałów i selektorów. Zapewnia on możliwość subskrypcji i otrzymywania wiadomości na tematy zainteresowań oraz proste GUI dla użytkowników i administratora. Dodatkowo, program jest zaprojektowany w sposób odporny na różne sytuacje awaryjne, co zapewnia niezawodność i stabilność działania.
