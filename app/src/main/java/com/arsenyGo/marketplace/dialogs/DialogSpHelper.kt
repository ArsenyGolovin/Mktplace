package com.arsenyGo.marketplace.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ArsenyGo.marketplace.R
import com.arsenyGo.marketplace.utils.CityHelper

class DialogSpHelper {
    fun showSpDialog(context: Context, list: ArrayList<String>, tvSelection: TextView) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val rootView = LayoutInflater.from(context).inflate(R.layout.sp_layout, null)
        val adapter = RvDialogSpAdapter(tvSelection, dialog)
        val rcView = rootView.findViewById<RecyclerView>(R.id.rcSpView)
        val sv = rootView.findViewById<SearchView>(R.id.svSpinner)
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
        dialog.setView(rootView)
        adapter.updateAdapter(list)
        setSearchViewListener(adapter, list, sv)
        dialog.show()
    }

    private fun setSearchViewListener(adapter: RvDialogSpAdapter, list: ArrayList<String>, sv: SearchView?) {
        sv?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tmpList = CityHelper.filterData(list, newText)
                adapter.updateAdapter(tmpList)
                return true
            }

        })
    }
}