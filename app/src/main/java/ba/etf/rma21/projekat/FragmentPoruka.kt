package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class FragmentPoruka : Fragment() {
    private lateinit var tvPoruka: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)
        tvPoruka = view.findViewById(R.id.tvPoruka)
        var bundle = this.arguments
        var poruka = bundle?.getString("data")
        var predmetIGrupa: List<String>? = poruka?.split("-")
        var nazivPredmeta = predmetIGrupa?.get(0)
        var nazivGrupe = predmetIGrupa?.get(1)
        poruka = "Uspješno ste upisani u grupu ${nazivGrupe} predmeta ${nazivPredmeta}!"
        tvPoruka.text = poruka

        return view
    }

    companion object {
        fun newInstance(): FragmentPoruka = FragmentPoruka()
    }

}