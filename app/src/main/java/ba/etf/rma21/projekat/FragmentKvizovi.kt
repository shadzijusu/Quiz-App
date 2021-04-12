package ba.etf.rma21.projekat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.ListaKvizovaAdapter
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentKvizovi : Fragment() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var listaKvizovaAdapter: ListaKvizovaAdapter
    private lateinit var filterKvizova: Spinner
    private var kvizListViewModel = KvizListViewModel()
    private lateinit var infoButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_kvizovi, container, false)
        listaKvizova = view.findViewById(R.id.listaKvizova)
        filterKvizova = view.findViewById(R.id.filterKvizova)
//        infoButton = view.findViewById(R.id.infoButton)
//        infoButton.setOnClickListener {
//            val popupWindow = PopupWindow()
//            val view = layoutInflater.inflate(R.layout.activity_info, null)
//            popupWindow.contentView = view
//            // Closes the popup window when touch outside. This method was written informatively in Google's docs.
//            popupWindow.isOutsideTouchable = true
//
//            // Set focus true to prevent a touch event to go to a below view (main layout), which works like a dialog with 'cancel' property => Try it! And you will know what I mean.
//            popupWindow.isFocusable = true
//
//            // Removes default background.
//            popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            popupWindow.showAsDropDown(infoButton, 15 * 8, -65 * 8)
//        }
        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.filteri,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                filterKvizova.adapter = adapter
            }
        }
        filterKvizova.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //No implementation
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                when (parent.getItemAtPosition(pos)) {
                    "Svi kvizovi" -> {
                        listaKvizovaAdapter = ListaKvizovaAdapter(
                            kvizListViewModel.getAll().sortedBy { it.datumPocetka }) {otvoriFragmentPokusaj()}
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getAll())
                    }
                    "Urađeni kvizovi" -> {
                        listaKvizovaAdapter = ListaKvizovaAdapter(
                            kvizListViewModel.getDone().sortedBy { it.datumPocetka }) {otvoriFragmentPokusaj()}
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getDone())
                    }
                    "Budući kvizovi" -> {
                        listaKvizovaAdapter = ListaKvizovaAdapter(
                            kvizListViewModel.getFuture().sortedBy { it.datumPocetka }) {otvoriFragmentPokusaj()}
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getFuture())
                    }
                    "Prošli kvizovi" -> {
                        listaKvizovaAdapter = ListaKvizovaAdapter(
                            kvizListViewModel.getNotTaken().sortedBy { it.datumPocetka }) {otvoriFragmentPokusaj()}
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getNotTaken())
                    }
                    else -> {
                        listaKvizovaAdapter = ListaKvizovaAdapter(
                            kvizListViewModel.getMyKvizes().sortedBy { it.datumPocetka }) {otvoriFragmentPokusaj()}
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getMyKvizes())
                    }
                }
            }
        })
        listaKvizova.layoutManager = GridLayoutManager(activity, 2)
        listaKvizova.addItemDecoration(DefaultItemDecorator(25, 5))
        listaKvizovaAdapter =
            ListaKvizovaAdapter(kvizListViewModel.getMyKvizes()) {otvoriFragmentPokusaj()}
        listaKvizova.adapter = listaKvizovaAdapter
        return view
    }

    private fun otvoriFragmentPokusaj() {
        var pokusajFragment = FragmentPokusaj(
            listOf(Pitanje("Pitanje 1", "Ako želimo da BroadcastReceiver osluškuje obavijesti čak i kada aplikacija nije pokrenuta, tada taj BroadcastReceiver registrujemo u ...",
            listOf("manifestu", "u glavnoj klasi aktivnosti aplikacije", "nemoguće"), 0)
        ))
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, pokusajFragment)
            ?.commit()
    }

    companion object {
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }
}