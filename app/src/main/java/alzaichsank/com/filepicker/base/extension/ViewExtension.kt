package alzaichsank.com.filepicker.base.extension

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun ImageView.rotateWithAnimation(value: Float) {
    this.animate().rotation(value).setDuration(300).start()
}

fun View.makeEnable() {
    this.isEnabled = true
}

fun View.makeDisable() {
    this.isEnabled = false
}

fun EditText.clearText() {
    if (this != null) {
        this.text.clear()
    }

}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun pad(input: Int): String {
    return if (input >= 10) {
        input.toString()
    } else {
        "0$input"
    }
}
