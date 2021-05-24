package ba.etf.rma21.projekat.data.staticdata

import ba.etf.rma21.projekat.data.models.Predmet

var mojiPredmeti = mutableListOf<Predmet>()
//    Predmet("DM", 2),
//    Predmet("RPR", 2),
//    Predmet("OOAD", 2),
//    Predmet("RMA", 2)


fun predmetsByGodina(godina: Int): List<Predmet> {
    return listOf()
//    when (godina) {
////        1 -> return listOf(
//            Predmet("VIS", 1),
//            Predmet("OS", 1),
//            Predmet("MLTI", 1),
//            Predmet("TP", 1)
//        )
//        2 -> return listOf(
//            Predmet("ASP", 2),
//            Predmet("OBP", 2),
//            Predmet("DM", 2),
//            Predmet("RPR", 2),
//            Predmet("OOAD", 2),
//            Predmet("RMA", 2)
//        )
//        3 -> return listOf(
//            Predmet("WT", 3),
//            Predmet("OOI", 3),
//            Predmet("VVS", 3),
//            Predmet("VI", 3)
//        )
//        4 -> return listOf(
//            Predmet("RV", 4),
//            Predmet("NOS", 4),
//            Predmet("NASP", 4)
//        )
//        5 -> return listOf(
//            Predmet("TS", 5),
//            Predmet("MPVI", 5),
//            Predmet("NSI", 5)
//        )
//        else -> return listOf(
//            Predmet("", 0)
//        )

}
fun all(): List<Predmet> {
    return listOf()

}

fun upisani(): List<Predmet> = mojiPredmeti

fun upisi(predmet : Predmet) {
      mojiPredmeti.add(predmet)
}