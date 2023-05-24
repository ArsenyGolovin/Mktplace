package com.arsenyGo.marketplace.utils

import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix


object ImagePicker {
    const val REQUEST_CODE_GET_IMAGE = 2345
    fun getImages(context: AppCompatActivity, imageNum: Int) {
        val options =
            Options.init().setRequestCode(REQUEST_CODE_GET_IMAGE)
                .setCount(imageNum)
                .setFrontfacing(false)
                .setMode(Options.Mode.Picture)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/pix/images")
        Pix.start(context, options)
    }
}