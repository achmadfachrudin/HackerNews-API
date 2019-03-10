package com.fachrudin.base.presentation.mainpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fachrudin.base.R
import com.fachrudin.base.core.BaseActivity
import com.fachrudin.base.core.ViewDataBindingOwner
import com.fachrudin.base.databinding.ActivityMainPageBinding
import com.fachrudin.base.presentation.favorite.FavoriteActivity
import com.fachrudin.base.presentation.mainpage.adapter.NewsListItemAdapter

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
class MainPageActivity : BaseActivity(),
    MainPageView,
    ViewDataBindingOwner<ActivityMainPageBinding> {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_main_page
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, MainPageActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MainPageViewModel
    override lateinit var binding: ActivityMainPageBinding

    private lateinit var listAdapter: NewsListItemAdapter
    override var layoutManager: LinearLayoutManager
        get() = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        set(value) {}

    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = MainPageViewModel()
        viewModel = binding.vm!!

        setUI()

        observeResult()
        observeError()
    }

    private fun setUI() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setHomeButtonEnabled(false)
        }

        title = getString(R.string.news_title)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.vertical_space_divider)!!)
        binding.rvList.addItemDecoration(divider)

        listAdapter = NewsListItemAdapter()
        binding.rvList.adapter = listAdapter
    }

    private fun observeError() {
        observeData(viewModel.getError()) { error ->
            error?.let {
                viewModel.bTextError.set(it.message.toString())
                viewModel.bShowErrorView.set(true)
            }
        }
    }

    /**
     * Observe result in viewModel if any data changed
     */
    private fun observeResult() {
        observeData(viewModel.getNewsList()) { data ->
            data?.let {
                listAdapter.setData(it)
                if (it.size >= 30) {
                    viewModel.bShowLoadingView.set(false)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle item selection
        return when (item?.itemId) {
            R.id.menu_favorite -> {
                FavoriteActivity.startThisActivity(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getIdNewsListFromApi()
    }

    override fun onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, getString(R.string.app_msg_close), Toast.LENGTH_SHORT).show()
        }
        time = System.currentTimeMillis()
    }
}