package com.example.quickmeals

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    internal val UPI_PAYMENT = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val amnt = findViewById<EditText>(R.id.editTextNumberSigned)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        idTextId.text = currentUser?.uid
        nameTextId.text = currentUser?.displayName
        emailTextId.text = currentUser?.email
        signOBtn.setOnClickListener {
            mAuth.signOut()
            //updateUI(null)
               val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
        }

        val arb = findViewById<Button>(R.id.adRestBtn)
        arb.setOnClickListener {
            val addrest = Intent(this, AddRestaurant::class.java)
            startActivity(addrest)
            finish()
        }
        //initializeViews()

        payBtn.setOnClickListener {
            //Getting the values from the EditTexts
            val amount = amnt.text.toString()
            val note = "Payment Test"
            val name = currentUser?.displayName.toString()
            val upiId = "7985209087@upi"
            payUsingUpi(amount, upiId, name, note)
        }
    }

    /*internal fun initializeViews() {
        val amountEt = "1".toString()
        val noteEt = "Test".toString()
        val nameEt = nameTextId.text.toString()
        val upiIdEt = "7985209087@upi".toString()
    }*/

    fun payUsingUpi(amount: String, upiId: String, name: String, note: String) {

        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(this@DashboardActivity, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt: String = data.getStringExtra("response").toString()
                    Log.d("UPI", "onActivityResult: $trxt")
                    val dataList = ArrayList<String>()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null") //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@DashboardActivity)) {
            var str: String? = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase(Locale.ROOT) == "Status".toLowerCase(Locale.ROOT)) {
                        status = equalStr[1].toLowerCase(Locale.ROOT)
                    } else if (equalStr[0].toLowerCase(Locale.ROOT) == "ApprovalRefNo".toLowerCase(
                            Locale.ROOT
                        ) || equalStr[0].toLowerCase(Locale.ROOT) == "txnRef".toLowerCase(Locale.ROOT)
                    ) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this@DashboardActivity, "Transaction successful.", Toast.LENGTH_SHORT).show()
                Log.d("UPI", "responseStr: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this@DashboardActivity, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@DashboardActivity, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@DashboardActivity, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable) {
                return true
            }
            return false
        }
    }
}