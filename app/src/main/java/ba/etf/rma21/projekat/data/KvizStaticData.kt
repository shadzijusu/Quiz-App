package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

var mojiKvizovi = mutableListOf(
    Kviz(
        "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
        Date(2021, 4, 2), Date(2021, 3, 29), 2,
        "PON11:30", 2F
    ),
    Kviz(
        "Test", "OOAD", Date(2021, 3, 15),
        Date(2021, 4, 30), Date(0, 0, 0),
        6, "SRI15", null
    ),
    Kviz(
        "Kviz - OOP u Javi", "RPR", Date(2021, 1, 8),
        Date(2021, 1, 15), Date(2021, 1, 9), 4,
        "Grupa4", 3F
    ),
    Kviz(
        "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
        Date(2021, 4, 18), Date(0, 0, 0), 2,
        "PON11:30", null
    ),
    Kviz(
        "Kviz1", "OOAD", Date(2021, 3, 12),
        Date(2021, 3, 15), Date(0, 0, 0), 4,
        "SRI15", null
    )
)

//svi kvizovi za predmete i grupe gdje je korisnik upisan
fun myKvizes(): List<Kviz> {
    return mojiKvizovi

}

//svi kvizovi u sistemu
fun allKvizes(): List<Kviz> {
    return listOf(
        Kviz(
            "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(2021, 3, 29), 2,
            "PON11:30", 2F
        ),
        Kviz(
            "Test", "OOAD", Date(2021, 3, 15),
            Date(2021, 4, 30), Date(0, 0, 0),
            6, "SRI15", null
        ),
        Kviz(
            "Kviz - OOP u Javi", "RPR", Date(2020, 12, 22),
            Date(2020, 12, 30), Date(2020, 12, 23), 4,
            "Grupa4", 3F
        ),
        Kviz(
            "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
            Date(2021, 4, 18), Date(0, 0, 0), 2,
            "PON11:30", null
        ),
        Kviz(
            "Kviz1", "OOAD", Date(2021, 3, 12),
            Date(2021, 3, 15), Date(0, 0, 0), 4,
            "SRI15", null
        ),
        Kviz(
            "Kviz vježbe 1", "MPVI", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(0, 0, 0), 2,
            "m2", null
        ),
        Kviz(
            "Kviz vježbe 2", "MPVI", Date(2021, 4, 3),
            Date(2021, 4, 10), Date(0, 0, 0), 2,
            "m1", null
        ),
        Kviz(
            "Red i stek", "ASP", Date(2021, 3, 15),
            Date(2021, 4, 30), Date(0, 0, 0),
            6, "Grupa2A", null
        ),
        Kviz(
            "Mape", "ASP", Date(2021, 2, 15),
            Date(2021, 3, 30), Date(0, 0, 0),
            6, "Grupa1A", null
        ),
        Kviz(
            "Kviz 5", "VVS", Date(2021, 4, 22),
            Date(2021, 4, 30), Date(0, 0, 0), 5,
            "A1", null
        ),
        Kviz(
            "CodeTuning", "VVS", Date(2021, 5, 22),
            Date(2021, 5, 30), Date(0, 0, 0), 5,
            "A2", null
        ),
        Kviz(
            "Kviz1", "OBP", Date(2021, 4, 25),
            Date(2021, 5, 5), Date(0, 0, 0), 4,
            "Prva", null
        ),
        Kviz(
            "Kviz2", "OBP", Date(2021, 5, 25),
            Date(2021, 6, 5), Date(0, 0, 0), 4,
            "Druga", null
        ),
        Kviz(
            "Priprema za ispit", "NOS", Date(2020, 4, 12),
            Date(2020, 4, 18), Date(0, 0, 0), 15,
            "EEF", null
        ),
        Kviz(
            "Kviz", "NOS", Date(2020, 5, 12),
            Date(2020, 5, 18), Date(0, 0, 0), 15,
            "AAA", null
        ),

        Kviz(
            "Kviz vježbe 1-5", "WT", Date(2021, 4, 22),
            Date(2021, 4, 30), Date(0, 0, 0), 5,
            "Prva", null
        ),
        Kviz(
            "Kviz vježba 6", "WT", Date(2021, 5, 22),
            Date(2021, 5, 30), Date(0, 0, 0), 5,
            "Treca", null
        )
    )
}

fun done(): List<Kviz> {
    return listOf(
        Kviz(
            "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(2021, 3, 29), 2,
            "PON11:30", 2F
        ),
        Kviz(
            "Kviz - OOP u Javi", "RPR", Date(2020, 12, 22),
            Date(2020, 12, 30), Date(2020, 12, 23), 4,
            "Grupa4", 3F
        )
    )
}

fun future(): List<Kviz> {
    return listOf(
        Kviz(
            "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
            Date(2021, 4, 18), Date(0, 0, 0), 2,
            "PON11:30", null
        )
    )
}

fun notTaken(): List<Kviz> {
    return listOf(
        Kviz(
            "Kviz1", "OOAD", Date(2021, 3, 12),
            Date(2021, 3, 15), Date(0, 0, 0), 4,
            "SRI15", null
        )
    )
}

fun dodajMoj(kviz: Kviz) {
    mojiKvizovi.add(kviz)
}