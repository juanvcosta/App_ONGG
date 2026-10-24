package com.example.app_ext

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class HomeMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Redirecionamento do botão de chás para o MainActivity (activity_main.xml)
        val cardChas = findViewById<MaterialCardView>(R.id.cardChas)
        cardChas?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Redirecionamento do botão de óleos para o OilsActivity (activity_oils.xml)
        val cardOleos = findViewById<MaterialCardView>(R.id.cardOleos)
        cardOleos?.setOnClickListener {
            val intent = Intent(this, OilsActivity::class.java)
            startActivity(intent)
        }
    }
}