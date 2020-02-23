Cune Andrei 321CB 
README Tema2

Implementarea se face astfel:
--Baza de date va avea 2 array list uri in care se vor afla nodurile si entitatile,nume,nr de noduri si capacitatea maxima a unui nod
--O entitate va avea nume,nr de atribute,rf-ul,cheia primara si 2 array-list-uri ,unul pentru tipul atributului(String,float,int) si atributul in sine(ex. Pret etc.)
--O instanta va avea numele entitatii de care aparine , un timestamp si un arraylist in care stocam valorile efective ale atributelor(care vor fi de tip obiect)
-- Un nod va avea un arraylist de instante si un index cu care retinem ordinea initiala a nodurilor.
--Vom avea si o  clasa cu care vom sorta nodurile, unde suprascriem metoda compare.


Se incepe prin deschiderea fisierelor de intrare/iesire , si construim baza de date goala.
Folosind un string tokeneizer vom citi  comenzile de pe fiecare linie pana la sfarsitul fisierului.

CREATEDB:
Setam numele , numarul de noduri si max_capacity , si apoi adaugam nodurile.

CREATE:
Initializam entitatea (goala) si in apoi setam valorile citite pentru entitatea noastra tinand cont si de tipul lor.In continuare vom citi tipurile si atributele entitatii ,setam cheia primara si adaugam entitatea in lista din database.

Insert:
Initializam o instanta triviala, ii setam numele entitatii si apoi gasim entitatea respectiva dupa nume in lista.Astfel verificam dupa lista de tipuri din entitate ce fel de element adaugam.Asa se insereaza toate valorile atributelor in instanta.
Vom sorta nodurile dupa numarul de instante si dupa indexul original al nodurilor.
Vom clona entitatea si clonele le vom adauga in rf noduri , atata timp cat dimensiune nodului nu depaseste capacitatea maxima.

Delete:
Citim numele entitatii si cheia primara ,gasim entitatea ( e ) .Vom cauta in toate nodurile dupa fiecare copie a instantei respective.Pentru asta, parsam cheia primara in functie de ce tip este ( tipul il luam din entitate) , astfel pastram cheia intr-un string pe care il comparam cu cel citit, daca coincid , am gasit o instanta in nodul respectiv si o eliminam.

GET:
Gasim entitatea la fel ca inainte si initializam o instanta cu prima din primul nod,aceasta oricum urmand sa fie schimbata.
Cand gasim o instanta ce are acelasi nume al entitatii , verificam in mod similar ca la DELETE daca coincide si cheia primara , in caz afirmativ , pastram instanta in inst , si variabila found va fi true.
In continuare , vom face scrierea doar daca am gasit instanta( found == true ) , vom afisa nodurile la care aparine instanta intr-un mod similar , cu o alta parcurgere. Apoi afisam entitatea si valorile atributelor instantei noastre in formatul cerut.

UPDATE:
Procedam la fel ca inainte , vom citipana la sfarsit de rand cate un atribut de inlocuit si inlocuitorul acestuia.Gasim instanta dupa cheia primara in mod similar , apoi parcurgem lista cu valorile atributelor instantei,si folosind procedeul anterior gasim tipul atributului , si trecem continutul acestuia intr-un string .Cand gasim atributul , setam noua valoare in functie de tipul acestuia.
Apoi clonam instanta noua , o eliminam si o adaugam la inceput , in locul in care ea trebuie sa  fie.

SNAPSHOTDB:
Parcurgem toate  nodurile , afisandu-le , si toate instantele nodurilor afisand in formatul cerut.

CLEANUP:
Parcurgem nodurile si instantele ,verificam daca timestamp ul instantei este mai mic , daca da scoatem elementul , si pentru a nu sari peste un posibil element eliminat , decrementam dupa eliminare contorul.