package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Pitanje

fun pitanja(nazivKviza : String, nazivPredmeta : String) : List<Pitanje> {
    when(nazivKviza) {
        "Kviz 1 - vježbe 2 i 3" -> return listOf(
            Pitanje("Pitanje 1", "Ako želimo da BroadcastReceiver osluškuje obavijesti čak i kada aplikacija nije pokrenuta, tada taj BroadcastReceiver registrujemo u ...",
            listOf("manifestu", "u glavnoj klasi aktivnosti aplikacije", "nemoguće"), 0)
        )
        else -> return listOf()
    }
}