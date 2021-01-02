package com.cerridan.dndxpcalc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.fragment.CalculatorFragment
import com.cerridan.dndxpcalc.fragment.ResultFragment
import com.cerridan.dndxpcalc.model.CalcResult
import com.cerridan.dndxpcalc.util.bindView

/**
 * Main Activity.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class MainActivity : AppCompatActivity() {

    private val toolbar: Toolbar by bindView(R.id.tb_main_toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main_fragment_host, CalculatorFragment())
            .commit()
    }

    /**
     * Show the given result of a calculation.
     */
    fun showResult(result: CalcResult) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_main_fragment_host, ResultFragment.create(result))
            .addToBackStack(null)
            .commit()
    }
}
