package ba.etf.rma21.projekat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.view.ListaKvizovaAdapter
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var listaKvizovaAdapter: ListaKvizovaAdapter
    private lateinit var filterKvizova: Spinner
    private lateinit var upisDugme: FloatingActionButton
    private var kvizListViewModel = KvizListViewModel()


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listaKvizova = findViewById(R.id.listaKvizova)
        upisDugme = findViewById(R.id.upisDugme)
        filterKvizova = findViewById(R.id.filterKvizova)

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
        filterKvizova.onItemSelectedListener = this
        listaKvizova.layoutManager = GridLayoutManager(
            this,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        listaKvizova.addItemDecoration(DefaultItemDecorator(0, 20))
        listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getMyKvizes())
        listaKvizova.adapter = listaKvizovaAdapter
        listaKvizovaAdapter.updateKvizove(kvizListViewModel.getMyKvizes())
        upisDugme.setOnClickListener {
            openUpisPredmet(it)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //No implementation
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        when (parent.getItemAtPosition(pos)) {
            "Svi kvizovi" -> {
                listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getAll())
                listaKvizova.adapter = listaKvizovaAdapter
                listaKvizovaAdapter.updateKvizove(kvizListViewModel.getAll())
            }
            "Urađeni kvizovi" -> {
                listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getDone())
                listaKvizova.adapter = listaKvizovaAdapter
                listaKvizovaAdapter.updateKvizove(kvizListViewModel.getDone())
            }
            "Budući kvizovi" -> {
                listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getFuture())
                listaKvizova.adapter = listaKvizovaAdapter
                listaKvizovaAdapter.updateKvizove(kvizListViewModel.getFuture())
            }
            "Prošli kvizovi (neurađeni)" -> {
                listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getNotTaken())
                listaKvizova.adapter = listaKvizovaAdapter
                listaKvizovaAdapter.updateKvizove(kvizListViewModel.getNotTaken())
            }
            else -> {
                listaKvizovaAdapter = ListaKvizovaAdapter(kvizListViewModel.getMyKvizes())
                listaKvizova.adapter = listaKvizovaAdapter
                listaKvizovaAdapter.updateKvizove(kvizListViewModel.getMyKvizes())
            }
        }
    }
    private fun openUpisPredmet(view : View) {
        val intent = Intent(this, UpisPredmet::class.java).apply {
            putExtra(" ", " " )
        }
        startActivity(intent)
    }
}


