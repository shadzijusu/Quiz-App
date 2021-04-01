package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Predmet

fun predmetsByGodina(godina: Int): List<Predmet> {
    when (godina) {
        1 -> return listOf(
            Predmet("OE", 1),
            Predmet("IM1", 1),
            Predmet("IF1", 1),
            Predmet("LAG", 1),
            Predmet("UUP", 1),
            Predmet("IM2", 1),
            Predmet("VIS", 1),
            Predmet("OS", 1),
            Predmet("MLTI", 1),
            Predmet("TP", 1)
        )
        2 -> return listOf(
            Predmet("ASP", 2),
            Predmet("OBP", 2),
            Predmet("DM", 2),
            Predmet("SP", 2),
            Predmet("RPR", 2),
            Predmet("LD", 2),
            Predmet("RA", 2),
            Predmet("ORM", 2),
            Predmet("OOAD", 2),
            Predmet("RMA", 2),
            Predmet("IEK", 2),
            Predmet("RMA", 2)
        )
        3 -> return listOf(
            Predmet("WT", 3),
            Predmet("RG", 3),
            Predmet("OIS", 3),
            Predmet("OOI", 3),
            Predmet("PJP", 3),
            Predmet("VVS", 3),
            Predmet("VI", 3),
            Predmet("SI", 3),
            Predmet("OA", 3),
            Predmet("MK", 3),
            Predmet("DSU", 3),
            Predmet("ITSU", 3)
        )
        4 -> return listOf(
            Predmet("RV", 4),
            Predmet("NOS", 4),
            Predmet("NASP", 4),
            Predmet("MU", 4),
            Predmet("PRS", 4),
            Predmet("NWM", 4),
            Predmet("MS", 4)
        )
        5 -> return listOf(
            Predmet("RI", 5),
            Predmet("MPVI", 5),
            Predmet("NSI", 5),
            Predmet("TS", 5),
            Predmet("PI", 5),
            Predmet("SPO", 5),
            Predmet("RAB", 5)
        )
        else -> return listOf(
            Predmet("", 0)
        )
    }
}

fun all(): List<Predmet> {
    return listOf(
        Predmet("OE", 1),
        Predmet("IM1", 1),
        Predmet("IF1", 1),
        Predmet("LAG", 1),
        Predmet("UUP", 1),
        Predmet("IM2", 1),
        Predmet("VIS", 1),
        Predmet("OS", 1),
        Predmet("MLTI", 1),
        Predmet("TP", 1),
        Predmet("ASP", 2),
        Predmet("OBP", 2),
        Predmet("DM", 2),
        Predmet("SP", 2),
        Predmet("RPR", 2),
        Predmet("LD", 2),
        Predmet("RA", 2),
        Predmet("ORM", 2),
        Predmet("OOAD", 2),
        Predmet("RMA", 2),
        Predmet("IEK", 2),
        Predmet("RMA", 2),
        Predmet("WT", 3),
        Predmet("RG", 3),
        Predmet("OIS", 3),
        Predmet("OOI", 3),
        Predmet("PJP", 3),
        Predmet("VVS", 3),
        Predmet("VI", 3),
        Predmet("SI", 3),
        Predmet("OA", 3),
        Predmet("MK", 3),
        Predmet("DSU", 3),
        Predmet("ITSU", 3),
        Predmet("RV", 4),
        Predmet("NOS", 4),
        Predmet("NASP", 4),
        Predmet("MU", 4),
        Predmet("PRS", 4),
        Predmet("NWM", 4),
        Predmet("MS", 4),
        Predmet("RI", 5),
        Predmet("MPVI", 5),
        Predmet("NSI", 5),
        Predmet("TS", 5),
        Predmet("PI", 5),
        Predmet("SPO", 5),
        Predmet("RAB", 5)
    )
}

fun upisani(): List<Predmet> {
    return listOf(
        Predmet("RA", 2),
        Predmet("ORM", 2),
        Predmet("OOAD", 2),
        Predmet("RMA", 2),
        Predmet("IEK", 2),
        Predmet("RMA", 2)
    )
}