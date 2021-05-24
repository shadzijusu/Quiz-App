package ba.etf.rma21.projekat.view.fragmenti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ba.etf.rma21.projekat.PreferenceManager
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaListViewModel
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel


class FragmentPredmeti : Fragment() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var upisDugme: Button
    private var kvizListViewModel = KvizListViewModel(null, null)
    private lateinit var predmetListViewModel: PredmetListViewModel
    private lateinit var grupaListViewModel: GrupaListViewModel
    private var predmetId = 0
    private var grupaId = 0
    private var preferenceManger: PreferenceManager? = null
    private lateinit var grupa: Grupa
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_predmeti, container, false)
        preferenceManger = PreferenceManager(view.context)
        odabirGodina = view.findViewById(R.id.odabirGodina)
        odabirPredmet = view.findViewById(R.id.odabirPredmet)
        odabirGrupa = view.findViewById(R.id.odabirGrupa)
        upisDugme = view.findViewById(R.id.dodajPredmetDugme)
        predmetListViewModel =
            PredmetListViewModel(this@FragmentPredmeti::searchDone, this@FragmentPredmeti::onError)
        grupaListViewModel = GrupaListViewModel(
            this@FragmentPredmeti::searchDoneGrupe,
            this@FragmentPredmeti::onError
        )

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
        predmetListViewModel.getAll()
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
                var naziviPredmeta = arrayListOf<String>()
                if (godina == 1) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        // Use `id` for example to update UI.
                        naziviPredmeta.clear()
                        for (predmet in id) {
                            if (predmet.godina == 1)
                                naziviPredmeta.add(predmet.naziv)
                        }
                    })
                    odabirPredmet.adapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        naziviPredmeta
                    )

                } else if (godina == 2) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        for (predmet in id) {
                            if (predmet.godina == 2)
                                naziviPredmeta.add(predmet.naziv)
                        }
                    })
                    odabirPredmet.adapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        naziviPredmeta
                    )

                } else if (godina == 3) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        for (predmet in id) {
                            if (predmet.godina == 3)
                                naziviPredmeta.add(predmet.naziv)
                        }
                    })
                    odabirPredmet.adapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        naziviPredmeta
                    )
                } else if (godina == 4) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        for (predmet in id) {
                            if (predmet.godina == 4)
                                naziviPredmeta.add(predmet.naziv)
                        }
                    })
                    odabirPredmet.adapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        naziviPredmeta
                    )
                } else if (godina == 5) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        for (predmet in id) {
                            if (predmet.godina == 5)
                                naziviPredmeta.add(predmet.naziv)
                        }
                    })
                    odabirPredmet.adapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        naziviPredmeta
                    )
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
                //Grupa (naziv, nazivPredmeta)
                var naziviGrupa = arrayListOf<String>()
                var nazivPredmeta: String = parent.getItemAtPosition(position).toString()
                predmetListViewModel.predmeti.observe(requireActivity(), Observer {
                    for (predmet in it) {
                        if (predmet.naziv == nazivPredmeta) {
                            predmetId = predmet.id
                            break
                        }
                    }
                })
                grupaListViewModel.getGrupeZaPredmet(predmetId)
                grupaListViewModel.grupe.observe(requireActivity(), Observer {
                    naziviGrupa.clear()
                    for(grupa in it)
                        naziviGrupa.add(grupa.naziv)

                })
                odabirGrupa.adapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    naziviGrupa
                )
                odabranPredmet = true
            }
        }
                )
        odabirGrupa.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                grupaListViewModel.grupe.observe(requireActivity(), Observer {
                    for(grupa in it)
                        if(grupa.naziv == parent.getItemAtPosition(position)) {
                            grupaId = grupa.id
                            break
                        }
                })
            }
        }
                )

        upisDugme.setOnClickListener {
            if (odabirPredmet.selectedItem != null && odabirGrupa.selectedItem != null)
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

        predmetListViewModel.upisiUGrupu(grupaId)

        var bundle: Bundle = Bundle()
        bundle.putString(
            "data",
            "Uspješno ste upisani u grupu ${nazivGrupe} predmeta ${nazivPredmeta}!"
        )
        val porukaFragment =
            FragmentPoruka()
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

    fun searchDone(predmeti: List<Predmet>) {
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun searchDoneGrupe(grupe: List<Grupa>) {
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
}