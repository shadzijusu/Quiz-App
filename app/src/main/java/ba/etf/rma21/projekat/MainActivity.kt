package ba.etf.rma21.projekat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.view.ListaKvizovaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var listaKvizova : RecyclerView
    private lateinit var listaKvizovaAdapter : ListaKvizovaAdapter
    private lateinit var filterKvizova : Spinner
    private lateinit var upisDugme : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listaKvizova = findViewById(R.id.listaKvizova)
        filterKvizova = findViewById(R.id.filterKvizova)
        upisDugme = findViewById(R.id.upisDugme)


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.filteri,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            filterKvizova.adapter = adapter
        }
        listaKvizova.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        listaKvizovaAdapter = ListaKvizovaAdapter(listOf())
        listaKvizova.adapter = listaKvizovaAdapter
        //listaKvizovaAdapter.updateKvizove(kvizListViewModel.get())

        upisDugme.setOnClickListener {
            openUpisPredmet()
        }
    }

    private fun openUpisPredmet() {
        TODO("Not yet implemented")
    }
}

