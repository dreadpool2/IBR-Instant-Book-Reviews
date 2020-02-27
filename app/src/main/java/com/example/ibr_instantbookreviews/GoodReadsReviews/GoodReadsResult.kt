package com.example.ibr_instantbookreviews.GoodReadsReviews

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.ibr_instantbookreviews.Activities.ResultActivity
import com.example.ibr_instantbookreviews.Activities.ScanActivity
import com.example.ibr_instantbookreviews.GoogleBooksReviews.GoogleBookResult
import com.example.ibr_instantbookreviews.OtherReviews.OtherReviewsResult
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Utilities.ApiInterface
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_result_duplicate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoodReadsResult (var context: ResultActivity, var context3: Context) : OnChartValueSelectedListener {
    var scannedString = ""
    var scannedId = ""

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return
        Log.i("VAL SELECTED",
            "Value: " + e.getY() + ", index: " + h?.getX()
                + ", DataSet index: " + h?.getDataSetIndex());
    }

    lateinit var apiInterface: ApiInterface

    fun createGoodReads() {

        apiInterface = APIClientGoodreads.client.create(ApiInterface::class.java)

        //get isbn from scanner activity

        var averageRating : String

        lateinit var  call : Call<GoodReadsBookObject>
        if(scannedString.isNotEmpty()){
            call = apiInterface.getXml(scannedString)
        } else {
            call = apiInterface.getXmlId(scannedId)
        }

        call.enqueue(object : Callback<GoodReadsBookObject> {
            override fun onResponse(call: Call<GoodReadsBookObject>, response: Response<GoodReadsBookObject>) {
                context.pbar.visibility = View.GONE

                context.progressBar.visibility = View.GONE

                val resource = response.body()
                val text = resource?.book
                val total = text?.title

                context.descriptionText.text = Html.fromHtml(text?.description, Html.FROM_HTML_MODE_LEGACY)
                //handle other events if clicked in main page and redirected to verdict
                if(scannedId.isNotEmpty()){
                    val googleBookResult =
                        GoogleBookResult(
                            context = context,
                            context3 = context3
                        )
                    googleBookResult.scannedString = text?.isbn13.toString()
                    googleBookResult.createGoogle()


                    //Other Reviews
                    val sharedPref = SharedPrefCommon(context)
                    val otherReviewsResult = OtherReviewsResult(
                        context = context,
                        context3 = context3
                    )
                    otherReviewsResult.scannedString = text?.isbn13.toString()
                    otherReviewsResult.createOtherReviews(sharedPref.getPrefString("token", "").toString())
                }

                averageRating = text?.average_rating.toString()
                if(averageRating.length > 2)
                    averageRating.trimEnd()
                Toast.makeText(context, total, Toast.LENGTH_LONG).show()
                //parse values

                var a = 0.0
                try{
                     a = averageRating.toDouble()
                } catch(ex : NumberFormatException){ // handle your exception

                }
                //startPieChart(averageRating, a)

                if(text?.title == null || text.title == ""){
                    context.callCompletion1 = false
                    context.fin.visibility = View.GONE
                    context.animation_view2.visibility = View.GONE
                    AlertDialog.Builder(context)
                        .setTitle("Book Not Found!")
                        .setMessage("Please Scan the ISBN code again")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Re-Scan", DialogInterface.OnClickListener { dialog, which ->
                            context.startActivity(Intent(context, ScanActivity::class.java))
                            context.finish()
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Take-Lite", null)
                        .setIcon(R.drawable.books)
                        .show()
                } else {
                    context.callCompletion1 = true
                    context.fin.visibility = View.GONE
                    context.animation_view2.visibility = View.GONE
                    context.bookCard.visibility = View.VISIBLE
                    context.line.visibility = View.VISIBLE
                    YoYo.with(Techniques.FadeIn)
                        .duration(400)
                        .playOn(context.bookCard)

                    context.animation_view.visibility = View.VISIBLE
                    context.gen.visibility = View.VISIBLE
                }

                context.bookName.text = text?.title

                var authors = ""
                val size = text?.authors?.author?.size
                Log.d("HEY", text?.authors.toString())
                if(size != null) {
                    for (i in 0 until size) {
                        val author = text.authors!!.author!![i]

                        if (i < size-1)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                authors += author.name.toString() +Html.fromHtml("<br>", Html.FROM_HTML_MODE_LEGACY)
                            } else {
                                TODO("VERSION.SDK_INT < N")
                            }
                        else
                            authors += author.name.toString()
                    }
                }
                context.bookAuthor.text = authors

                if(text?.publisher != null)
                context.bookPublisher.text = text.publisher
                else
                context.bookPublisher.text = "No Data"

                context.avRate.text = averageRating
                additionalAddOns(a)

                val ratings = text?.work?.ratings_count

                var ah = 0
                try{
                    ah = Integer.parseInt(ratings)
                } catch(ex : NumberFormatException){ // handle your exception

                }

                context.listRatings.add(a)
                context.listNumberRatings.add(ah)

                context.reviewCount.text = if(ah == 1) ("$ah Review") else ("$ah Reviews")
                context.bookContainer.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<GoodReadsBookObject>, t: Throwable) {
                Log.e("Hello", t.toString())

                //call.cancel()
                Toast.makeText(context3, "Duh ! :(", Toast.LENGTH_LONG).show()

            }
        })

    }
    private fun additionalAddOns(value : Double){
        if(value > 4 && value <= 5){
            context.bookContainer.setCardBackgroundColor(Color.parseColor("#78C8A1"))
        }
        if(value > 3 && value <= 4){
            context.bookContainer.setCardBackgroundColor(Color.parseColor("#AED787"))
        }
        if(value > 2 && value <= 3){
            context.bookContainer.setCardBackgroundColor(Color.parseColor("#FFD834"))
        }
        if(value > 1 && value <= 2){
            context.bookContainer.setCardBackgroundColor(Color.parseColor("#FFB234"))
        }
        if(value >= 0 && value <= 1){
            context.bookContainer.setCardBackgroundColor(Color.parseColor("#FF8B5A"))
        }
    }
}
