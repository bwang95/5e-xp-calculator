package com.cerridan.dndxpcalc.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.fragment.CalculatorFragment
import com.cerridan.dndxpcalc.util.bindView

class MainActivity : AppCompatActivity() {
    private val toolbar: Toolbar by bindView(R.id.tb_main_toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_main_fragment_host, CalculatorFragment())
            .commit()
    }
}
