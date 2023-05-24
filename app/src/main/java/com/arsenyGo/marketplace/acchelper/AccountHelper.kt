package com.arsenyGo.marketplace.acchelper

import android.widget.Toast
import com.ArsenyGo.marketplace.R
import com.arsenyGo.marketplace.MainActivity
import com.arsenyGo.marketplace.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(act: MainActivity) {
    private val act = act
    val signInRequestCode = 792
    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        act.uiUpdate(task.result?.user)
                    } else {
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            linkEmailToGoogle(email, password)
                        }
                        Toast.makeText(
                            act,
                            act.resources.getString(R.string.sign_up_err),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    act.uiUpdate(task.result?.user)
                } else {
                    Toast.makeText(
                        act,
                        act.resources.getString(R.string.sign_in_err),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_ver_err),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_ver_done),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun signOutGoogle() {
        getSignInClient().signOut()
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        act.myAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(act, "Регистрация завершена", Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            }
        }
    }

    private fun linkEmailToGoogle(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        act.myAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(act, act.resources.getString(R.string.link_done), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
        return GoogleSignIn.getClient(act, gso)
    }
}