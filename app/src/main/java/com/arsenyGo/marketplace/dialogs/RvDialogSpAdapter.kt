package com.arsenyGo.marketplace.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ArsenyGo.marketplace.R
import com.arsenyGo.marketplace.act.EditAdsAct

class RvDialogSpAdapter(var tvSelection: TextView, var dialog: AlertDialog) :
    RecyclerView.Adapter<RvDialogSpAdapter.SpViewHolder>() {
    private val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_item_list, parent, false)
        return SpViewHolder(view, tvSelection, dialog)
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    class SpViewHolder(itemView: View, var tvSelection: TextView, var dialog: AlertDialog) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var itemText = ""

        fun setData(text: String) {
            val tvSpItem = itemView.findViewById<TextView>(R.id.tvSpItem)
            tvSpItem.text = text
            itemText = text
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            tvSelection.text = itemText
            dialog.dismiss()
        }
    }

    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
    }
}