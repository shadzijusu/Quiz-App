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
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel
import okhttp3.ResponseBody


class FragmentPredmeti : Fragment() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var upisDugme: Button
    private lateinit var predmetListViewModel: PredmetListViewModel
    private var grupaId = 0
    private var preferenceManger: PreferenceManager? = null
    private var grupeZaPredmet: ArrayList<Grupa> = arrayListOf()
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
            PredmetListViewModel(null, null)


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
        var naziviPredmeta = arrayListOf<String>()
        val predmetiAdapter: ArrayAdapter<String>
        predmetiAdapter = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_dropdown_item,
            naziviPredmeta
        )
        odabirPredmet.adapter = predmetiAdapter

        var naziviGrupa = arrayListOf<String>()
        val grupeAdapter: ArrayAdapter<String>
        grupeAdapter = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_dropdown_item,
            naziviGrupa
        )
        odabirGrupa.setAdapter(grupeAdapter)

        predmetListViewModel.getAll(
            onSuccess = ::onSuccess,
            onError = ::onError
        )
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
                if (godina == 1) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        // Use `id` for example to update UI.
                        naziviPredmeta.clear()
                        if (id != null)
                            for (predmet in id) {
                                if (predmet.godina == 1)
                                    naziviPredmeta.add(predmet.naziv)
                            }
                        predmetiAdapter.notifyDataSetChanged()
                    })

                } else if (godina == 2) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        if (id != null)
                            for (predmet in id) {
                                if (predmet.godina == 2)
                                    naziviPredmeta.add(predmet.naziv)
                            }
                        predmetiAdapter.notifyDataSetChanged()

                    })


                } else if (godina == 3) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        if (id != null)
                            for (predmet in id) {
                                if (predmet.godina == 3)
                                    naziviPredmeta.add(predmet.naziv)
                            }
                        predmetiAdapter.notifyDataSetChanged()

                    })

                } else if (godina == 4) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        if (id != null)
                            for (predmet in id) {
                                if (predmet.godina == 4)
                                    naziviPredmeta.add(predmet.naziv)
                            }
                        predmetiAdapter.notifyDataSetChanged()

                    })

                } else if (godina == 5) {
                    predmetListViewModel.predmeti.observe(requireActivity(), Observer { id ->
                        naziviPredmeta.clear()
                        // Use `id` for example to update UI.
                        if (id != null)
                            for (predmet in id) {
                                if (predmet.godina == 5)
                                    naziviPredmeta.add(predmet.naziv)
                            }
                        predmetiAdapter.notifyDataSetChanged()

                    })
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

                var nazivPredmeta: String = parent.getItemAtPosition(position).toString()

                predmetListViewModel.getAll(
                    onSuccess = ::onSuccess,
                    onError = ::onError
                )
                var idPredmeta: Int = 0
                predmetListViewModel.predmeti.observe(requireActivity(), Observer {
                    if (it != null) {
                        for (predmet in it) {
                            if (predmet.naziv == nazivPredmeta) {
                                idPredmeta = predmet.id
                                break
                            }
                        }
                    }
                })


                predmetListViewModel.getGrupeZaPredmet(
                    onSuccess = ::onSuccessGrupe,
                    onError = ::onError,
                    idPredmeta = idPredmeta
                )

                predmetListViewModel.grupeZaPredmet.observe(requireActivity(), Observer {
                    if (it != null) {
                        naziviGrupa.clear()
                        for (grupa in it) {
                            naziviGrupa.add(grupa.naziv)
                            grupeZaPredmet.add(grupa)
                        }
                    }
                    grupeAdapter.notifyDataSetChanged()

                })
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
                var nazivGrupe = parent.getItemAtPosition(position)
                for (grupa in grupeZaPredmet) {
                    if (grupa.naziv.equals(nazivGrupe)) {
                        grupaId = grupa.id
                        break
                    }
                }

            }

        }
                )


        upisDugme.setOnClickListener {
            if (odabirPredmet.selectedItem != null)
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
        predmetListViewModel.upisiStudenta(
            onSuccess = ::onSuccessUpis,
            onError = ::onError,
            idGrupe = grupaId
        )

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

    fun onSuccessGrupe(grupe: List<Grupa>) {
        val toast = Toast.makeText(context, "Grupe pronađene", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccess(predmeti: List<Predmet>) {
        val toast = Toast.makeText(context, "Predmeti pronađeni", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessUpis(upis: ResponseBody) {
        val toast = Toast.makeText(context, "Upisan", Toast.LENGTH_SHORT)
        toast.show()
    }
}