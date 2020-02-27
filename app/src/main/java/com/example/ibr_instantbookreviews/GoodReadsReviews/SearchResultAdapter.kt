package com.example.ibr_instantbookreviews.GoodReadsReviews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Activities.ResultActivity
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import com.example.ibr_instantbookreviews.Utilities.SignUpHandler
import kotlinx.android.synthetic.main.book_result_item.view.*

class SearchResultAdapter(
    private val intent: Intent,
    private val context : Context,
    private val activity : AppCompatActivity,
    private val works: ArrayList<WorkResults>
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = works.get(position)
        holder.name.text = item.best_book?.title
        holder.author.text = item.best_book?.author?.name
        Glide
            .with(holder.mView)
            .load(item.best_book?.image_url)
            .into(holder.image)

        YoYo.with(Techniques.FadeInUp)
            .duration(250)
            .playOn(holder.mView)

        holder.mView.setOnClickListener {
            val intent2 = Intent(context, ResultActivity::class.java)

            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val sharedPref = SharedPrefCommon(activity)
            val signUpHandler = SignUpHandler(activity, sharedPref)
            signUpHandler.handleSign(intent)

            sharedPref.putPrefString("is_bn", "0")
            sharedPref.putPrefString("id", item.best_book?.id.toString())
            context.startActivity(intent2)
        }

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = works.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val name: TextView = mView.name
        val author: TextView = mView.author
        val image: ImageView = mView.imageBook

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

}
