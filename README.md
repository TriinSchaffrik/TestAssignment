# Proovitöö

Tegemist on Androidi rakendusega, mis kuvab DummyJSON API-st saadud toodete nimekirja. Rakenduse käivitamisel laaditakse API-st 20 toodet, mida saab sirvida nimekirjavaates. Tootele vajutades avaneb selle detailvaade, kus kuvatakse toote pilt, nimi ja kirjeldus.
Rakendus on üles ehitatud Kotlinile ja Jetpack Compose'ile.

---

## Lokaalne käivitamine

### Versioonid
* **Java SDK:** Java 17+
* **Kotlin:** `2.4.20` | **AGP:** `9.3.2`
* **targetSdk/compileSdk:** API 37 | **minSdk:** API 24

### Käivitamise sammud
1. Ava projekt **Android Studio** keskkonnas.
2. Oota, kuni **Gradle Sync** lõpetab töö.
3. Vali ühendatud seade/emulaator ja vajuta **Run** (`▶`) nuppu (`Shift + F10`).

Käsurealt ehitamine:
```bash
./gradlew installDebug
```

Testide käivitamiseks:
```bash
./gradlew test
```
---

## Arhitektuur ja tehnilised valikud

Projekt põhineb MVVM arhitektuuril:

```text
UI (Compose)  ➔  ViewModel  ➔  Repository  ➔  Retrofit API
```

### Kasutatud tööriistad:
* **MVVM + StateFlow:** `ProductListViewModel` juhib toodete laadimist ja UI olekuid (`Loading`, `Success`, `Error`).
* **Repository muster:** eraldab API suhtluse ViewModelist, võimaldades testides kasutada `FakeProductRepository`-t.
* **Koin:** dependency injection.
* **Retrofit + Gson:** API päringud ja JSON-i parsimine. https://dummyjson.com/products?limit=20
* **Coil 3:** piltide asünkroonne laadimine ja vahemällu salvestamine (caching).
* **Navigation Compose:** vaadete vahel liikumine.
* **Error handling:** API vea korral kuvatakse veateade ja kasutajal on võimalik päringut uuesti proovida.

*Märkus: Detailvaade eraldi API päringut ei tee. Kuna andmemaht on väike (20 toodet), võetakse toote andmed ID alusel otse olemasolevast listist.*

---

## Mida teeksin suuremas pärisprojektis teisiti?

Proovitöö puhul hoidsin arhitektuuri teadlikult lihtsa ja projekti mahuga proportsionaalsena. Suuremas rakenduses teeksin tõenäoliselt järgmised täiendused:

1. Kui toodete arv oleks suurem laeksin andmeid järk-järgult vastavalt kasutaja kerimisele (Paging 3).

2. Kui detailvaade muutuks keerukamaks või peaks toetama deep link'e ja iseseisvat andmete värskendamist, looksin sellele eraldi ViewModeli ja `getProductById(id)` API päringu.

3. Praegu kuvatakse veateate puhul API-st või süsteemist saadud sõnum kasutajale üsna otse. Pärisrakenduses tuleks tehnilised veateated teisendada kasutajale arusaadavateks sõnumiteks ning võimalik, et erinevaid veaolukordi eraldi käsitleda.

4. Suurema meeskonna ja kasvava funktsionaalsuse puhul kaaluksin projekti jagamist feature- ja core-mooduliteks.