package com.hola.app.music

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MediaItemAdapter(private val mContext: Context) : BaseAdapter() {
    private val items = ArrayList<MediaBrowserCompat.MediaItem>()

    fun addItem(item: MediaBrowserCompat.MediaItem) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addItems(items: List<MediaBrowserCompat.MediaItem>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: List<MediaBrowserCompat.MediaItem>) {
        this.items.clear()
        addItems(items)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.media_item_view, null)
        val name = view.findViewById<TextView>(R.id.name)
        name.text = items[position].description.title
        return view
    }
}