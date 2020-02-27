package com.example.ibr_instantbookreviews.Utilities

import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.text.HtmlCompat
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Activities.ResultActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.activity_result_duplicate.*


class CreatePieChart(var context: ResultActivity, var pieChart: PieChart) : OnChartValueSelectedListener {

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return
        Log.i("VAL SELECTED",
            "Value: " + e.getY() + ", index: " + h?.getX()
                + ", DataSet index: " + h?.getDataSetIndex())
    }

    fun startPieChart(ratingValues: ArrayList<Int>, averageRating : String, value: Double, nRatings : Int){

        pieChart.setUsePercentValues(true)

        pieChart.description.isEnabled = false

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.setCenterTextColor(Color.BLACK)

        pieChart.setCenterTextSize(15f)

        pieChart.isDrawHoleEnabled = true

        pieChart.setHoleColor(Color.WHITE)

        pieChart.setTransparentCircleColor(Color.WHITE)

        pieChart.setTransparentCircleAlpha(110)

        pieChart.holeRadius = 80f

        pieChart.transparentCircleRadius = 0f

        pieChart.setDrawCenterText(true)

        pieChart.rotationAngle = 0.0F

        pieChart.setOnChartValueSelectedListener(this)

        pieChart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        setData(ratingValues, 5, 3f, value)
        pieChart.centerText = nRatings.toString() + "\nSources"
        pieChart.setCenterTextSize(12f)
        pieChart.setEntryLabelTypeface(context.resources.getFont(R.font.raleway_light))
        pieChart.legend.isEnabled = false

    }

    private fun setData(ratingValues: ArrayList<Int>, count: Int, range: Float, value: Double) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until ratingValues.size) {
            entries.add(
                PieEntry(
                    ratingValues[i].toFloat(),
                    HtmlCompat.fromHtml("",HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                    context.resources.getDrawable(R.drawable.abc_ic_star_black_48dp, context.theme)
                )
            )
        }

        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 1f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.valueTypeface = context.resources.getFont(R.font.raleway_light)
        dataSet.valueFormatter = PercentFormatter(pieChart)
        dataSet.valueTextSize = 0.0f
        dataSet.setDrawValues(false)

        // add a lot of colors

        val colors = ArrayList<Int>()

        context.ratingGod.rating = value.toFloat()
        if(ratingValues.size == 3){
            colors.add(Color.parseColor("#78C8A1"))
            colors.add(Color.parseColor("#AED787"))
            colors.add(Color.parseColor("#FFD834"))
        }
        if(ratingValues.size == 2){
            colors.add(Color.parseColor("#AED787"))
            colors.add(Color.parseColor("#FFD834"))
        }
        if(ratingValues.size == 1){
            colors.add(Color.parseColor("#FFD834"))
        }
        if(ratingValues.size == 4){
            colors.add(Color.parseColor("#FFB234"))
            colors.add(Color.parseColor("#FFD834"))
            colors.add(Color.parseColor("#AED787"))
            colors.add(Color.parseColor("#78C8A1"))
        }

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            pieChart.setCenterTextTypeface(context.resources.getFont(R.font.raleway_light))

    }
    
}
