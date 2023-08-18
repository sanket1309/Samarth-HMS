package com.samarthhms.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.SearchView

class TextChangeListenerUtils {
    companion object{
        fun getTextWatcher(onBeforeTC: (()->Unit)? = null, onTC: (()->Unit)? = null, onAfterTC: (()->Unit)? = null):TextWatcher {
            return object: TextWatcher {
                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) { onBeforeTC?.let { onBeforeTC() } }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { onTC?.let { onTC() } }
                override fun afterTextChanged(s: Editable?) { onAfterTC?.let { onAfterTC() } }
            }
        }

        fun getQueryListener(onQTChange: ((newText: String?)->Unit)? = null, onQTSubmit: ((query: String?)->Unit)? = null):SearchView.OnQueryTextListener {
            return object: SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    onQTChange?.let { onQTChange(newText) }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    onQTSubmit?.let { onQTSubmit(query) }
                    return false
                }
            }
        }
    }
}