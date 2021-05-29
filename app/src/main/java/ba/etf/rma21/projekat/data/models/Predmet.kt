package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Predmet(
    @SerializedName("id") var id: Int,
    @SerializedName("naziv") var naziv: String,
    @SerializedName("godina") var godina: Int)