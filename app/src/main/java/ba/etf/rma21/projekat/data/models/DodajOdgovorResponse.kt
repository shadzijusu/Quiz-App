package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

class DodajOdgovorResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("odgovoreno") var odgovoreno: Int,
    @SerializedName("message") var message: String
)