package ba.etf.rma21.projekat

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences


class PreferenceManager(context: Context) {
    var mContext: Context

    var selection: Int
        get() {
            val sharedPref: SharedPreferences = mContext.getSharedPreferences(
                PREFERENCE_NAME,
                MODE_PRIVATE
            )
            return sharedPref.getInt(KEY_SELECTION, 0)
        }
        set(pos) {
            val sharedPref: SharedPreferences = mContext.getSharedPreferences(
                PREFERENCE_NAME,
                MODE_PRIVATE
            )
            sharedPref.edit().putInt(KEY_SELECTION, pos).commit()
        }

    companion object {
        private const val PREFERENCE_NAME = "test"
        private const val KEY_SELECTION = "key_selection"
    }

    init {
        mContext = context
    }
}