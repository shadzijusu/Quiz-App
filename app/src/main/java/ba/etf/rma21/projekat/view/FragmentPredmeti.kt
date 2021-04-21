package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.PreferenceManager
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel


class FragmentPredmeti : Fragment() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var upisDugme : Button
    private var kvizListViewModel = KvizListViewModel()
    private var predmetListViewModel = PredmetListViewModel()
    private var preferenceManger: PreferenceManager? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_predmeti, container, false)
        preferenceManger = PreferenceManager(view.context)
        odabirGodina = view.findViewById(R.id.odabirGodina)
        odabirPredmet = view.findViewById(R.id.odabirPredmet)
        odabirGrupa = view.findViewById(R.id.odabirGrupa)
        upisDugme = view.findViewById(R.id.dodajPredmetDugme)

        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.godine,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                odabirGodina.adapter = adapter
            }
        }
        var godina: Int = 0
        odabirGodina.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {
                odabirGodina.setSelection(preferenceManger!!.selection)
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                preferenceManger!!.selection = position
                godina =
                    when (parent.getItemAtPosition(position)) {
                        "1" -> 1
                        "2" -> 2
                        "3" -> 3
                        "4" -> 4
                        "5" -> 5
                        else -> 0
                    }
                if(godina == 0) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.odaberite,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                }
                if (godina == 1) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.prva,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                } else if (godina == 2) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.druga,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                } else if (godina == 3) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.treca,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                } else if (godina == 4) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.cetvrta,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                } else if (godina == 5) {
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.peta,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirPredmet.adapter = adapter
                    }
                }
            }
        })
        var odabranPredmet = false
        odabirPredmet.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                var predmet: String = parent.getItemAtPosition(position).toString()
                when (predmet) {
                    "VIS" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeVIS,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "OS" ->
                        ArrayAdapter.createFromResource(
                            view.context,
                            R.array.grupeOS,
                            android.R.layout.simple_spinner_item
                        ).also { adapter ->
                            // Specify the layout to use when the list of choices appears
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            // Apply the adapter to the spinner
                            odabirGrupa.adapter = adapter
                        }
                    "MLTI" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeMLTI,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "TP" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeTP,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "ASP" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeASP,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "OBP" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeOBP,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "DM" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeDM,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "RPR" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeRPR,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "OOAD" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeOOAD,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "RMA" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeRMA,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "WT" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeWT,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "OOI" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeOOI,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "VVS" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeVVS,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "VI" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeVI,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "RV" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeRV,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "NOS" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeNOS,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "NASP" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeNASP,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "TS" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeTS,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "MPVI" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeMPVI,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "NSI" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeNSI,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                }
                odabranPredmet = true
            }
        })
        upisDugme.setOnClickListener {
            if(odabirPredmet.selectedItem != null && odabirGrupa.selectedItem != null)
            upisiMe()
        }
        return view
    }
    companion object {
        fun newInstance(): FragmentPredmeti =
            FragmentPredmeti()
    }
    private fun upisiMe() {
        var godina = odabirGodina.selectedItem.toString()
        var nazivPredmeta = odabirPredmet.selectedItem.toString()
        var nazivGrupe = odabirGrupa.selectedItem.toString()

        predmetListViewModel.upisiPredmet(Predmet(nazivPredmeta, godina.toInt()))

        for (kviz in kvizListViewModel.getAll()) {
            if (kviz.nazivPredmeta.equals(nazivPredmeta) || kviz.nazivGrupe.equals(nazivGrupe) && !kvizListViewModel.getMyKvizes()
                    .contains(kviz)
            )
                kvizListViewModel.addMine(kviz)
        }
            var bundle: Bundle = Bundle()
            bundle.putString(
                "data",
                "Uspješno ste upisani u grupu ${nazivGrupe} predmeta ${nazivPredmeta}!"
            )
            val porukaFragment = FragmentPoruka()
            porukaFragment.arguments = bundle
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, porukaFragment)?.addToBackStack(null)
                ?.commit()
        }

    override fun onResume() {
        super.onResume()
        odabirGodina.setSelection(preferenceManger?.selection!!)
    }
}