package com.mabrouk.newstask.presentaion.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mabrouk.newstask.R
import com.mabrouk.newstask.databinding.ArticleDetailsBinding
import com.mabrouk.newstask.domain.models.Article
import dagger.hilt.android.AndroidEntryPoint

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/11/22
 */
@AndroidEntryPoint
class ArticleDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding: ArticleDetailsBinding
    var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.article_details)
        intent.getParcelableExtra<Article>("article")?.let {
            article=it
            viewBinding.article = it
        }
    }

    fun backClick(view: View) {
        finishAfterTransition()
    }

    fun urlClick(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(article?.url)
        startActivity(intent)
    }
}