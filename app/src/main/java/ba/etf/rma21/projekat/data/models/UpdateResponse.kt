package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

class UpdateResponse (
    @SerializedName("changed") var changed: Boolean
)