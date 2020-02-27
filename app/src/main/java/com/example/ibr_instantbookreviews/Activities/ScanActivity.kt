package com.example.ibr_instantbookreviews.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibr_instantbookreviews.R
import com.example.ibr_instantbookreviews.Utilities.SharedPrefCommon
import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zbar.BarcodeFormat
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScanActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    override fun handleResult(p0: Result?) {
        Toast.makeText(
            this, "Contents = " + p0?.contents +
                ", Format = " + p0?.barcodeFormat.toString(), Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(applicationContext, ResultActivity::class.java)
        val sharedPref = SharedPrefCommon(this)
        sharedPref.putPrefString("is_bn", p0?.contents.toString())
        sharedPref.putPrefString("id", "0")

        if(p0?.contents.toString().length < 13){
            Toast.makeText(
                this, "Unable to process correct ISBN, align camera correctly"+p0?.contents.toString().length, Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(applicationContext, ScanActivity::class.java))
            finish()
        } else {
            Toast.makeText(
                this, ""+p0?.contents.toString(), Toast.LENGTH_SHORT
            ).show()
            startActivity(intent)
        }

    }
    private var mScannerView: ZBarScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        mScannerView = ZBarScannerView(this)   // Programmatically initialize the scanner view

        contentframe.addView(mScannerView)
        val a  = ArrayList<BarcodeFormat>()
        a.add(BarcodeFormat.EAN13)
        mScannerView?.setFormats(a)
        supportActionBar?.hide()
    }
    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

}
