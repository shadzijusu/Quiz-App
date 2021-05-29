package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.fragmenti.FragmentKvizovi
import ba.etf.rma21.projekat.view.fragmenti.FragmentPokusaj
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import java.text.SimpleDateFormat
import java.util.*


class ListaKvizovaAdapter(
    private var kvizovi: List<Kviz>) :
    RecyclerView.Adapter<ListaKvizovaAdapter.KvizViewHolder>() {
    private val pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private val kvizListViewModel = KvizListViewModel(null, null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount() = kvizovi.size


    override fun onBindViewHolder(holder: ListaKvizovaAdapter.KvizViewHolder, position: Int) {
        val current: Date = Calendar.getInstance().time
        var boja: String = "zelena"
//        holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta
        holder.nazivKviza.text = kvizovi[position].naziv
        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()
        val pattern = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        if (kvizovi[position].osvojeniBodovi == -1F) {
            holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumKraj)
            holder.stanjeKviza.setImageResource(R.drawable.crvena)
            holder.osvojeniBodovi.text = ""
        } else if (kvizListViewModel.getDone().contains(kvizovi[position]) && kvizovi[position].osvojeniBodovi != null) {
            holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumRada)
            holder.stanjeKviza.setImageResource(R.drawable.plava)
            holder.osvojeniBodovi.text = kvizovi[position].osvojeniBodovi.toString()
        } else if (kvizovi[position].datumPocetka != null && (kvizovi[position].datumPocetka.compareTo(current) > 0 && kvizovi[position].osvojeniBodovi == null)) {
            holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumPocetka)
            holder.stanjeKviza.setImageResource(R.drawable.zuta)
            holder.osvojeniBodovi.text = ""
        } else {
            val datum = kvizovi[position].datumPocetka
            if(datum != null) {
                datum.year = datum.year.minus(1900)
                datum.month = datum.month.minus(1)
                holder.datumKviza.text = simpleDateFormat.format(datum)
            }
            //promijenila datum
            holder.stanjeKviza.setImageResource(R.drawable.zelena)
            holder.osvojeniBodovi.text = ""
        }


        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                var kvizoviSPitanjima = pitanjeKvizListViewModel.getKvizoveSPitanjima()
                if (!kvizoviSPitanjima.contains(kvizovi[position].naziv))
                    return
                var nazivKviza = kvizovi[position].naziv
//                var nazivPredmeta = kvizovi[position].nazivPredmeta
                var activity: AppCompatActivity = view?.context as AppCompatActivity
               var pokusajFragment =
                    FragmentPokusaj(
                        pitanjeKvizListViewModel.getPitanja(nazivKviza, "nazivPredmeta")
                    )
                var bundle = Bundle()
                bundle.putString("naziv", kvizovi[position].naziv)
                pokusajFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, pokusajFragment).commit()
            }
        })
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
    }
}


