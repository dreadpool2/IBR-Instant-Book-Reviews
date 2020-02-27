package com.example.ibr_instantbookreviews.Utilities

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignUpHandler(var activity: Activity, var sharedPref : SharedPrefCommon){

    var mGoogleSignInClient : GoogleSignInClient? = null
    var mainAccount : Account? = null

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.getSignInIntent();
        activity.startActivityForResult(signInIntent, 42)
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private inner class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            val bundle: Bundle = result.getResult()

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            val token: String = bundle.getString(AccountManager.KEY_AUTHTOKEN)
            sharedPref.putPrefString("token", token)
        }

    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, intentMain: Intent) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            mainAccount = account?.account
            val am: AccountManager = AccountManager.get(activity)
            val options = Bundle()

            am.getAuthToken(
                mainAccount,                     // Account retrieved using getAccountsByType()
                "oauth2:https://www.googleapis.com/auth/spreadsheets",            // Auth scope
                options,                        // Authenticator-specific options
                activity,                           // Your activity
                OnTokenAcquired(),              // Callback called when a token is successfully acquired
                Handler()              // Callback called if an error occurs
            )

            sharedPref.putPrefString("nameLoggedIn", account?.displayName.toString())
            sharedPref.putPrefString("emailLoggedIn", account?.email.toString())
            sharedPref.putPrefString("uriLoggedIn", account?.photoUrl.toString())

            activity.startActivity(intentMain)
            activity.overridePendingTransition(android.R.anim.slide_in_left,  android.R.anim.slide_in_left); // remember to put it after startActivity, if you put it to above, animation will not working
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error Sign", "signInResult:failed code=" + e.statusCode)
        }

    }

    private fun googleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        mGoogleSignInClient?.signOut()

        signIn()
    }

    fun handleSign(intentMain: Intent){
        val current  = System.currentTimeMillis()
        var timeChanged : Long = 0
        val lastTime = sharedPref.getPrefLong("lasttime", 0)

        if(lastTime.toString() == "0"){
            sharedPref.putPrefLong("lasttime", current)
            googleSignIn()
            Toast.makeText(activity, "Please Log-In", Toast.LENGTH_LONG).show()
        } else {
            timeChanged = current - lastTime
            timeChanged/=60000
            if(timeChanged >= 60){
                googleSignIn()
                Toast.makeText(activity, "Please Log-In", Toast.LENGTH_LONG).show()
                sharedPref.putPrefLong("lasttime", current)
            } else {
                activity.startActivity(intentMain)
            }
        }
    }

}
