package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class UpisPredmet : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var dodajPredmetDugme: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)
        dodajPredmetDugme = findViewById(R.id.dodajPredmetDugme)
        dodajPredmetDugme.visibility = View.GONE


        odabirGodina = findViewById(R.id.odabirGodina)
        odabirPredmet = findViewById(R.id.odabirPredmet)
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
        odabirGodina.onItemSelectedListener = this

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

    }
}
