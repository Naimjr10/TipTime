package com.example.tiptime

import android.content.Context
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.hitungButton.setOnClickListener { hitungTip() }
        binding.kolomIsian.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    fun hitungTip() {
        val stringDiKolomIsi = binding.kolomIsian.text.toString()
        val biaya = stringDiKolomIsi.toDoubleOrNull()
        if (biaya == null) {
            binding.jumlahTip.text = getString(R.string.jumlah_tip)
            return
        }

        val IDterpilih = binding.opsiTip.checkedRadioButtonId

        val presentase_tip = when (IDterpilih) {
            R.id.opsi_20persen -> 0.20
            R.id.opsi_18persen -> 0.18
            else -> 0.15
        }

        var tip = biaya * presentase_tip
        val bulatkan = binding.bulatkanTip

        if (bulatkan.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.jumlahTip.text = getString(R.string.jumlah_tip1, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}