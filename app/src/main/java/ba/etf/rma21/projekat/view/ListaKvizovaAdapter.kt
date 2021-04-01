package ba.etf.rma21.projekat.view

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class ListaKvizovaAdapter(private var kvizovi: List<Kviz>) :
    RecyclerView.Adapter<ListaKvizovaAdapter.KvizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount() = kvizovi.size


    override fun onBindViewHolder(holder: ListaKvizovaAdapter.KvizViewHolder, position: Int) {
        val current : Date = Date(2021, 4, 1)

        holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta
        holder.nazivKviza.text = kvizovi[position].naziv

        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()
        holder.osvojeniBodovi.text = kvizovi[position].osvojeniBodovi.toString()
        if(kvizovi[position].datumRada?.day != 0) {
            holder.datumKviza.text = kvizovi[position].datumRada?.day.toString() + "."+ kvizovi[position].datumRada?.month.toString() + "." + kvizovi[position].datumRada?.year.toString()
            holder.stanjeKviza.setImageResource(R.drawable.plava)
        }//ako je poslije danasnjeg datuma to je future i ide zuta
        else if(kvizovi[position].datumKraj.compareTo(current) > 0) {
            holder.datumKviza.text = kvizovi[position].datumPocetka?.day.toString() + "."+ kvizovi[position].datumPocetka.month.toString() + "." + kvizovi[position].datumPocetka.year.toString()
            holder.stanjeKviza.setImageResource(R.drawable.zuta)
            }
        //crvena - before today i bodovi nula
        else if(kvizovi[position].datumKraj.compareTo(current) < 0 && kvizovi[position].osvojeniBodovi == 0F) {
            holder.datumKviza.text = kvizovi[position].datumKraj.day.toString() + "."+ kvizovi[position].datumKraj.month.toString() + "." + kvizovi[position].datumKraj.year.toString()
            holder.stanjeKviza.setImageResource(R.drawable.crvena)
        }
        else {
            holder.datumKviza.text = kvizovi[position].datumKraj.day.toString() + "."+ kvizovi[position].datumKraj.month.toString() + "." + kvizovi[position].datumKraj.year.toString()
            holder.stanjeKviza.setImageResource(R.drawable.zelena)
        }
    }


fun updateKvizove(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        notifyDataSetChanged()
    }

    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivPredmeta: TextView = itemView.findViewById(R.id.nazivPredmeta)
        val nazivKviza: TextView = itemView.findViewById(R.id.nazivKviza)
        val datumKviza: TextView = itemView.findViewById(R.id.datumKviza)
        val trajanjeKviza: TextView = itemView.findViewById(R.id.trajanjeKviza)
        val osvojeniBodovi: TextView = itemView.findViewById(R.id.osvojeniBodovi)
        val stanjeKviza: ImageView = itemView.findViewById(R.id.stanjeKviza)
        //val upisDugme : FloatingActionButton = itemView.findViewById(R.id.upisDugme)
    }
}


