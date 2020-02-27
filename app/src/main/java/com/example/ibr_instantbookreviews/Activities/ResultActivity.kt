package com.example.ibr_instantbookreviews.Activities

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.ibr_instantbookreviews.GoodReadsReviews.GoodReadsResult
import com.example.ibr_instantbookreviews.GoogleBooksReviews.GoogleBookResult
import com.example.ibr_instantbookreviews.OtherReviews.OtherReviewsResult
import com.example.ibr_instantbookreviews.OtherReviews.OtherReviewsViewModel
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.ResultActivityFragments.DescriptionFragment
import com.example.ibr_instantbookreviews.ResultActivityFragments.FragmentManagerResults
import com.example.ibr_instantbookreviews.ResultActivityFragments.PricingFragment
import com.example.ibr_instantbookreviews.ResultActivityFragments.ReviewsFragment
import com.example.ibr_instantbookreviews.Utilities.CreatePieChart
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import kotlinx.android.synthetic.main.activity_result_duplicate.*

class ResultActivity : AppCompatActivity() {
    lateinit var otherReviewsResult : OtherReviewsResult
    var listRatings = ArrayList<Double>()
    var listNumberRatings = ArrayList<Int>()

    var callCompletion1 : Boolean = false
    var callCompletion2 : Boolean = false
    var callCompletion3 : Boolean = false

    private lateinit var fragmentManager: FragmentManagerResults

    //var fragment1: Fragment = RatingFragment()
    val fragment2: Fragment = ReviewsFragment()
    val fragment3: Fragment = PricingFragment()
    val fragment4: Fragment = DescriptionFragment()
    lateinit var viewPagerAdapter : FragmentPagerAdapter

    lateinit var otherReviewsViewModel : OtherReviewsViewModel
    lateinit var sharedPref : SharedPrefCommon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_duplicate)
        otherReviewsViewModel = ViewModelProviders.of(this).get(OtherReviewsViewModel::class.java)


        nestedScroll.setOnTouchListener { _, _ -> true }
        animation_view2.playAnimation()

        backArrow.setOnClickListener {
            finish()
        }


        var sharedPref = SharedPrefCommon(this)

        var scannedString = sharedPref.getPrefString("is_bn","").toString()
        val scannedId = sharedPref.getPrefString("id","").toString()


        if(scannedId == "0"){
            //Google Books

            //Goodreads
            val goodReadsResult = GoodReadsResult(
                context = this,
                context3 = applicationContext
            )
            goodReadsResult.scannedString = scannedString
            goodReadsResult.createGoodReads()


            val googleBookResult = GoogleBookResult(
                context = this,
                context3 = applicationContext
            )
            googleBookResult.scannedString = scannedString
            googleBookResult.createGoogle()


            //Other Reviews
            var otherReviewsResult = OtherReviewsResult(
                context = this,
                context3 = applicationContext
            )
            otherReviewsResult.scannedString = scannedString
            otherReviewsResult.createOtherReviews(sharedPref.getPrefString("token", "").toString())
        } else {

            //Goodreads
            val goodReadsResult = GoodReadsResult(
                context = this,
                context3 = applicationContext
            )
            goodReadsResult.scannedId = scannedId
            goodReadsResult.createGoodReads()
        }


        setupViewPager(viewpager)
        setSupportActionBar(toolbarMain)
        finalRun()
    }

    private fun finalRatingCalculation(lR : ArrayList<Double>, lNR : ArrayList<Int>): Double{
        var rat = 0.0
        var ratN = 0

        for(i in 0 until lR.size){
            rat = rat.plus(lR[i] * lNR[i])
            ratN += lNR[i]
        }

        return Math.round(rat/ratN*100.00)/100.00
    }
    private fun setupViewPager(viewPager: ViewPager) {

        //fragmentManager = FragmentManagerResults(supportFragmentManager, fragment1, fragment2, fragment3, fragment4)
        //viewPager.adapter = fragmentManager
    }

    private fun finalRun(){
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if(callCompletion1){
                    animation_view.playAnimation()
                }

                if(callCompletion1 && callCompletion2 && callCompletion3){
                    nestedScroll.setOnTouchListener(null)
                    animation_view.visibility = View.GONE
                    gen.visibility = View.GONE
                    animation_view.pauseAnimation()

                    verdictCard.visibility = View.VISIBLE
                    YoYo.with(Techniques.SlideInUp)
                        .duration(400)
                        .playOn(verdictCard)

                    ratingCard.visibility = View.VISIBLE
                    YoYo.with(Techniques.SlideInUp)
                        .duration(700)
                        .playOn(ratingCard)

                    descriptionCard.visibility = View.VISIBLE
                    YoYo.with(Techniques.SlideInUp)
                        .duration(700)
                        .playOn(descriptionCard)

                    val a = finalRatingCalculation(listRatings, listNumberRatings)
                    finalRating.text = a.toString()
                    ratingColor(a.toFloat())
                    val createPieChart  =
                        CreatePieChart(this@ResultActivity, pieChartGod)
                    createPieChart.startPieChart(listNumberRatings, a.toString(), a, listNumberRatings.size)
                    callCompletion1 = false
                }
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun ratingColor(value:Float){
        if(value > 4.5 && value <= 5){
            finalRating.setTextColor(Color.parseColor("#42725A"))
            recommendation.text = "A MUST READ! :)"
        }
        if(value > 4 && value <= 4.5){
            finalRating.setTextColor(Color.parseColor("#78C8A1"))
            recommendation.text = "RECOMMENDED! :)"
        }
        if(value > 3.5 && value <= 4){
            finalRating.setTextColor(Color.parseColor("#AED787"))
            recommendation.text = "GOOD BOOK! :)"
        }
        if(value > 2 && value <= 3){
            finalRating.setTextColor(Color.parseColor("#FFD834"))
            recommendation.text = "BETTER TO AVOID :("
        }
        if(value > 1 && value <= 2){
            finalRating.setTextColor(Color.parseColor("#FFB234"))
            recommendation.text = "PLEASE AVOID :("
        }
        if(value >= 0 && value <= 1){
            finalRating.setTextColor(Color.parseColor("#FF8B5A"))
            recommendation.text = "ABSOLUTE GARBAGE :("
        }
    }


}
