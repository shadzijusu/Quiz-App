package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

//
//val naziv: String, val nazivPredmeta: String, val datumPocetka: Date, val datumKraj: Date,
//val datumRada: Date, val trajanje: Int, val nazivGrupe: String, val osvojeniBodovi: Float
fun myKvizes(): List<Kviz> {
    return listOf(
        //Uradjen, prosao
        Kviz(
            "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(2021, 3, 29), 2,
            "PON11:30", 2F
        ),
        //Aktivan, jos nije uradjen
        Kviz("Test", "OOAD", Date(2021, 3, 15),
            Date(2021, 4, 30), Date(0, 0,  0),
            6, "SRI15", null
        ),
        //Tek treba da bude
        Kviz(
            "Kviz vježbe 1-5", "ORM", Date(2021, 4, 22),
            Date(2021, 4, 30), Date(0, 0, 0), 5,
            "GrupaA", 5F
        ),
        //Uradjen, prosao
        Kviz(
            "Kviz DKA i NKA", "AFJ", Date(2021, 3, 22),
            Date(2021, 3, 30), Date(2021, 1, 4), 4,
            "Prva", 3F
        ),
        //Tek treba da bude
        Kviz(
            "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
            Date(2021, 4, 18), Date(0, 0, 0), 2,
            "PON11:30", 2F
        ),
        //Prosao neuradjen
        Kviz(
            "Kviz1", "OOAD", Date(2021, 3, 12),
            Date(2021, 3, 15), Date(0, 0, 0), 4,
            "SRI15", null
        ))
}
//add more.. svi kvizovi u sistemu
fun allKvizes() : List<Kviz> {
    return listOf(
        Kviz(
            "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(2021, 3, 29), 2,
            "PON11:30", 2F
        ),
        Kviz("Test", "OOAD", Date(2021, 3, 15),
            Date(2021, 4, 30), Date(0, 0,  0),
            6, "SRI15", null
        ),
        Kviz(
            "Kviz vježbe 1-5", "ORM", Date(2021, 4, 22),
            Date(2021, 4, 30), Date(0, 0, 0), 5,
            "GrupaA", 5F
        ),
        Kviz(
            "Kviz DKA i NKA", "AFJ", Date(2021, 3, 22),
            Date(2021, 3, 30), Date(2021, 1, 4), 4,
            "Prva", 3F
        ),
        Kviz(
            "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
            Date(2021, 4, 18), Date(0, 0, 0), 2,
            "PON11:30", 2F
        ),
        Kviz(
            "Kviz1", "RA", Date(2020, 4, 12),
            Date(2020, 4, 18), Date(0, 0, 0), 2,
            "Grupa1", null
        ))
}
fun done() : List<Kviz> {
    return listOf (
        Kviz(
            "Kviz vježbe 2 i 3", "RMA", Date(2021, 3, 29),
            Date(2021, 4, 2), Date(2021, 3, 29), 2,
            "PON11:30", 2F
        ),
        Kviz(
            "Kviz DKA i NKA", "AFJ", Date(2021, 3, 22),
            Date(2021, 3, 30), Date(2021, 1, 4), 4,
            "Prva", 3F
        ))
}
fun future() : List<Kviz> {
    return listOf(
        Kviz(
            "Kviz vježbe 1-5", "ORM", Date(2021, 4, 22),
            Date(2021, 4, 30), Date(0, 0, 0), 5,
            "GrupaA", 5F
        ),
        Kviz(
            "Kviz vježbe 4 i 5", "RMA", Date(2021, 4, 12),
            Date(2021, 4, 18), Date(0, 0, 0), 2,
            "PON11:30", 2F
        ))
}
fun notTaken() : List<Kviz> {
    return listOf(
        Kviz(
            "Kviz1", "OOAD", Date(2021, 3, 12),
            Date(2021, 3, 15), Date(0, 0, 0), 4,
            "SRI15", null
        ))
}