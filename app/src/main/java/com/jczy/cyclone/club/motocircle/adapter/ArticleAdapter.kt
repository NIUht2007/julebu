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
import com.jczy.cyclone.club.motocircle.model.Article

class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(ARTICLE_COMPARATOR) {
    
    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }
            
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }
    
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cover: ImageView = itemView.findViewById(R.id.cover)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val readCount: TextView = itemView.findViewById(R.id.read_count)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)
        
        fun bind(article: Article) {
            // 加载封面
            Glide.with(itemView.context)
                .load(article.cover)
                .into(cover)
            
            // 设置标题
            title.text = article.title
            
            // 设置作者
            author.text = article.author
            
            // 设置统计数据
            readCount.text = "${article.readCount}阅读"
            likeCount.text = "${article.likeCount}赞"
        }
    }
}