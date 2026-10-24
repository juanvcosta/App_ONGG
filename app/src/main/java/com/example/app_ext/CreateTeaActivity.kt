package com.example.app_ext

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateTeaActivity : AppCompatActivity() {

    private lateinit var ivTeaImage: ImageView
    private lateinit var layoutAddImagePlaceholder: LinearLayout
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            ivTeaImage.setImageURI(it)
            layoutAddImagePlaceholder.visibility = View.GONE
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tea)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        ivTeaImage = findViewById(R.id.ivTeaImage)
        layoutAddImagePlaceholder = findViewById(R.id.layoutAddImagePlaceholder)

        findViewById<FrameLayout>(R.id.cardImageContainer).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        val etTeaName = findViewById<TextInputEditText>(R.id.etTeaName)
        val etCategory = findViewById<TextInputEditText>(R.id.etCategory)
        val etSteepTime = findViewById<TextInputEditText>(R.id.etSteepTime)
        val etTemperature = findViewById<TextInputEditText>(R.id.etTemperature)
        val etPortion = findViewById<TextInputEditText>(R.id.etPortion)
        val etInstructions = findViewById<TextInputEditText>(R.id.etInstructions)
        val etBenefits = findViewById<TextInputEditText>(R.id.etBenefits)
        val btnSaveTea = findViewById<MaterialButton>(R.id.btnSaveTea)

        btnSaveTea.setOnClickListener {
            val name = etTeaName.text.toString().trim()
            if (name.isEmpty()) {
                etTeaName.error = "Por favor, informe o nome do chá"
                return@setOnClickListener
            }

            val category = etCategory.text.toString().trim().ifEmpty { "Chá de Ervas" }
            val steepTime = etSteepTime.text.toString().trim().ifEmpty { "5 min" }
            val temp = etTemperature.text.toString().trim().ifEmpty { "90°C" }
            val portion = etPortion.text.toString().trim().ifEmpty { "1 colher de chá" }
            val instructions = etInstructions.text.toString().trim().ifEmpty {
                "1. Ferva a água.\n2. Adicione as ervas.\n3. Deixe em infusão.\n4. Coe e sirva."
            }
            val benefits = etBenefits.text.toString().trim().ifEmpty {
                "Propriedades naturais e medicinais do chá personalizado."
            }

            val resultIntent = Intent().apply {
                putExtra(EXTRA_NEW_NAME, name)
                putExtra(EXTRA_NEW_CATEGORY, category)
                putExtra(EXTRA_NEW_STEEP_TIME, steepTime)
                putExtra(EXTRA_NEW_TEMP, temp)
                putExtra(EXTRA_NEW_PORTION, portion)
                putExtra(EXTRA_NEW_INSTRUCTIONS, instructions)
                putExtra(EXTRA_NEW_BENEFITS, benefits)
                selectedImageUri?.let {
                    putExtra(EXTRA_NEW_IMAGE_URI, it.toString())
                }
            }

            setResult(RESULT_OK, resultIntent)
            Toast.makeText(this, "Chá $name criado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        const val EXTRA_NEW_NAME = "extra_new_name"
        const val EXTRA_NEW_CATEGORY = "extra_new_category"
        const val EXTRA_NEW_STEEP_TIME = "extra_new_steep_time"
        const val EXTRA_NEW_TEMP = "extra_new_temp"
        const val EXTRA_NEW_PORTION = "extra_new_portion"
        const val EXTRA_NEW_INSTRUCTIONS = "extra_new_instructions"
        const val EXTRA_NEW_BENEFITS = "extra_new_benefits"
        const val EXTRA_NEW_IMAGE_URI = "extra_new_image_uri"
    }
}