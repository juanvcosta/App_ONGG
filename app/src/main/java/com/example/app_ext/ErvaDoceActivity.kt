package com.example.app_ext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ErvaDoceActivity : AppCompatActivity() {

    private lateinit var ivTeaImage: ImageView
    private lateinit var layoutAddImagePlaceholder: LinearLayout

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            ivTeaImage.setImageURI(it)
            layoutAddImagePlaceholder.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_erva_doce)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        ivTeaImage = findViewById(R.id.ivTeaImage)
        layoutAddImagePlaceholder = findViewById(R.id.layoutAddImagePlaceholder)

        findViewById<FrameLayout>(R.id.cardImageContainer).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }
}