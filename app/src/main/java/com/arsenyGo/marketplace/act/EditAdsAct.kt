package com.arsenyGo.marketplace.act

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ArsenyGo.marketplace.R
import com.ArsenyGo.marketplace.databinding.ActivityEditAdsBinding
import com.arsenyGo.marketplace.adapters.ImageAdapter
import com.arsenyGo.marketplace.database.DbManager
import com.arsenyGo.marketplace.dialogs.DialogSpHelper
import com.arsenyGo.marketplace.fragment.FragmentCloseInterface
import com.arsenyGo.marketplace.fragment.FragmentImageList
import com.arsenyGo.marketplace.fragment.SelectImageItem
import com.arsenyGo.marketplace.utils.CityHelper
import com.arsenyGo.marketplace.utils.ImagePicker
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil

class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {
    private lateinit var imageAdapter: ImageAdapter
    lateinit var rootElem: ActivityEditAdsBinding
    private var dialog = DialogSpHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElem = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElem.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_IMAGE) {
            if (data != null) {
                val returnVal = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                if (returnVal?.size!! > 1) {
                    rootElem.linLayout.visibility = View.GONE
                    val fm = supportFragmentManager.beginTransaction()
                    fm.replace(R.id.placeHolder, FragmentImageList(this, returnVal))
                    fm.commit()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    ImagePicker.getImages(this, 7)
                else {
                    Toast.makeText(
                        this,
                        "Предоставьте разрешение чтобы рзместить изображение",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun init() {
        imageAdapter = ImageAdapter()
        rootElem.vpImage.adapter = imageAdapter
    }

    fun onClickSelectCountry(v: View) {
        val countryList = CityHelper.getCountries(this)
        dialog.showSpDialog(this, countryList, rootElem.tvCountry)
        if (rootElem.tvCity.text.toString() != getString(R.string.select_city))
            rootElem.tvCity.text = getString(R.string.select_city)
    }

    fun onClickSelectCity(v: View) {
        val country = rootElem.tvCountry.text.toString()
        if (country == getString(R.string.select_country)) {
            Toast.makeText(this, "Страна не выбрана", Toast.LENGTH_LONG).show()
            return
        }
        val cityList = CityHelper.getCities(country, this)
        dialog.showSpDialog(this, cityList, rootElem.tvCountry)
    }

    fun onClickGetImages(v: View) {
        val dbManager = DbManager()
        dbManager.publishAd()
    }

    fun onClickPublish(v: View) {
        ImagePicker.getImages(this, 7)
    }

    override fun onFragmentClose(list: ArrayList<SelectImageItem>) {
        rootElem.linLayout.visibility = View.VISIBLE
        imageAdapter.update(list)
    }
}