## Toteutus

### Paketit ja luokat

.pathfinding - sisältää ohjelman Main-luokan ja polunetsintäalgoritmit.
Astar: A*/Dijkstra toteutus
Heuristic: auttaa algoritmeissa tarvittavassa heuristiikassa
JPS: JPS-toteutus
Pathfinder: algoritmien rajapinta

.domain - sisältää karttojen esittämiseen ja käsittelyyn tarvittavat luokat
Node: kuvaa yksittäistä verkon solmua (ts. yksittäistä pikseliä .png-tiedostossa)
Maze: kuvaa karttaa kaksiulotteisena Node-arrayna

.ui - sisältää graafisen käyttöliittymän
GUI: ohjelman graafinen käyttöliittymä, josta ohjelma ja tehokkuustestit suoritetaan. Hoitaa myös polun piirtämisen kartalle

.util - sisältää algoritmien käyttämien tietorakenteiden toteutukset
MinHeap: Nodeista koostuva binääriminimikeko
NodeStack: Nodeista koostuva pino

.test - sisältää tehokkuustestauksessa käytettävän Test-luokan
Test: sisältää tehokkuustesteissä käytetyn logiikan

.io - sisältää .png-muotoisten karttojen lukuun ja kirjoitukseen tarvittavan MyIO-luokan
MyIO: muokkaa .png-muotoiset kartat kaksiulotteiseksi pikseliarrayksi ja päinvastoin

### Lähteet
* https://en.wikipedia.org/wiki/A*_search_algorithm
* https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
* D. Harabor and A. Grastien. Online Graph Pruning for Pathfinding on Grid Maps. In National Conference on Artificial Intelligence (AAAI), 2011.
* https://harablog.wordpress.com/2011/09/07/jump-point-search/