package ba.etf.rma21.projekat

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class UpisPredmet : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)

        // Enables Always-on
        setAmbientEnabled()
    }
}