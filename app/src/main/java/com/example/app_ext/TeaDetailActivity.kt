package com.example.app_ext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip

class TeaDetailActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_tea_detail)

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

        // Popula os dados se recebidos via Intent
        val name = intent.getStringExtra(EXTRA_TEA_NAME)
        val category = intent.getStringExtra(EXTRA_TEA_CATEGORY)
        val benefits = intent.getStringExtra(EXTRA_TEA_BENEFITS)
        val temp = intent.getStringExtra(EXTRA_TEA_TEMP)
        val steepTime = intent.getStringExtra(EXTRA_TEA_STEEP_TIME)
        val portion = intent.getStringExtra(EXTRA_TEA_PORTION)
        val instructions = intent.getStringExtra(EXTRA_TEA_INSTRUCTIONS)
        val imageUriString = intent.getStringExtra(EXTRA_TEA_IMAGE_URI)

        name?.let { findViewById<TextView>(R.id.tvTeaTitle)?.text = it }
        category?.let { findViewById<Chip>(R.id.chipCategory)?.text = it }
        benefits?.let { findViewById<TextView>(R.id.tvBenefits)?.text = it }
        temp?.let { findViewById<TextView>(R.id.tvTemperature)?.text = it }
        steepTime?.let { findViewById<TextView>(R.id.tvSteepTime)?.text = it }
        portion?.let { findViewById<TextView>(R.id.tvPortion)?.text = it }
        instructions?.let { findViewById<TextView>(R.id.tvInstructions)?.text = it }

        if (!imageUriString.isNullOrEmpty()) {
            try {
                ivTeaImage.setImageURI(Uri.parse(imageUriString))
                layoutAddImagePlaceholder.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val EXTRA_TEA_NAME = "extra_tea_name"
        const val EXTRA_TEA_CATEGORY = "extra_tea_category"
        const val EXTRA_TEA_BENEFITS = "extra_tea_benefits"
        const val EXTRA_TEA_TEMP = "extra_tea_temp"
        const val EXTRA_TEA_STEEP_TIME = "extra_tea_steep_time"
        const val EXTRA_TEA_PORTION = "extra_tea_portion"
        const val EXTRA_TEA_INSTRUCTIONS = "extra_tea_instructions"
        const val EXTRA_TEA_IMAGE_URI = "extra_tea_image_uri"
    }
}