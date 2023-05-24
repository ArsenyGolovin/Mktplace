package com.arsenyGo.marketplace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ArsenyGo.marketplace.R

class FragmentImageList(private val fragCloseInterface: FragmentCloseInterface, private val newList: ArrayList<String>): Fragment() {
    val adapter = SelectImageRvAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btBack = view.findViewById<Button>(R.id.btBack)
        val rv = view.findViewById<RecyclerView>(R.id.rvSelectImage)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        val updateList = ArrayList<SelectImageItem>()
        for (n in 0 until newList.size) {
            updateList.add(SelectImageItem(n.toString(), newList[n]))
        }
        adapter.updateAdapter(updateList)
        btBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragmentClose(adapter.mainArray)
    }
}