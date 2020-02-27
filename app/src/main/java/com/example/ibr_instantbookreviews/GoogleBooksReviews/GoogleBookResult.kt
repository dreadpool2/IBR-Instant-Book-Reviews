package com.example.ibr_instantbookreviews.GoogleBooksReviews

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ibr_instantbookreviews.Activities.ResultActivity
import com.example.ibr_instantbookreviews.Utilities.ApiInterface
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_result_duplicate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GoogleBookResult(var context: ResultActivity, var context3: Context) : OnChartValueSelectedListener {
    var scannedString = ""

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

    lateinit var apiInterface: ApiInterface

    fun createGoogle() {
        var averageRating2 : String

        Glide
            .with(context3)
            .load("https://banner2.cleanpng.com/20180324/iww/kisspng-google-logo-g-suite-google-5ab6f1cee66464.5739288415219388949437.jpg")
            .centerCrop()
            .into(context.googleBooksLogo)

        apiInterface = APIClientGoogle.client.create(ApiInterface::class.java)

        val call = apiInterface.getJsonGoogle("isbn:$scannedString")
        call.enqueue(object : Callback<GoogleBooksObject> {
            override fun onResponse(call: Call<GoogleBooksObject>, response: Response<GoogleBooksObject>) {
                context.callCompletion2 = true
                context.pbar2.visibility = View.GONE

                val resource = response.body()
                val text = resource?.googleBooks
                val total = text?.get(0)
                averageRating2 = total?.volumeInfo?.averageRating.toString()
                if(averageRating2.length > 2)
                    averageRating2.trimEnd()
                Toast.makeText(context, averageRating2, Toast.LENGTH_LONG).show()
                //parse values

                var a = 0.0
                try{
                     a = averageRating2.toDouble()
                } catch(ex : NumberFormatException){ // handle your exception

                }
                additionalAddOns(a)

                //startPieChart(averageRating2, a)


                val ratings = text?.get(0)?.volumeInfo?.ratingsCount.toString()

                var ah = 0
                try{
                    ah = Integer.parseInt(ratings)
                } catch(ex : java.lang.NumberFormatException){ // handle your exception

                }
                if(ah == 0){
                    context.bookContainer2.visibility = View.GONE
                } else {
                    context.listRatings.add(a)
                    context.listNumberRatings.add(ah)
                }
                context.avRate2.text = a.toString()

                context.reviewCount2.text = if(ah == 1) ("$ah Review") else ("$ah Reviews")
            }

            override fun onFailure(call: Call<GoogleBooksObject>, t: Throwable) {
                Log.e("Hello", t.toString())

                //call.cancel()
                Toast.makeText(context, "Duh ! :(", Toast.LENGTH_LONG).show()

            }
        })
    }
    private fun additionalAddOns(value : Double){
        if(value > 4 && value <= 5){
            context.bookContainer2.setCardBackgroundColor(Color.parseColor("#78C8A1"))
        }
        if(value > 3 && value <= 4){
            context.bookContainer2.setCardBackgroundColor(Color.parseColor("#AED787"))
        }
        if(value > 2 && value <= 3){
            context.bookContainer2.setCardBackgroundColor(Color.parseColor("#FFD834"))
        }
        if(value > 1 && value <= 2){
            context.bookContainer2.setCardBackgroundColor(Color.parseColor("#FFB234"))
        }
        if(value >= 0 && value <= 1){
            context.bookContainer2.setCardBackgroundColor(Color.parseColor("#FF8B5A"))
        }
    }
}
