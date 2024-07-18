@file:Suppress("DEPRECATION")

package com.adilashraf.orderkroadminapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivitySignUpBinding
import com.adilashraf.orderkroadminapp.model.UserModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var nameOfRestaurant: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_CODE: Int = 10
//    private val EMAIL: String = "email"
//    private lateinit var callbackManager: CallbackManager
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initializing Auth
        auth = Firebase.auth
        // Initializing Auth
        val user = auth.currentUser?:""
        // Setting facebook Permissions
//        binding.btnFacebook.setReadPermissions(EMAIL, "public_profile")
//        callbackManager = CallbackManager.Factory.create()

        // Initializing Database
        databaseRef = Firebase.database.getReference("admin")
        // Setting Google Option
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Initializing Google Sign In
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.btnSignUp.setOnClickListener {
            name = binding.editName.text.toString().trim()
            nameOfRestaurant = binding.editNameOfRestaurant.text.toString().trim()
            email = binding.editEmail.text.toString().trim()
            password = binding.editPassword.text.toString().trim()

            if (name.isBlank() || nameOfRestaurant.isBlank() || isValidateEmail(email) || isValidatePassword(password)) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            } else {
                createUser(email, password)
            }
        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, REQ_CODE)

        }

        // Initialize Facebook Login button
//        binding.btnFacebook.registerCallback(
//            callbackManager,
//            object : FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult) {
//                     handleFacebookAccessToken(result.accessToken)
//                }
//                override fun onCancel() {
////                    Log.d(TAG, "facebook:onCancel")
//                }
//                override fun onError(error: FacebookException) {
////                    Log.d(TAG, "facebook:onError", error)
//                }
//            },)


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


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount = task.result
                val personName = account.displayName
                val personEmail = account.email

                val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credentials).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = UserModel(personName, null, personEmail, null, null, null)
                        try {
                            val uid = auth.currentUser!!.uid
                            databaseRef.child(uid).setValue(user)
                        } catch (_: Error) {}

                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)


                    } else {
                        Toast.makeText(this, "Failed to Sign in", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    // Creating User
    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User is SignUp Successfully", Toast.LENGTH_SHORT).show()
                    saveData()
                    val user = auth.currentUser
                    updateUI(user)
                    finish()
                } else {
                    Toast.makeText(this, "User Creation Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //     Saving Details of User in Database
    private fun saveData() {
        name = binding.editName.text.toString().trim()
        nameOfRestaurant = binding.editNameOfRestaurant.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        val uid = auth.currentUser!!.uid
        val userModel = UserModel(name, nameOfRestaurant, email, password, null, null )
        try {
            databaseRef.child(uid).setValue(userModel)
        } catch (_: Error) {}

    }

    private fun updateUI(user: FirebaseUser?) {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
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