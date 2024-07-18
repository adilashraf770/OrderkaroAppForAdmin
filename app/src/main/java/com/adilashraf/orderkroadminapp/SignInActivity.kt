@file:Suppress("DEPRECATION")

package com.adilashraf.orderkroadminapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivitySignInBinding
import com.adilashraf.orderkroadminapp.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {
    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_CODE: Int = 10
//    private val EMAIL: String = "email"
    //    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initializing Auth
        auth = Firebase.auth
        // Initializing Databasse
        databaseRef = Firebase.database.reference
        // Setting Permission
//        binding.btnFacebook.setReadPermissions(EMAIL, "public_profile")
//        callbackManager = CallbackManager.Factory.create()
        // Setting Google Option
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("309899331843-b4audocqk2m0suo1mhjejbee0haa4101.apps.googleusercontent.com")
                .requestEmail()
                .build()

        // Initializing Google Sign In
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.btnGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, REQ_CODE)
        }

        // Initialize Facebook Login button
//        binding.btnFacebook.registerCallback(
//            callbackManager,
//            object : FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult) {
////                    Log.d(TAG, "facebook:onSuccess:$loginResult")
//                    handleFacebookAccessToken(result.accessToken)
//                }
//
//                override fun onCancel() {
////                    Log.d(TAG, "facebook:onCancel")
//                }
//
//                override fun onError(error: FacebookException) {
////                    Log.d(TAG, "facebook:onError", error)
//                }
//            },)

        binding.btnSignIn.setOnClickListener {
            email = binding.editEmail.text.toString().trim()
            password = binding.editPassword.text.toString().trim()

            if (isValidateEmail(email) || isValidatePassword(password)) {
                Toast.makeText(this, "Please Fill All Details Correctly", Toast.LENGTH_SHORT).show()
            } else {
                signInWithUser(email, password)
            }
        }


        binding.dontHaveAccount.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }


    }

//    private fun handleFacebookAccessToken(accessToken: AccessToken) {
//        val credential = FacebookAuthProvider.getCredential(accessToken.token)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext,
//                        "User is Signed in Successfully ",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    updateUI(null)
//                }
//            }
//    }

    // Google Sign IN
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //  Google Code
        googleSignIn(requestCode, data)

        // Facebook Code
//        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // Google signin CODe
    private fun googleSignIn(requestCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount = task.result
                val personName = account.displayName
                val personEmail = account.email
                val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credentials).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User is Signed in Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val user = UserModel(personName, null, personEmail, null, null, null)
                        try {
                            val uid = auth.currentUser!!.uid
                            databaseRef.child("admin").child(uid).setValue(user)

                        } catch (_: Error) {
                        }
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this, "Failed to Sign in", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    // Creating user Function
    private fun signInWithUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User is Logged in Successfully", Toast.LENGTH_SHORT)
                        .show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Please Fill All Details Correctly", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    //Email Validation
    private fun isValidateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(Regex(emailPattern))
    }

    //Password Validation
    private fun isValidatePassword(password: String): Boolean {
        // Password criteria: at least 8 characters, including upper, lower, digit, special
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=-]).{8,}\$"
        return password.matches(Regex(passwordPattern))
    }


}