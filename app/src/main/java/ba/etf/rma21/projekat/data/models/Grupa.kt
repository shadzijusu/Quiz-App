package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Grupa(
    @SerializedName("id") var id: Int,
    @SerializedName("naziv") var naziv: String,
    @SerializedName("PredmetId") var PredmetId : Int
)