package com.example.app_ext

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val createTeaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data!!
            val name = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_NAME) ?: "Novo Chá"
            val category = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_CATEGORY) ?: "Chá de Ervas"
            val steepTime = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_STEEP_TIME) ?: "5 min"
            val temp = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_TEMP) ?: "90°C"
            val portion = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_PORTION) ?: "1 colher de chá"
            val instructions = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_INSTRUCTIONS) ?: ""
            val benefits = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_BENEFITS) ?: ""
            val imageUri = data.getStringExtra(CreateTeaActivity.EXTRA_NEW_IMAGE_URI)

            val customTea = CustomTea(
                name = name,
                category = category,
                steepTime = steepTime,
                temp = temp,
                portion = portion,
                instructions = instructions,
                benefits = benefits,
                imageUri = imageUri
            )

            // Salvar no armazenamento local do dispositivo
            TeaStorage.saveTea(this, customTea)

            // Adicionar à interface
            addNewTeaCardToGrid(
                name = name,
                category = category,
                steepTime = steepTime,
                temp = temp,
                portion = portion,
                instructions = instructions,
                benefits = benefits,
                imageUri = imageUri
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageButton>(R.id.button)?.setOnClickListener {
            val intent = Intent(this, HomeMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        setupTeaCardClickListeners()
        loadAndDisplaySavedTeas()

        findViewById<FloatingActionButton>(R.id.fabAdd)?.setOnClickListener {
            createTeaLauncher.launch(Intent(this, CreateTeaActivity::class.java))
        }
    }

    private fun loadAndDisplaySavedTeas() {
        val savedTeas = TeaStorage.getSavedTeas(this)
        for (tea in savedTeas) {
            addNewTeaCardToGrid(
                name = tea.name,
                category = tea.category,
                steepTime = tea.steepTime,
                temp = tea.temp,
                portion = tea.portion,
                instructions = tea.instructions,
                benefits = tea.benefits,
                imageUri = tea.imageUri
            )
        }
    }

    private fun setupTeaCardClickListeners() {
        val cardActivityMap = mapOf(
            R.id.cardCamomila to CamomilaActivity::class.java,
            R.id.cardVerde to ChaVerdeActivity::class.java,
            R.id.cardHortela to HortelaActivity::class.java,
            R.id.cardErvaDoce to ErvaDoceActivity::class.java,
            R.id.cardBoldo to BoldoActivity::class.java,
            R.id.cardGengibre to GengibreActivity::class.java,
            R.id.cardHibisco to HibiscoActivity::class.java,
            R.id.cardCapimLobao to CapimLobaoActivity::class.java,
            R.id.cardMate to ChaMateActivity::class.java,
            R.id.cardMelissa to MelissaActivity::class.java,
            R.id.cardJasmim to JasmimActivity::class.java,
            R.id.cardAnis to AnisActivity::class.java,
            R.id.cardCanela to CanelaActivity::class.java,
            R.id.cardCarqueja to CarquejaActivity::class.java,
            R.id.cardAlecrim to AlecrimActivity::class.java,
            R.id.cardMarcela to MarcelaActivity::class.java
        )

        for ((cardId, activityClass) in cardActivityMap) {
            findViewById<MaterialCardView>(cardId)?.setOnClickListener {
                startActivity(Intent(this, activityClass))
            }
        }
    }

    private fun addNewTeaCardToGrid(
        name: String,
        category: String,
        steepTime: String,
        temp: String,
        portion: String,
        instructions: String,
        benefits: String,
        imageUri: String?
    ) {
        val scrollView = findViewById<ScrollView>(R.id.scrollView2) ?: return
        val containerLayout = scrollView.getChildAt(0) as? LinearLayout ?: return

        var lastRow: LinearLayout? = null
        if (containerLayout.childCount > 0) {
            val lastChild = containerLayout.getChildAt(containerLayout.childCount - 1)
            if (lastChild is LinearLayout && lastChild.orientation == LinearLayout.HORIZONTAL && lastChild.childCount < 2) {
                lastRow = lastChild
            }
        }

        if (lastRow == null) {
            lastRow = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dpToPx(12)
                }
                orientation = LinearLayout.HORIZONTAL
            }
            containerLayout.addView(lastRow)
        }

        val isFirstInRow = lastRow.childCount == 0

        val card = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                dpToPx(100),
                1f
            ).apply {
                if (isFirstInRow) marginEnd = dpToPx(6) else marginStart = dpToPx(6)
            }
            radius = dpToPx(12).toFloat()
            cardElevation = dpToPx(4).toFloat()

            val innerLayout = LinearLayout(this@MainActivity).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                orientation = LinearLayout.VERTICAL
                setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12))

                val tvName = TextView(this@MainActivity).apply {
                    text = name
                    setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Medium)
                    setTypeface(null, Typeface.BOLD)
                }

                val tvCategory = TextView(this@MainActivity).apply {
                    text = category
                    setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body2)
                    setPadding(0, dpToPx(4), 0, 0)
                }

                addView(tvName)
                addView(tvCategory)
            }

            addView(innerLayout)

            setOnClickListener {
                val intent = Intent(this@MainActivity, TeaDetailActivity::class.java).apply {
                    putExtra(TeaDetailActivity.EXTRA_TEA_NAME, name)
                    putExtra(TeaDetailActivity.EXTRA_TEA_CATEGORY, category)
                    putExtra(TeaDetailActivity.EXTRA_TEA_BENEFITS, benefits)
                    putExtra(TeaDetailActivity.EXTRA_TEA_TEMP, temp)
                    putExtra(TeaDetailActivity.EXTRA_TEA_STEEP_TIME, steepTime)
                    putExtra(TeaDetailActivity.EXTRA_TEA_PORTION, portion)
                    putExtra(TeaDetailActivity.EXTRA_TEA_INSTRUCTIONS, instructions)
                    imageUri?.let { uri -> putExtra(TeaDetailActivity.EXTRA_TEA_IMAGE_URI, uri) }
                }
                startActivity(intent)
            }
        }

        lastRow.addView(card)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}