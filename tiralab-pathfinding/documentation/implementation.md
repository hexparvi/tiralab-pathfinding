## Toteutus

### Paketit ja luokat

.pathfinding - sisältää ohjelman Main-luokan ja polunetsintäalgoritmit.

**Astar:** A*/Dijkstra toteutus

**Heuristic:** auttaa algoritmeissa tarvittavassa heuristiikassa

**JPS:** JPS-toteutus

**Pathfinder:** algoritmien rajapinta

---

.domain - sisältää karttojen esittämiseen ja käsittelyyn tarvittavat luokat

**Node:** kuvaa yksittäistä verkon solmua (ts. yksittäistä pikseliä .png-tiedostossa)

**Maze:** kuvaa karttaa kaksiulotteisena Node-arrayna

---

.ui - sisältää graafisen käyttöliittymän

**GUI:** ohjelman graafinen käyttöliittymä, josta ohjelma ja tehokkuustestit suoritetaan. Hoitaa myös polun piirtämisen kartalle

---

.util - sisältää algoritmien käyttämien tietorakenteiden toteutukset

**MinHeap:** Nodeista koostuva binääriminimikeko

**NodeStack:** Nodeista koostuva pino

---

.test - sisältää tehokkuustestauksessa käytettävän Test-luokan

**Test:** sisältää tehokkuustesteissä käytetyn logiikan

---

.io - sisältää .png-muotoisten karttojen lukuun ja kirjoitukseen tarvittavan MyIO-luokan

**MyIO:** muokkaa .png-muotoiset kartat kaksiulotteiseksi pikseliarrayksi ja päinvastoin

---

### Puutteet

Yleisen hiomisen lisäksi työhön olisi voinut vielä toteuttaa tavan syöttää tehokkuustesteille etsittävien polkujen alku- ja loppusolmuja vaikkapa tekstitiedostosta. Nykyisellään solmujen syöttäminen käsin hankaloittaa testaamista. Vertailun monipuolistamisen avuksi olisi voinut myös toteuttaa kartan vapaan valitsemisen. Algoritmien toteutuksessa itseäni jäi vaivaamaan eniten JPS:n naapurien karsiminen; nykyinen pruneNeighbors-metodi on aikamoinen hirviö. En oikein uskaltanut koskea metodiin enää jälkeenpäin, kun koko algoritmin ylipäätään toimivaksi saattaminen aiheutti jo itsessään niin paljon päänvaivaa.

---

### Lähteet
* https://en.wikipedia.org/wiki/A*_search_algorithm
* https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
* D. Harabor and A. Grastien. Online Graph Pruning for Pathfinding on Grid Maps. In National Conference on Artificial Intelligence (AAAI), 2011.
* https://harablog.wordpress.com/2011/09/07/jump-point-search/
* https://en.wikipedia.org/wiki/Euclidean_distance
