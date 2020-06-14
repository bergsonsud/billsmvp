package com.example.billsmvp.scenes.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.billsmvp.R
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.models.Receita
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class MainFragment : Fragment(), CoroutineScope{
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    var totalReceitas = 0F
    var totalDespesas = 0F
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onStart() {
        super.onStart()

        launch {
            withContext(IO) {
                getReceitasValue()
                getDespesasValue()
            }
            valor_receitas.text = totalReceitas.toString()
            valor_despesas.text = totalDespesas.toString()
            saldo_valor.text = (totalReceitas-totalDespesas).toString()
            setupPie()
        }


    }

    private fun setupPie() {
        val pieDataSet = PieDataSet(getPieData(), "Total")
        pieDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.animateXY(0, 0)
        //pieChart.invalidate()

        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(10f)

        pieChart.getDescription().setEnabled(false)

        val l: Legend = pieChart.getLegend()
        l.setDrawInside(false)
        l.xEntrySpace = 20f
        l.yEntrySpace = 20f
        l.yOffset = 0f

        pieData.setValueTextSize(20f)
        pieData.setValueFormatter(DefaultValueFormatter(2))
        pieData.isHighlightEnabled = true

    }

    private fun getPieData(): ArrayList<PieEntry> {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(totalDespesas, "Despesas"))
        entries.add(PieEntry(totalReceitas, "Receitas"))
        return entries

    }

    private suspend fun getDespesasValue() {
        withContext(IO){
            ObjectFirebaseInstance.getAllAsync("despesas",Despesa::class.java){ success, list ->
                totalDespesas = list.map { it.valor }.sum()
            }
        }
    }

    private suspend fun getReceitasValue() {
        withContext(IO){
            ObjectFirebaseInstance.getAllAsync("receitas",Receita::class.java){ success, list ->
                totalReceitas = list.map { it.valor }.sum()
            }
        }
    }
}