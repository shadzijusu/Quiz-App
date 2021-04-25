package ba.etf.rma21.projekat.view.fragmenti


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.staticdata.pitanja
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import ba.etf.rma21.projekat.viewmodel.ViewModelFactory
import com.google.android.material.navigation.NavigationView


class FragmentPitanje() : Fragment() {
    private lateinit var odgovoriLista: ListView
    private lateinit var tekstPitanja: TextView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var pitanje: Pitanje
    private  var pitanjeKvizListViewModel = PitanjeKvizListViewModel()

    private var pozicija = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var odabraniOdgovor = pitanjeKvizListViewModel.dajOdgovor(pitanje)
        if (odabraniOdgovor != null) {
            pozicija = odabraniOdgovor
        }
    }

    override fun onResume() {
        super.onResume()
        if (pitanjeKvizListViewModel.getAll().size > 0) {
            var odgovori = pitanjeKvizListViewModel.getAll().values.toMutableList()
            var questions = pitanjeKvizListViewModel.getAll().keys.toMutableList()
            for (i in 0 until questions.size) {
                    if(questions[i].tekst == pitanje.tekst) {
                        pozicija = odgovori[i]
                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                odgovoriLista.performItemClick(
                                    odgovoriLista.findViewWithTag(odgovoriLista.adapter.getItem(pozicija)),
                                    pozicija,
                                    odgovoriLista.adapter.getItemId(pozicija)
                                )
                            }, 10)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        navigacijaPitanja =
            requireParentFragment().requireView().findViewById(R.id.navigacijaPitanja)
        var bundle = this.arguments
        var poruka = bundle?.getString("data")
        tekstPitanja.text = pitanje.tekst

        val listaVrijednosti = pitanje.opcije
        val layoutID = android.R.layout.simple_list_item_1
        var adapter = view.context?.let { ArrayAdapter<String>(it, layoutID, listaVrijednosti) }
        odgovoriLista.adapter = adapter
        adapter?.notifyDataSetChanged()


        odgovoriLista.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (pitanjeKvizListViewModel.dajOdgovor(pitanje) == null)
                    pitanjeKvizListViewModel.addAnswer(pitanje, position)
                var menuItem = poruka?.toInt()?.let { navigacijaPitanja.menu.getItem(it) }
                var s: SpannableString = SpannableString("")
                if (position.equals(pitanje.tacan)) {
                    parent.getChildAt(position)
                        .setBackgroundColor(resources.getColor(R.color.tacno))
                    s = SpannableString(menuItem?.title.toString() + " +")
                    s.setSpan(ForegroundColorSpan(Color.GREEN), 0, s.length, 0)

                } else if (!position.equals(pitanje.tacan)) {
                    parent.getChildAt(position)
                        .setBackgroundColor(resources.getColor(R.color.netacno))
                    parent.getChildAt(pitanje.tacan)
                        .setBackgroundColor(resources.getColor(R.color.tacno))
                    s = SpannableString(menuItem?.title.toString() + " -")
                    s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
                }
                odgovoriLista.isEnabled = false
                menuItem?.title = s
            }
        return view
    }

    companion object {
        fun newInstance(): FragmentPitanje =
            FragmentPitanje()
    }

    constructor(pitanje: Pitanje) : this() {
        this.pitanje = pitanje
    }
}