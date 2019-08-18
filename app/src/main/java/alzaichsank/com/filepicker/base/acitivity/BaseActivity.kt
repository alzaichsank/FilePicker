@file:Suppress("DEPRECATION")

package alzaichsank.com.filepicker.base.acitivity

import alzaichsank.com.filepicker.R
import alzaichsank.com.filepicker.base.extension.isVisible
import alzaichsank.com.filepicker.base.extension.makeGone
import alzaichsank.com.filepicker.base.extension.makeVisible
import android.app.ProgressDialog
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_loading.*

open class BaseActivity : AppCompatActivity() {

    protected var disableBackPressed: Boolean = false
    protected var wantExitNow: Boolean = false
    protected var loading: ProgressDialog? = null

    override fun onBackPressed() {
        if (disableBackPressed) {
            return
        } else {
            if (wantExitNow) {
                finish()
            } else {
                super.onBackPressed()
            }
        }
    }

    protected fun showDialog(messageToShow: String) {
        loading = ProgressDialog(this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT)
        loading = ProgressDialog.show(
            this, getString(R.string.progress_loading),
            messageToShow, false, false
        )
        loading?.show()
    }

    protected fun hideDialog() {
        if (loading != null) {
            if (loading!!.isShowing) {
                loading!!.dismiss()
                loading = null
            }
        }
    }

    protected fun showLoad() {
        if (linear_layout_loading != null) {
            linear_layout_loading.makeVisible()
            progress_loading.makeVisible()
            progress_text.makeGone()
        }
    }

    protected fun showLoad(messageToShow: String) {
        if (linear_layout_loading != null) {
            linear_layout_loading.makeVisible()
            progress_loading.makeVisible()
            progress_text.makeVisible()
            progress_text.text = messageToShow
        }
    }

    protected fun changeLoadText(messageToShow: String) {
        if (linear_layout_loading != null) {
            if (linear_layout_loading.isVisible()
                && progress_text.isVisible()
                && progress_loading.isVisible()
            ) {
                progress_text.text = messageToShow
            } else if (linear_layout_loading.isVisible()
                && progress_loading.isVisible()
            ) {
                progress_text.makeVisible()
                progress_text.text = messageToShow
            }
        }
    }

    protected fun hideLoad() {
        if (linear_layout_loading != null) {
            linear_layout_loading.makeGone()
        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideDialog()
    }
}