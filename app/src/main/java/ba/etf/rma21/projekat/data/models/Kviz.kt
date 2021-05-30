package ba.etf.rma21.projekat.data.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class Kviz(
    @SerializedName("id") var id: Int,
    @SerializedName("naziv") var naziv: String,
    @SerializedName("datumPocetka") var datumPocetka: Date,
    @SerializedName("datumKraj") var datumKraj: Date,
    @SerializedName("trajanje") var trajanje: Int
)