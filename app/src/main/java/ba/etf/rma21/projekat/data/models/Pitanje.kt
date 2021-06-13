package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Pitanje")
data class Pitanje(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") var tekstPitanja: String,
    @ColumnInfo(name = "opcije") @SerializedName("opcije") var opcije: String,
    @ColumnInfo(name = "tacan")@SerializedName("tacan") var tacan: Int,
    @ColumnInfo(name = "kvizId") var kvizId : Int
)