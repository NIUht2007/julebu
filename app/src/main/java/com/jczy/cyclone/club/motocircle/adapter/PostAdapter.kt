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
import com.jczy.cyclone.club.motocircle.model.Post

class PostAdapter : PagingDataAdapter<Post, PostAdapter.PostViewHolder>(POST_COMPARATOR) {
    
    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }
            
            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        if (post != null) {
            holder.bind(post)
        }
    }
    
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.avatar)
        private val username: TextView = itemView.findViewById(R.id.username)
        private val content: TextView = itemView.findViewById(R.id.content)
        private val postImage: ImageView = itemView.findViewById(R.id.post_image)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)
        private val commentCount: TextView = itemView.findViewById(R.id.comment_count)
        private val shareCount: TextView = itemView.findViewById(R.id.share_count)
        
        fun bind(post: Post) {
            // 加载头像
            Glide.with(itemView.context)
                .load(post.avatar)
                .circleCrop()
                .into(avatar)
            
            // 设置用户名
            username.text = post.username
            
            // 设置内容
            content.text = post.content
            
            // 加载图片
            if (post.images.isNotEmpty()) {
                postImage.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(post.images[0])
                    .into(postImage)
            } else {
                postImage.visibility = View.GONE
            }
            
            // 设置统计数据
            likeCount.text = post.likeCount.toString()
            commentCount.text = post.commentCount.toString()
            shareCount.text = post.shareCount.toString()
        }
    }
}