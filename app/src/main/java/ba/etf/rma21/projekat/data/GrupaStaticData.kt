package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Grupa

fun groupsByPredmet(nazivPredmeta : String) : List<Grupa>{
    when(nazivPredmeta) {
        "RA" -> return listOf(
            Grupa("Grupa1", "RA"),
            Grupa("Grupa2", "RA")
        )
        "OOAD" -> return listOf(
            Grupa("PON9", "OOAD"),
            Grupa("PON13", "OOAD"),
            Grupa("UTO12", "OOAD"),
            Grupa("SRI15", "OOAD"),
            Grupa("PET10", "OOAD")
        )
        "ORM" -> return listOf(
            Grupa("GrupaA", "ORM"),
            Grupa("GrupaB", "ORM")
        )
        "RMA" -> return listOf(
            Grupa("PON9", "RMA"),
            Grupa("PON1130", "RMA"),
            Grupa("UTO9", "RMA"),
            Grupa("SRI13", "RMA"),
            Grupa("CET14", "RMA")
        )
        "AFJ" -> return listOf(
            Grupa("Prva", "AFJ"),
            Grupa("Druga", "AFJ")
        )
        //add more ..
        else -> return listOf(
            Grupa("", "")
        )
    }
}