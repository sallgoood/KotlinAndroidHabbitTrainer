package it.`is`.all.good.android.habbittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import it.`is`.all.good.android.habbittrainer.repository.HabbitRepository
import kotlinx.android.synthetic.main.activity_add_habbit.*
import java.io.IOException

class AddHabbitActivity : AppCompatActivity() {

    private val TAG = AddHabbitActivity::class.java.simpleName

    private val CHOOSE_IMAGE_REQUEST = 1

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habbit)
    }

    fun chooseImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habbit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose image sent...")
    }

    fun storeHabbit(v: View) {
        if (edit_title.isBlank() || edit_description.isBlank()) {
            Log.d(TAG, "No habit stored: title or description missing")
            displayErrorMessage("Your habbit needs an engaging title and description")
            return
        } else if (imageBitmap == null) {
            Log.d(TAG, "No habbit stored: image missing")
            displayErrorMessage("Add a motivating picture to your habbit")
            return
        }

        tv_error.visibility = View.INVISIBLE
        val title = edit_title.text.toString()
        val description = edit_description.text.toString()
        val habbit = Habbit(title, description, imageBitmap!!)

        val id = HabbitRepository(this).save(habbit)

        if (id == -1L) {
            displayErrorMessage("Habbit could not be stored...")
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text = message
        tv_error.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && intent != null && intent.data != null) {
            Log.d(TAG, "An image was chosen by the user")

            val bitmap = tryReadBitmap(intent.data!!)

            bitmap?.let {
                this.imageBitmap = bitmap
                iv_image.setImageBitmap(bitmap)
                Log.d(TAG, "Read image bitmap and updated image view")
            }
        }

    }

    private fun tryReadBitmap(uri: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}

private fun EditText.isBlank() = this.text.toString().isBlank()
