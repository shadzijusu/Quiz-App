package ba.etf.rma21.projekat.data.staticdata

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
//Pitanja za kvizove iz RMA i OOAD
fun svaPitanjaSNazivomKviza() : List<PitanjeKviz> {
    return listOf(
        PitanjeKviz(
            Pitanje(
                "Pitanje 1",
                "Ako želimo da BroadcastReceiver osluškuje obavijesti čak i kada aplikacija nije pokrenuta, tada taj BroadcastReceiver registrujemo u ...",
                listOf("manifestu", "u glavnoj klasi aktivnosti aplikacije", "nemoguće"),
                0
            ), "Kviz 1 - vježbe 2 i 3"
        ),
        PitanjeKviz( Pitanje(
            "Pitanje 1",
            "Fragment je",
            listOf(
                "modularni dio aktivnosti",
                "obavezni dio aktivnosti",
                "komponenta Android aplikacija"
            ),
            0
        ), "Kviz 2 - vježbe 4 i 5"),
        PitanjeKviz( Pitanje(
            "Pitanje 1",
            "Dijagram klasa i dijagram objekata spadaju UML skupini dijagrama",
            listOf(
                "ponašanja",
                "strukture",
                "ne pripadaju istoj skupini"
            ),
            0
        ), "Test")
    )
}

fun pitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
    var pitanjaKviza = listOf<Pitanje>()
    pitanjaKviza =
            when (nazivKviza) {
            "Kviz 1 - vježbe 2 i 3" -> listOf(
                Pitanje(
                    "Pitanje 1",
                    "Ako želimo da BroadcastReceiver osluškuje obavijesti čak i kada aplikacija nije pokrenuta, tada taj BroadcastReceiver registrujemo u ...",
                    listOf("manifestu", "u glavnoj klasi aktivnosti aplikacije", "nemoguće"),
                    0
                ),
                Pitanje(
                    "Pitanje 2",
                    "Metoda kojom provjeravamo da li postoji aplikacija koja može da odgovori na implicitni intent je",
                    listOf("resolveAction()", "resolveActivity()", "checkAction()"),
                    1
                ),
                Pitanje(
                    "Pitanje 3",
                    "Koje metode klasa koja implementira TypeSafe matcher treba imat",
                    listOf(
                        "assert i assertThat",
                        "describeTo i matchesSafely",
                        "withText i withId"
                    ),
                    1
                ),
                Pitanje(
                    "Pitanje 4",
                    "Ako želimo da u kodu aplikacije prikažemo labelu prevedenu u /res/values folderu čiji name je title koristimo",
                    listOf("R.values.name", "R.title", "R.string.title"),
                    2
                ),
                Pitanje(
                    "Pitanje 5",
                    "Metoda onCreate() se poziva",
                    listOf(
                        "kada se prvi put kreira aktivnost",
                        "aktivnost treba postati vidljiva korisniku",
                        "kada korisnik počinje interakciju s aplikacijom"
                    ),
                    0
                )
            )
            "Kviz 2 - vježbe 4 i 5" ->  listOf(
                Pitanje(
                    "Pitanje 1",
                    "Fragment je",
                    listOf(
                        "modularni dio aktivnosti",
                        "obavezni dio aktivnosti",
                        "komponenta Android aplikacija"
                    ),
                    0
                ),
                Pitanje(
                    "Pitanje 2",
                    "Ako želimo da se fragment transakcija poništi kada korisnik pritisne back dugme tada koristimo metodu: ",
                    listOf(
                        "FragmentTransaction.commit()",
                        "FragmentManager.addToBackStack()",
                        "FragmentTransaction.addToBackStack()"
                    ),
                    2
                ),
                Pitanje(
                    "Pitanje 3",
                    "Koji od ova tri načina ubacivanja fragmenta u aktivnost nije istinit",
                    listOf("Dinamički", "Ekskluzivni", "Statički"),
                    1
                ),
                Pitanje(
                    "Pitanje 4",
                    "Toggle je zamjena za",
                    listOf("checkbox", "floating action button", "radio button"),
                    0
                ),
                Pitanje(
                    "Pitanje 5",
                    "Koji Home Screen pattern koristi Google Chat",
                    listOf("Carousel", "Lista i detalj", "Map"),
                    1
                )
            )
            "Test" -> listOf(
                Pitanje(
                    "Pitanje 1",
                    "Dijagram klasa i dijagram objekata spadaju UML skupini dijagrama",
                    listOf(
                        "ponašanja",
                        "strukture",
                        "ne pripadaju istoj skupini"
                    ),
                    0
                ),
                Pitanje(
                    "Pitanje 2",
                    "Koji od sljedećih koncepata su povezani sa dijagramom aktivnosti",
                    listOf(
                        "include relacija",
                        "generalizacija",
                        "particije"
                    ),
                    2
                ),
                Pitanje(
                    "Pitanje 3",
                    "Za dijagram objekata je istinito",
                    listOf("pripada procesnom pogledu na sistem", "link je veza između objekata", "objekat ne može biti anoniman"),
                    1
                ),
                Pitanje(
                    "Pitanje 4",
                    "Dijagram slučajeva upotrebe nam govori",
                    listOf("šta sistem radi", "kako sistem radi", "ništa od navedenog"),
                    0
                ),
                Pitanje(
                    "Pitanje 5",
                    "Koju vezu između Uređivanje profila i Promjena šifre ćemo upotrijebiti za modeliranje sljedeće situacije: " +
                            "Student uređuje svoj korisnički profil. Pri tome može i promijeniti lozinku ako želi.\n",
                    listOf("asocijaciju", "extend", "include"),
                    1
                )
            )
                else -> emptyList()
            }
    return pitanjaKviza
}