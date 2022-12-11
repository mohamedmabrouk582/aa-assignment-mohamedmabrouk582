package com.mabrouk.newstask.presentaion.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.mabrouk.newstask.R
import com.mabrouk.newstask.core.countries
import com.mabrouk.newstask.databinding.ActivityMainBinding
import com.mabrouk.newstask.presentaion.viewmodels.ArticleStates
import com.mabrouk.newstask.presentaion.viewmodels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var viewBinding: ActivityMainBinding
    private val viewModel: ArticleViewModel by viewModels()
    private val adapter by lazy {
        ArticleAdapter {
            val intent = Intent(this,ArticleDetailsActivity::class.java)
            intent.putExtra("article",it)
            startActivity(intent)
        }
    }

    private val countriesAdapter by lazy {
        ArrayAdapter(
            this,
            R.layout.spinner_item_view,
            viewModel.countries
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.vm = viewModel
        viewBinding.articlesRcv.adapter = adapter

        lifecycleScope.launch {
            viewModel.articlesStates.collectLatest {
                when (it) {
                    is ArticleStates.Error ->
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    ArticleStates.IDLE -> {}
                    is ArticleStates.LoadArticles -> {
                        adapter.data = it.data
                    }
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu?.findItem(R.id.spinner)
        val spinner = item?.actionView as Spinner
        if (spinner.adapter == null) {
            spinner.adapter = countriesAdapter
            spinner.onItemSelectedListener = this
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        countries()[viewModel.countries[position]]?.let {
            viewModel.getArticles(it)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


}