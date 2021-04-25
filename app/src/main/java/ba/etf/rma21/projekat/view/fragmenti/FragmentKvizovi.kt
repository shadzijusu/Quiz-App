package ba.etf.rma21.projekat.view.fragmenti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.DefaultItemDecorator
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.ListaKvizovaAdapter
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import ba.etf.rma21.projekat.viewmodel.ViewModelFactory

class FragmentKvizovi : Fragment() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var listaKvizovaAdapter: ListaKvizovaAdapter
    private lateinit var filterKvizova: Spinner
    private var kvizListViewModel = KvizListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_kvizovi, container, false)
        listaKvizova = view.findViewById(R.id.listaKvizova)
        filterKvizova = view.findViewById(R.id.filterKvizova)
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
                        listaKvizovaAdapter =
                            ListaKvizovaAdapter(
                                kvizListViewModel.getAll().sortedBy { it.datumPocetka })
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getAll())
                    }
                    "Urađeni kvizovi" -> {
                        listaKvizovaAdapter =
                            ListaKvizovaAdapter(
                                kvizListViewModel.getDone().sortedBy { it.datumPocetka })
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getDone())
                    }
                    "Budući kvizovi" -> {
                        listaKvizovaAdapter =
                            ListaKvizovaAdapter(
                                kvizListViewModel.getFuture().sortedBy { it.datumPocetka })
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getFuture())
                    }
                    "Prošli kvizovi" -> {
                        listaKvizovaAdapter =
                            ListaKvizovaAdapter(
                                kvizListViewModel.getNotTaken().sortedBy { it.datumPocetka })
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getNotTaken())
                    }
                    else -> {
                        listaKvizovaAdapter =
                            ListaKvizovaAdapter(
                                kvizListViewModel.getMyKvizes().sortedBy { it.datumPocetka })
                        listaKvizova.adapter = listaKvizovaAdapter
                        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getMyKvizes())
                    }
                }
            }
        })
        listaKvizova.layoutManager = GridLayoutManager(activity, 2)
        listaKvizova.addItemDecoration(
            DefaultItemDecorator(
                25,
                5
            )
        )
        listaKvizovaAdapter =
            ListaKvizovaAdapter(kvizListViewModel.getMyKvizes())
        listaKvizova.adapter = listaKvizovaAdapter
        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getMyKvizes())
        return view
    }


    companion object {
        fun newInstance(): FragmentKvizovi =
            FragmentKvizovi()
    }
}