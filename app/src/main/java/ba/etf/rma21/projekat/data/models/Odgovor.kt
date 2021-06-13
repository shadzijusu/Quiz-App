package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Odgovor")
data class Odgovor(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") var odgovoreno: Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") var PitanjeId: Int,
    @ColumnInfo(name = "KvizId") @SerializedName("KvizId") var KvizId: Int,
    @ColumnInfo(name = "KvizTakenId") @SerializedName("KvizTakenId") var KvizTakenId: Int,
    @ColumnInfo(name = "bodovi") @SerializedName("bodovi") var bodovi : Float
    )

