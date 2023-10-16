package com.eth.refiq.ui.searchtopic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eth.refiq.databinding.ItemTopicBinding
import com.eth.refiq.domain.Topic

class TopicAdapter constructor(private val onTopicClicked: ((Topic) -> Unit)) :
    RecyclerView.Adapter<TopicItemViewHolder>() {
    private val items = mutableListOf<Topic>()
    fun updateAdapter(items: List<Topic>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicItemViewHolder {
        return TopicItemViewHolder(
            ItemTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TopicItemViewHolder, position: Int) {
        holder.bind(items[position],onTopicClicked)
    }

}

class TopicItemViewHolder(private val binding: ItemTopicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(topic: Topic, onTopicClicked: (Topic) -> Unit) {
        binding.textviewTopicitem.text = topic.name
        binding.root.setOnClickListener {
            onTopicClicked(topic)
        }
    }
}