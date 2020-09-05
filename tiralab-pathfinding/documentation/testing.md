## Tests

## Tehokkuustestit

Algoritmien tehokkuutta voi ohjelmassa vertailla kahdella kartalla käyttäen käsinpoimittuja alku- ja loppusolmuja polunetsinnässä.
Kukin algoritmi suoritetaan valituilla solmuilla valinnan mukaan joko 10 tai 100 kertaa ja kirjataan ylös eri algoritmien suoritukseen kuluneiden aikojen keskiarvot.
Testeihin käytetyt kartat on otettu Moving AI Labsin sivuilta. Kartoista toinen on sokkelo ja toinen on avoimempi, paljon tyhjää tilaa sisältävä kartta ("Luola"). Molemmat ovat kokoa 1073px x 1073px.

Testasin algoritmeja seuraavilla käsin poimituilla skenaarioilla, joista kukin suoritettiin 100 kertaa:

* Luola 1: polun pituus 508 solmua, alku- ja loppukoordinaatit (112,146)-(424,534)

* Luola 2: polun pituus 1002 solmua, alku- ja loppukoordinaatit (98,217)-(1043,574)

* Sokkelo 1: polun pituus 505 solmua, alku- ja loppukoordinaatit (669,455)-(1042,312)

* Sokkelo 2: polun pituus 1020 solmua, alku- ja loppukoordinaatit (682,82)-(154,545)

* Sokkelo 3: polun pituus 1567 solmua, alku- ja loppukoordinaatit (242,233)-(474,118)

* Sokkelo 4: polun pituus 2020 solmua, alku- ja loppukoordinaatit (48,161)-(1002,143)

|          | Luola 1 | Luola 2 | Sokkelo 1 | Sokkelo 2 | Sokkelo 3 | Sokkelo 4 |
|----------|---------|---------|-----------|-----------|-----------|-----------|
| Dijkstra | 84 ms   | 165 ms  | 79 ms     | 97 ms     | 104 ms    | 147 ms    |
| A*       | 48 ms   | 153 ms  | 37 ms     | 78 ms     | 99 ms     | 118 ms    |
| JPS      | 119 ms  | 361 ms  | 18 ms     | 17 ms     | 22 ms     | 38 ms     |

Sokkeloskenaarioissa on selkeimmin nähtävillä algoritmien väliset erot. Eriyisesti JPS on kaikilla syötteillä 
merkittävästi kahta muuta algoritmia nopeampi. Luolaskenaarioissa JPS puolestaan jää selvästi jälkeen kahdesta muusta algoritmista.
Asiaan vaikuttanee jonkin verran se, että avoimessa tilassa JPS tekee herkemmin pitkiä 'turhia' hyppäyksiä, mutta testeissä havaittu ajallinen 
ero on sen verran suuri että epäilen vähän toteutukseni toimivuutta. Perimmäistä syytä tälle hitaudelle en ole kuitenkaan onnistunut selvittämään.

### Yksikkötestit

Yksikkötestit on toteutettu JUnitilla. Omia tietorakenteita (keko ja pino) sekä karttojen käsittelyyn käytettyjä luokkia Maze ja Node on testattu. GUI-, Test- ja MyIO-luokat on tarkoituksella jätetty testauksen ulkopuolelle. Itse algoritmeja ei ole juuri yksikkötestattu, sillä en keksinyt mitä muuta kuin löydetyn polun pituutta voi luotettavasti testata automaattisesti. Kartalta löydetyn polun testaus tyssäsi siihen, kun kartalta väistämättä löytyi useampi mahdollinen lyhyin polku.

![Testikattavuusraportti](https://i.imgur.com/vnCXpjZ.png)
