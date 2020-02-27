package com.example.ibr_instantbookreviews.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import com.example.ibr_instantbookreviews.Utilities.SignUpHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPref : SharedPrefCommon
    lateinit var intentMain : Intent
    lateinit var signUpHandler: SignUpHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedPref = SharedPrefCommon(this)
        signUpHandler = SignUpHandler(this, sharedPref)

        scanbar.setOnClickListener { v ->
            val intent = Intent(this, ScanActivity::class.java)
            intentMain = intent
            signUpHandler.handleSign(intentMain)
        }
        login.setOnClickListener { v ->
            val intent = Intent(this, LoggedInMainActivity::class.java)
            intentMain = intent
            signUpHandler.handleSign(intentMain)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 42) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            signUpHandler.handleSignInResult(task, intentMain)
        }
    }
}
