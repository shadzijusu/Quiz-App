package ba.etf.rma21.projekat


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var infoButton: ImageButton
    private lateinit var close: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listaKvizova = findViewById(R.id.listaKvizova)
        upisDugme = findViewById(R.id.upisDugme)
        filterKvizova = findViewById(R.id.filterKvizova)
        infoButton = findViewById(R.id.infoButton)

        infoButton.setOnClickListener {
            val popupWindow = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.activity_info, null)
            popupWindow.contentView = view
            // Closes the popup window when touch outside. This method was written informatively in Google's docs.
            popupWindow.isOutsideTouchable = true

            // Set focus true to prevent a touch event to go to a below view (main layout), which works like a dialog with 'cancel' property => Try it! And you will know what I mean.
            popupWindow.isFocusable = true

            // Removes default background.
            popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            popupWindow.showAsDropDown(filterKvizova, 0, 102*8)
        }
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
            LinearLayoutManager.VERTICAL,
            false
        )
        listaKvizova.addItemDecoration(DefaultItemDecorator(15, 5))
        listaKvizovaAdapter =
            ListaKvizovaAdapter(kvizListViewModel.getMyKvizes().sortedBy { it.datumPocetka })
        listaKvizova.adapter = listaKvizovaAdapter
        listaKvizovaAdapter.updateKvizove(
            kvizListViewModel.getMyKvizes().sortedBy { it.datumPocetka })

        upisDugme.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this,
                    UpisPredmet::class.java
                )
            )
        })
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

    override fun onResume() {
        super.onResume()
        listaKvizovaAdapter.notifyDataSetChanged()
        listaKvizovaAdapter.updateKvizove(
            kvizListViewModel.getMyKvizes().sortedBy { it.datumPocetka })
    }
}


