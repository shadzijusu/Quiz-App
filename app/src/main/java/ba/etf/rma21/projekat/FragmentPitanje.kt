package ba.etf.rma21.projekat


import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.android.material.navigation.NavigationView


class FragmentPitanje() : Fragment() {
    private lateinit var odgovoriLista: ListView
    private lateinit var tekstPitanja: TextView
    private lateinit var navigacijaPitanja: NavigationView

    private lateinit var pitanje: Pitanje
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pitanje, container, false)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        navigacijaPitanja = requireParentFragment().requireView().findViewById(R.id.navigacijaPitanja)


        var bundle = this.arguments
        var poruka = bundle?.getString("data")
        tekstPitanja.text = pitanje.tekst
        val listaVrijednosti = pitanje.opcije
        val layoutID = android.R.layout.simple_list_item_1
        var adapter = ArrayAdapter<String>(view.context, layoutID, listaVrijednosti)
        odgovoriLista.adapter = adapter
        adapter.notifyDataSetChanged()
        //change text color instead of background
        odgovoriLista.setOnItemClickListener { parent, view, position, id ->
            var menuItem = poruka?.toInt()?.let { navigacijaPitanja.menu.getItem(it) }
            val s = SpannableString(menuItem?.title.toString())
            if (position.equals(pitanje.tacan)) {
                parent.getChildAt(position).setBackgroundColor(resources.getColor(R.color.tacno))
                s.setSpan(ForegroundColorSpan(Color.GREEN), 0, s.length, 0)

            } else if (!position.equals(pitanje.tacan)) {
                parent.getChildAt(position).setBackgroundColor(resources.getColor(R.color.netacno))
                parent.getChildAt(pitanje.tacan)
                    .setBackgroundColor(resources.getColor(R.color.tacno))
                s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
            }
            menuItem?.title = s
        }
        return view
    }
    companion object {
        fun newInstance(): FragmentPitanje = FragmentPitanje()
    }

    constructor(pitanje: Pitanje) : this() {
        this.pitanje = pitanje
    }

}