package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Odgovor(
    @SerializedName("id") var id: Int,
    @SerializedName("odgovoreno") var odgovoreno: Int,
    @SerializedName("PitanjeId") var PitanjeId: Int)

