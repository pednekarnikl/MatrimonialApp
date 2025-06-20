package com.ddesk.sddemoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.data.model.UserEntity

class ItemAdapter(private val items: List<UserEntity>) : RecyclerView.Adapter<ItemViewHolder>() {
    private var onCancelClickListener: ((UserEntity) -> Unit)? = null
    private var onSelectClickListener: ((UserEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_new_match, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], onCancelClickListener, onSelectClickListener)
    }

    override fun getItemCount(): Int = items.size

    // Setters for click listeners
    fun setOnCancelClickListener(listener: (UserEntity) -> Unit) {
        onCancelClickListener = listener
    }

    fun setOnSelectClickListener(listener: (UserEntity) -> Unit) {
        onSelectClickListener = listener
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: UserEntity,
        onCancelClick: ((UserEntity) -> Unit)?,
        onSelectClick: ((UserEntity) -> Unit)?
    ) {
        val icon = itemView.findViewById<ImageView>(R.id.ivIcon)

        Glide.with(itemView.context)
            .load(item.picture)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(icon)

        itemView.findViewById<TextView>(R.id.tvRating).text = ((item.matchScore)?.times(100))?.toInt().toString()
        itemView.findViewById<TextView>(R.id.tvName).text = item.fullName
        itemView.findViewById<TextView>(R.id.tvAgeAddress).text =
            "${item.age} ${item.street},${item.city},${item.street}"

        itemView.findViewById<ImageView>(R.id.ivCancel).setOnClickListener {
            onCancelClick?.invoke(item)
        }
        itemView.findViewById<ImageView>(R.id.ivSelect).setOnClickListener {
            onSelectClick?.invoke(item)
        }
    }
}