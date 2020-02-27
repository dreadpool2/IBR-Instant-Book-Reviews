package com.example.ibr_instantbookreviews.OtherReviews

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.example.ibr_instantbookreviews.Activities.ResultActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_result_duplicate.*


class OtherReviewsResult (var context: ResultActivity, var context3: Context) : OnChartValueSelectedListener {
    var scannedString = ""

    override fun onNothingSelected() {
        Log.i("No Value Selected" , "HOLA")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Value Selected" , "HOLA")
    }

    fun createOtherReviews(token: String) {

        context.otherReviewsViewModel.scannedString = scannedString

        context.otherReviewsViewModel.createOtherReviews(token)

        context.otherReviewsViewModel.textFlip.observe(context, Observer {
            completeCall(it, context.bookContainer3, context.rating3, context.avRate3, context.reviewCount3)
        })
        context.otherReviewsViewModel.textLib.observe(context, Observer {
            completeCall(it, context.bookContainer4, context.rating4, context.avRate4, context.reviewCount4)
        })
        context.otherReviewsViewModel.textWal.observe(context, Observer {
            completeCall(it, context.bookContainer5, context.rating5, context.avRate5, context.reviewCount5)
        })

        context.otherReviewsViewModel.contextCallCompletion.observe(context, Observer {
            context.callCompletion3 = it
        })

        context.otherReviewsViewModel.progressBarVisibility.observe(context, Observer {
            context.pbar3.visibility = it
        })

        context.otherReviewsViewModel.progressVisibility.observe(context, Observer {
            context.progressBar.visibility = it
        })

        context.otherReviewsViewModel.failure.observe(context, Observer {
            if(it == "Failure!")
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show()
        })

    }
    private fun startPieChart(bookContainer: CardView, resources: Resources, rating: RatingBar, avRate: TextView, averageRating : String, value: Double){
        avRate.text = averageRating
        setData(bookContainer, resources, rating, value)
    }

    private fun setData(bookContainer: CardView, resources: Resources, rating: RatingBar, value: Double) {
        rating.rating = value.toFloat()
        if(value > 4 && value <= 5)
            bookContainer.setCardBackgroundColor(Color.parseColor("#78C8A1"))

        if(value > 3 && value <= 4)
            bookContainer.setCardBackgroundColor(Color.parseColor("#AED787"))

        if(value > 2 && value <= 3)
            bookContainer.setCardBackgroundColor(Color.parseColor("#FFD834"))

        if(value > 1 && value <= 2)
            bookContainer.setCardBackgroundColor(Color.parseColor("#FFB234"))

        if(value >= 0 && value <= 1)
            bookContainer.setCardBackgroundColor(Color.parseColor("#FF8B5A"))

    }

    private fun completeCall (text : ArrayList<String>?, bookContainer: CardView, rating: RatingBar, avRate: TextView, reviewCount: TextView){
        //library thing
        val ratings = text?.get(0)
        val averageRating= text?.get(1)

        if(averageRating?.length!! > 2)
            averageRating.trimEnd()

        var b = 0.0
        try{
            b = averageRating.toDouble()
        } catch(ex : NumberFormatException){ // handle your exception

        }

        var bh = 0
        try{
            bh = Integer.parseInt(ratings)
        } catch(ex : NumberFormatException){ // handle your exception

        }

        if(bh == 0){
            bookContainer.visibility = View.GONE
        } else {
            context.listRatings.add(b)
            context.listNumberRatings.add(bh)
        }

        reviewCount.text = if(bh == 1) ("$bh Review") else ("$bh Reviews")
        //avrate 4 for library thing
        startPieChart(bookContainer, context.resources, rating, avRate, averageRating, b)

    }

}
