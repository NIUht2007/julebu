package com.jczy.cyclone.club.motocircle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.motocircle.model.Guide

class GuideAdapter : PagingDataAdapter<Guide, GuideAdapter.GuideViewHolder>(GUIDE_COMPARATOR) {
    
    companion object {
        private val GUIDE_COMPARATOR = object : DiffUtil.ItemCallback<Guide>() {
            override fun areItemsTheSame(oldItem: Guide, newItem: Guide): Boolean {
                return oldItem.id == newItem.id
            }
            
            override fun areContentsTheSame(oldItem: Guide, newItem: Guide): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guide, parent, false)
        return GuideViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        val guide = getItem(position)
        if (guide != null) {
            holder.bind(guide)
        }
    }
    
    class GuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cover: ImageView = itemView.findViewById(R.id.cover)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val distance: TextView = itemView.findViewById(R.id.distance)
        private val duration: TextView = itemView.findViewById(R.id.duration)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)
        
        fun bind(guide: Guide) {
            // 加载封面
            Glide.with(itemView.context)
                .load(guide.cover)
                .into(cover)
            
            // 设置标题
            title.text = guide.title
            
            // 设置作者
            author.text = guide.author
            
            // 设置距离和时长
            distance.text = "${guide.distance}km"
            duration.text = "${guide.duration}分钟"
            
            // 设置点赞数
            likeCount.text = "${guide.likeCount}赞"
        }
    }
}