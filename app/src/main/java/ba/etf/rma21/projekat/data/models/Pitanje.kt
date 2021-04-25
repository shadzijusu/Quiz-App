package ba.etf.rma21.projekat.data.models

import java.io.Serializable

class Pitanje(var naziv : String,
                var tekst : String,
                var opcije : List<String>,
                var tacan : Int) : Serializable