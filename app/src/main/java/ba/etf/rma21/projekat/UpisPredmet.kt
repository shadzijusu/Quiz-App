package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel


class UpisPredmet : AppCompatActivity() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var dodajPredmetDugme: Button
    private var kvizListViewModel = KvizListViewModel()
    private var predmetListViewModel = PredmetListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)
        dodajPredmetDugme = findViewById(R.id.dodajPredmetDugme)
        dodajPredmetDugme.visibility = View.GONE
        dodajPredmetDugme.setOnClickListener {
            upisiMe()
        }

        odabirGodina = findViewById(R.id.odabirGodina)
        odabirPredmet = findViewById(R.id.odabirPredmet)
        odabirGrupa = findViewById(R.id.odabirGrupe)

        ArrayAdapter.createFromResource(
            this,
            R.array.godine,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            odabirGodina.adapter = adapter
        }

        var godina: Int = 0
        odabirGodina.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
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
        var odabranPredmet: Boolean = false
        odabirPredmet.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                var predmet: String = parent.getItemAtPosition(position).toString()
                when (predmet) {
                    "RA" -> ArrayAdapter.createFromResource(
                        view.context,
                        R.array.grupeRA,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        odabirGrupa.adapter = adapter
                    }
                    "OOAD" ->
                        ArrayAdapter.createFromResource(
                            view.context,
                            R.array.grupeOOAD,
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
        odabirGrupa.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (godina != 0 && odabranPredmet == true)
                    dodajPredmetDugme.visibility = View.VISIBLE
            }
        })
    }

    private fun upisiMe() {
        var godina = odabirGodina.selectedItem
        var nazivPredmeta = odabirPredmet.selectedItem
        var nazivGrupe = odabirGrupa.selectedItem
        //implement
    }
}
