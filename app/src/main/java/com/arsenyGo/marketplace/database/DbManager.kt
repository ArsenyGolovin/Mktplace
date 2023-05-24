package com.arsenyGo.marketplace.database

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager {
    val db = Firebase.database.reference
    val auth = Firebase.auth

    fun publishAd() {
        if (auth.uid != null) {}
    }
}