package com.example.app_ext

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class OilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_oils)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.oilsMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, HomeMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        setupOilClickListeners()
    }

    private fun setupOilClickListeners() {
        val oilActivityMap = mapOf(
            R.id.cardLavanda to LavandaOleoActivity::class.java,
            R.id.cardCamomilaOleo to CamomilaOleoActivity::class.java,
            R.id.cardSandalo to SandaloActivity::class.java,
            R.id.cardVetiver to VetiverActivity::class.java,
            R.id.cardRosa to RosaActivity::class.java,
            R.id.cardBergamota to BergamotaActivity::class.java,
            R.id.cardAlecrimOleo to AlecrimOleoActivity::class.java,
            R.id.cardEucalipto to EucaliptoActivity::class.java,
            R.id.cardHortelaPimenta to HortelaPimentaActivity::class.java,
            R.id.cardMelaleuca to MelaleucaActivity::class.java,
            R.id.cardJasmimOleo to JasmimOleoActivity::class.java,
            R.id.cardLaranjaDoce to LaranjaDoceActivity::class.java
        )

        for ((cardId, activityClass) in oilActivityMap) {
            findViewById<MaterialCardView>(cardId)?.setOnClickListener {
                startActivity(Intent(this, activityClass))
            }
        }
    }
}