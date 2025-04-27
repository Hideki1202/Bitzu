package com.example.bitzu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitzu.R
import com.example.bitzu.models.Tag

class TagAdapter(private val tagList: List<Tag>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTagName: TextView = itemView.findViewById(R.id.text_view_tag_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tag_item, parent, false) // Crie um layout para exibir cada tag
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val currentTag = tagList[position]
        holder.textViewTagName.text = currentTag.tag
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}