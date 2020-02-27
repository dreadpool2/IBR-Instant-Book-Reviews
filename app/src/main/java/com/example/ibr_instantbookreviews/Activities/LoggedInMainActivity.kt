package com.example.ibr_instantbookreviews.Activities

import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.ibr_instantbookreviews.GoodReadsReviews.APIClientGoodreads
import com.example.ibr_instantbookreviews.GoodReadsReviews.GoodReadsSearchObject
import com.example.ibr_instantbookreviews.GoodReadsReviews.SearchResultAdapter
import com.example.ibr_instantbookreviews.GoodReadsReviews.WorkResults
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Utilities.ApiInterface
import com.example.ibr_instantbookreviews.Utilities.BaseAnimation
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import com.example.ibr_instantbookreviews.Utilities.SignUpHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.book_result_list.*
import kotlinx.android.synthetic.main.content_main2.*
import kotlinx.android.synthetic.main.nav_header_main2.view.*
import kotlinx.android.synthetic.main.navigation_drawer_menu.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoggedInMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {
    lateinit var apiInterface : ApiInterface
    var arrayList = ArrayList<WorkResults>()
    var width = 0

    override fun onButtonClicked(buttonCode: Int) {
        Log.d("Hello", "Hello")
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        if(enabled) {
            width = searchBar.layoutParams.width
            searchBar.layoutParams.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            linehead.visibility = View.VISIBLE
            handleVisbility(false)
        }
        else {
            handleVisbility(true)
            linehead.visibility = View.GONE
            if(arrayList.isNotEmpty()){
                arrayList.clear()
            }
            searchBar.layoutParams.width = width
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        handleVisbility(false)
        val anim = BaseAnimation()
        anim.animatorStart(Techniques.FadeIn, 200, searchVis, View.VISIBLE)
        anim.animatorEnd(Techniques.FadeOut, 200, noResults, View.GONE)
        heading.setText("\" ${text} \"")

        val call = apiInterface.getSearchedBooks(text.toString(), "1")
        call.enqueue(object : Callback<GoodReadsSearchObject> {
            override fun onResponse(call: Call<GoodReadsSearchObject>, response: Response<GoodReadsSearchObject>) {
                anim.animatorEnd(Techniques.FadeOut, 200, searchVis, View.GONE)
                val searchR = response.body()
                val results = searchR?.search?.results?.works
                arrayList = results as ArrayList<WorkResults>

                if(arrayList.isEmpty()){
                    anim.animatorStart(Techniques.FadeIn, 200, noResults, View.VISIBLE)
                }

                listBooks.adapter = SearchResultAdapter(
                    intent,
                    applicationContext,
                    this@LoggedInMainActivity,
                    arrayList
                )
                val spannable =  SpannableString("Top "+arrayList.size+" Results for "+text)


                //find number of digits
                var i = arrayList.size
                var j = 0
                while(i > 0){
                    i/= 10
                    j++
                }
                spannable.setSpan( ForegroundColorSpan(Color.parseColor("#000000")), 17 + j, 17+ j + text.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                topHeader.setText(spannable, TextView.BufferType.SPANNABLE)
                includedMaterial.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<GoodReadsSearchObject>, t: Throwable) {
                Log.e("LoggedInMainActivity", t.toString())
                anim.animatorEnd(Techniques.FadeOut, 200, searchVis, View.GONE)
                anim.animatorStart(Techniques.FadeIn, 200, noResults, View.VISIBLE)
            }
        })
    }

    lateinit var signUpHandler : SignUpHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        apiInterface = APIClientGoodreads.client.create(ApiInterface::class.java)

        val a =  LinearLayoutManager(this@LoggedInMainActivity)
        a.orientation = RecyclerView.VERTICAL
        listBooks.layoutManager = a


        searchBar.setOnSearchActionListener(this)

        listBooks.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                Log.d("Hey","scrollx"+scrollX+"scrolly"+scrollY)
            }
        })

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        var sharedPrefCom = SharedPrefCommon(this)
        signUpHandler = SignUpHandler(this, sharedPrefCom)

        var personName = sharedPrefCom.getPrefString("nameLoggedIn", "")?.replaceAfter(" ","")
        var email = sharedPrefCom.getPrefString("emailLoggedIn", "")
        var personPhoto = sharedPrefCom.getPrefString("uriLoggedIn", "")

        navView.name.text = "Hello ${personName}!"

        Glide
            .with(this)
            .load(Uri.parse(personPhoto))
            .centerCrop()
            .into(navView.imageView)

        navView.imageView.setImageURI(Uri.parse(personPhoto))

        navView.logout.setOnClickListener {
            sharedPrefCom.putPrefString("token", "")
            sharedPrefCom.putPrefString("emailLoggedIn", "")
            sharedPrefCom.putPrefString("uriLoggedIn", "")
            sharedPrefCom.putPrefString("nameLoggedIn", "")
            sharedPrefCom.putPrefLong("lasttime", 0)
            signUpHandler.mGoogleSignInClient?.signOut()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        hamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            YoYo.with(Techniques.Bounce)
                .duration(100)
                .playOn(it)
        }

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun handleVisbility(boolean: Boolean){

        val anim = BaseAnimation()
        if(!boolean) {
            anim.animatorEnd(Techniques.SlideOutUp,300,mainText, View.GONE)
            anim.animatorEnd(Techniques.SlideOutUp,300,nBooks, View.GONE)
            anim.animatorEnd(Techniques.SlideOutUp,300,dashboard, View.GONE)
            anim.animatorEnd(Techniques.FadeOut,200,hamburger, View.GONE)
            anim.animatorEnd(Techniques.FadeOut,200, dashId, View.GONE )

            //anim.animatorStart(Techniques.FadeIn,200,heading, View.VISIBLE)
            anim.animatorStart(Techniques.FadeInUp,200,listBooks, View.VISIBLE)
        } else {
            //anim.animatorEnd(Techniques.FadeOut,200,heading, View.GONE)
            anim.animatorEnd(Techniques.FadeOutDown,200,listBooks, View.GONE)
            anim.animatorEnd(Techniques.FadeOut,200,searchVis, View.GONE)
            anim.animatorEnd(Techniques.FadeOut, 200, noResults, View.GONE)


            anim.animatorStart(Techniques.FadeIn,300,mainText, View.VISIBLE)
            anim.animatorStart(Techniques.FadeIn,300,dashboard, View.VISIBLE)
            anim.animatorStart(Techniques.FadeIn,300,nBooks, View.VISIBLE)
            anim.animatorStart(Techniques.Tada,200,hamburger, View.VISIBLE)
            anim.animatorStart(Techniques.FadeIn,300, dashId, View.VISIBLE )
            includedMaterial.visibility = View.GONE
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 42) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            signUpHandler.handleSignInResult(task, intent)
        }
    }
}
