package com.example.app_ext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OilDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oil_detail)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, OilsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Popula dados dinâmicos se recebidos via Intent
        intent.getStringExtra(EXTRA_OIL_INTRO)?.let {
            findViewById<TextView>(R.id.tvOilIntro)?.text = it
        }
        intent.getStringExtra(EXTRA_CHIP_1)?.let {
            findViewById<TextView>(R.id.tvChip1)?.text = it
        }
        intent.getStringExtra(EXTRA_CHIP_2)?.let {
            findViewById<TextView>(R.id.tvChip2)?.text = it
        }
        intent.getStringExtra(EXTRA_CHIP_3)?.let {
            findViewById<TextView>(R.id.tvChip3)?.text = it
        }
        intent.getStringExtra(EXTRA_CHIP_4)?.let {
            findViewById<TextView>(R.id.tvChip4)?.text = it
        }
        intent.getStringExtra(EXTRA_MASSAGE_INFO)?.let {
            findViewById<TextView>(R.id.tvMassageInfo)?.text = it
        }
        intent.getStringExtra(EXTRA_INHALATION_INFO)?.let {
            findViewById<TextView>(R.id.tvInhalationInfo)?.text = it
        }
        intent.getStringExtra(EXTRA_FOOTBATH_INFO)?.let {
            findViewById<TextView>(R.id.tvFootBathInfo)?.text = it
        }
    }

    companion object {
        const val EXTRA_OIL_INTRO = "extra_oil_intro"
        const val EXTRA_CHIP_1 = "extra_chip_1"
        const val EXTRA_CHIP_2 = "extra_chip_2"
        const val EXTRA_CHIP_3 = "extra_chip_3"
        const val EXTRA_CHIP_4 = "extra_chip_4"
        const val EXTRA_MASSAGE_INFO = "extra_massage_info"
        const val EXTRA_INHALATION_INFO = "extra_inhalation_info"
        const val EXTRA_FOOTBATH_INFO = "extra_footbath_info"
    }
}