package com.mabrouk.newstask.presentaion.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.mabrouk.newstask.R
import com.mabrouk.newstask.databinding.ArticleItemViewBinding
import com.mabrouk.newstask.domain.models.Article

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
class ArticleAdapter(val onArticleClick: (article: Article) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.Holder>() {
    var data: ArrayList<Article> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Holder(val viewbinding: ArticleItemViewBinding) :
        RecyclerView.ViewHolder(viewbinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.article_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val article = data[position]
        holder.viewbinding.article = article
        holder.viewbinding.root.setOnClickListener { onArticleClick(article) }
    }

    override fun getItemCount(): Int = data.size
}