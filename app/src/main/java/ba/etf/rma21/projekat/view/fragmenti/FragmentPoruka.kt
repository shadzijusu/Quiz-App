package ba.etf.rma21.projekat.view.fragmenti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel


class FragmentPoruka : Fragment() {
    private lateinit var tvPoruka: TextView
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)
        tvPoruka = view.findViewById(R.id.tvPoruka)
        var bundle = this.arguments
        var poruka = bundle?.getString("data")
        tvPoruka.text = poruka
        return view
    }

    companion object {
        fun newInstance(): FragmentPoruka =
            FragmentPoruka()
    }

}