package ba.etf.rma21.projekat.data.staticdata

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*
var gotovi: MutableList<Kviz> = mutableListOf(
//    Kviz(
//        "Kviz - OOP u Javi", "RPR", Date(121, 1, 8),
//        Date(121, 1, 15), Date(121, 1, 9), 4,
//        "Grupa4", 3F
//    )
)


var mojiKvizovi = mutableListOf<Kviz>()
//    Kviz(
//        "Kviz 1 - vježbe 2 i 3", "RMA", Date(121, 2, 29),
//        Date(121, 6, 5), Date(0, 0, 0), 2,
//        "PON11:30", null
//    ),
//    Kviz(
//        "Test", "OOAD", Date(121, 3, 3),
//        Date(121, 8, 30), Date(0, 0, 0),
//        6, "SRI15", null
//    ),
//    Kviz(
//        "Kviz - OOP u Javi", "RPR", Date(121, 1, 8),
//        Date(121, 1, 15), Date(121, 1, 9), 4,
//        "Grupa4", 3F
//    ),
//    Kviz(
//        "Kviz 2 - vježbe 4 i 5", "RMA", Date(121, 2, 25),
//        Date(121, 7, 30), Date(0, 0, 0), 2,
//        "PON11:30", null
//    ),
//    Kviz(
//        "Kviz1", "OOAD", Date(121, 1, 12),
//        Date(121, 2, 2), Date(0, 0, 0), 4,
//        "SRI15", -1F
//    ),
//    Kviz(
//        "Test2", "OOAD", Date(121, 6, 12),
//        Date(121, 9, 2), Date(0, 0, 0), 5,
//        "SRI15", null
//    )
//)//
var sviKvizovi = mutableListOf<Kviz>()
//    "Kviz vježbe 1", "MPVI", Date(120, 3, 29),
//    Date(120, 4, 2), Date(0, 0, 0), 2,
//    "m2", -1F
//),
//    Kviz(
//        "Kviz vježbe 2", "MPVI", Date(121, 4, 3),
//        Date(121, 4, 10), Date(0, 0, 0), 2,
//        "m1", null
//    ),
//    Kviz(
//        "Red i stek", "ASP", Date(121, 3, 2),
//        Date(121, 3, 30), Date(0, 0, 0),
//        6, "Grupa2A", null
//    ),
//    Kviz(
//        "Mape", "ASP", Date(121, 2, 15),
//        Date(121, 4, 30), Date(0, 0, 0),
//        6, "Grupa1A", null
//    ),
//    Kviz(
//        "Kviz 5", "VVS", Date(121, 4, 22),
//        Date(121, 4, 30), Date(0, 0, 0), 5,
//        "A1", null
//    ),
//    Kviz(
//        "CodeTuning", "VVS", Date(121, 5, 22),
//        Date(121, 5, 30), Date(0, 0, 0), 5,
//        "A2", null
//    ),
//    Kviz(
//        "Kviz1", "OBP", Date(121, 3, 25),
//        Date(121, 4, 5), Date(0, 0, 0), 4,
//        "Prva", null
//    ),
//    Kviz(
//        "Kviz2", "OBP", Date(121, 5, 25),
//        Date(121, 6, 5), Date(0, 0, 0), 4,
//        "Druga", null
//    ),
//    Kviz(
//        "Priprema za ispit", "NOS", Date(120, 2, 12),
//        Date(120, 2, 18), Date(0, 0, 0), 15,
//        "EEF", -1F
//    ))
//svi kvizovi za predmete i grupe gdje je korisnik upisan
fun myKvizes(): List<Kviz> {
    return mojiKvizovi
}

fun dajKviz(nazivKviza: String): Kviz {
    var kviz =
//        "Kviz 1 - vježbe 2 i 3" ->   Kviz(
//            "Kviz 1 - vježbe 2 i 3", "RMA", Date(121, 2, 29),
//            Date(121, 6, 5), Date(0, 0, 0), 2,
//            "PON11:30", null
//        )
//        "Test" -> Kviz(
//            "Test", "OOAD", Date(121, 3, 3),
//            Date(121, 8, 30), Date(0, 0, 0),
//            6, "SRI15", null
//        )
//        "Kviz 2 - vježbe 4 i 5" -> Kviz(
//            "Kviz 2 - vježbe 4 i 5", "RMA", Date(121, 2, 25),
//            Date(121, 7, 30), Date(0, 0, 0), 2,
//            "PON11:30", null
//        )
      Kviz(1, "", "", "",  0, "", "", 0f)

    return kviz
}

//svi kvizovi u sistemu
fun allKvizes(): List<Kviz> {
    var svi = mutableListOf<Kviz>()
    svi.addAll(mojiKvizovi)
    svi.addAll(sviKvizovi)
    return svi
}

fun done(): List<Kviz> {
    return gotovi
}

fun future(): List<Kviz> {
    return listOf(
//        Kviz(
//            "Test2", "OOAD", Date(121, 6, 12),
//            Date(121, 9, 2), Date(0, 0, 0), 5,
//            "SRI15", null
//        )
    )
}

fun notTaken(): List<Kviz> {
    return listOf(
//        Kviz(
//            "Kviz1", "OOAD", Date(121, 1, 12),
//            Date(121, 2, 2), Date(0, 0, 0), 4,
//            "SRI15", -1F
//        )
    )
}

fun dodajMoj(noviKviz: Kviz) {
    val iterator = mojiKvizovi.iterator()
    while (iterator.hasNext()) {
        val item = iterator.next()
        if (item.naziv.equals(noviKviz.naziv)) {
            iterator.remove()
        }
    }
    mojiKvizovi.add(noviKviz)
    gotovi.add(noviKviz)
}