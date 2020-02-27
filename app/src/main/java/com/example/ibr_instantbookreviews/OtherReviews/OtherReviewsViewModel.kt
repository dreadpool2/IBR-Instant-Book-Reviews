package com.example.ibr_instantbookreviews.OtherReviews

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ibr_instantbookreviews.Utilities.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherReviewsViewModel : ViewModel() {
    var progressVisibility = MutableLiveData<Int>()
    var progressBarVisibility = MutableLiveData<Int>()

    var textFlip = MutableLiveData<ArrayList<String>>()
    var textLib = MutableLiveData<ArrayList<String>>()
    var textWal = MutableLiveData<ArrayList<String>>()

    var failure = MutableLiveData<String>()

    var contextCallCompletion = MutableLiveData<Boolean>()
    var scannedString = ""

    lateinit var apiInterface: ApiInterface

    fun createOtherReviews(token: String) {

        apiInterface = APIClientOtherReviews.client.create(ApiInterface::class.java)

        //get isbn from scanner activity

        val isbn_number = scannedString


        val arrlist = ArrayList<ArrayList<String>>()
        val arrlist2 = ArrayList<String>()

        arrlist2.add("ISBN")
        arrlist2.add(isbn_number)
        arrlist.add(arrlist2)

        val body = upDateObject("Sheet1!A1:D5", "ROWS", arrlist)

        val call = apiInterface.updateCell(token, body)
        call.enqueue(object : Callback<updateCellObject> {
            override fun onResponse(call: Call<updateCellObject>, response: Response<updateCellObject>) {
                progressVisibility.value = View.GONE

                //fetch Reviews
                val call2 = apiInterface.getJsonOtherReviews()
                call2.enqueue(object : Callback<OtherReviewsObject> {
                    override fun onResponse(call: Call<OtherReviewsObject>, response: Response<OtherReviewsObject>) {
                        contextCallCompletion.value = true
                        progressBarVisibility.value= View.GONE

                        val resource = response.body()
                        val text = resource?.values

                        textFlip.value = text?.get(0)
                        textLib.value = text?.get(1)
                        textWal.value = text?.get(2)
                    }

                    override fun onFailure(call: Call<OtherReviewsObject>, t: Throwable) {
                        Log.e("Hello", t.toString())
                        failure.value = "Failed!"
                    }
                })

            }
            override fun onFailure(call: Call<updateCellObject>, t: Throwable) {
                Log.e("Hello", t.toString())

                //call.cancel()
                failure.value = "Failed!"
            }
        })

    }
}
