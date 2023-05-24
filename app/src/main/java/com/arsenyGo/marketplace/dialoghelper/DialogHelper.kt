package com.arsenyGo.marketplace.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.ArsenyGo.marketplace.R
import com.ArsenyGo.marketplace.databinding.SignDialogBinding
import com.arsenyGo.marketplace.MainActivity
import com.arsenyGo.marketplace.acchelper.AccountHelper

class DialogHelper(act: MainActivity) {
    private val act = act
    val accHelper = AccountHelper(act)

    fun createSignDialog(idx: Int) {
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        val builder = AlertDialog.Builder(act)
        builder.setView(view)
        setDialogState(idx, rootDialogElement)

        val dialog = builder.create()
        rootDialogElement.btSignInUp.setOnClickListener {
            setOnClickSign(idx, rootDialogElement, dialog)
        }
        rootDialogElement.btForgetPassword.setOnClickListener {
            setOnClickResetPass(rootDialogElement, dialog)
        }
        rootDialogElement.btGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setOnClickResetPass(rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
        if (rootDialogElement.etSignEmail.text.isNotEmpty()) {
            act.myAuth.sendPasswordResetEmail(rootDialogElement.etSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(act, R.string.reset_email, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSign(
        idx: Int,
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        dialog?.dismiss()
        if (idx == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                rootDialogElement.etSignEmail.text.toString(),
                rootDialogElement.etSignPassword.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                rootDialogElement.etSignEmail.text.toString(),
                rootDialogElement.etSignPassword.text.toString()
            )
        }
    }

    private fun setDialogState(idx: Int, rootDialogElement: SignDialogBinding) {
        if (idx == DialogConst.SIGN_UP_STATE) {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.sign_up_act)
        } else {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.sign_in_act)
            rootDialogElement.btForgetPassword.visibility = View.VISIBLE
        }
    }
}

