package alzaichsank.com.filepicker

import alzaichsank.com.filepicker.base.acitivity.BaseActivity
import alzaichsank.com.filepicker.base.helper.Logger
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStoret
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainActivity : BaseActivity() {

    companion object {
        const val STORAGE_PERMISSION = 123
        const val REQUEST_FILE_CODE = 345
        const val DELAY_DURATION = 750L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun doOnButtonInsertFileClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isStoragePermissionGranted(this)) {
                val openFileIntent = Intent(Intent.ACTION_GET_CONTENT)
                openFileIntent.type = "*/*"
                startActivityForResult(openFileIntent, REQUEST_FILE_CODE)
            }
        }
    }

    private fun isStoragePermissionGranted(context: Activity): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    true
                } else {
                    ActivityCompat
                        .requestPermissions(
                            context,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            STORAGE_PERMISSION
                        )
                    false
                }
            }
            else -> true
        }
    }

    @SuppressLint("Recycle")
    private fun getRealPathFromURIPath(context: Activity, contentURI: Uri): String {
        val cursor = context.contentResolver.query(
            contentURI,
            null,
            null,
            null,
            null
        )
        return if (cursor == null) {
            contentURI.path ?: ""
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            when (requestCode) {
                REQUEST_FILE_CODE -> {
                    when {
                        resultCode == Activity.RESULT_OK && data != null -> {
                            Handler().postDelayed({
                                val uri = data.data
                                val filePath = File(uri?.path).absolutePath
                                val requestBodyFile = RequestBody.create(MediaType.parse("*/*"), filePath)
                                Logger.debug("CEK filePath >> $filePath ")
                                val fileToUpload =
                                    MultipartBody.Part.createFormData("file", File(uri?.path).name, requestBodyFile)
                                Logger.debug("CEK GALLERY RESULT >> $fileToUpload path ${uri?.path} detauk ${File(uri?.path).name}")
                            }, DELAY_DURATION)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            showSnackBar(getString(R.string.error_general))
            alzaichsank.com.filepicker.base.helper.Logger.debug("ERROR >> $e")
        }
    }

    private fun showSnackBar(messageToShow: String) {
        Snackbar.make(layout_main_filepicker, messageToShow, Snackbar.LENGTH_LONG).show()
    }
}
