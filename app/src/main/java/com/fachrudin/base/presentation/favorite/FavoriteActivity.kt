package com.fachrudin.base.presentation.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fachrudin.base.R
import com.fachrudin.base.core.BaseActivity
import com.fachrudin.base.core.ViewDataBindingOwner
import com.fachrudin.base.databinding.ActivityFavoriteBinding
import com.fachrudin.base.entity.NewsItem
import com.fachrudin.base.presentation.favorite.adapter.FavoriteListItemAdapter
import com.fachrudin.base.util.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
class FavoriteActivity : BaseActivity(),
    FavoriteView,
    ViewDataBindingOwner<ActivityFavoriteBinding>,
    FavoriteListItemAdapter.OnItemSelectedListener {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_favorite
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, FavoriteActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: FavoriteViewModel
    override lateinit var binding: ActivityFavoriteBinding

    private lateinit var listAdapter: FavoriteListItemAdapter
    override var layoutManager: LinearLayoutManager
        get() = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        set(value) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = FavoriteViewModel()
        viewModel = binding.vm!!

        setUI()
    }

    private fun setUI() {
        title = getString(R.string.favorite_title)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.vertical_space_divider)!!)
        binding.rvList.addItemDecoration(divider)

        listAdapter = FavoriteListItemAdapter()
        listAdapter.listener = this
        binding.rvList.adapter = listAdapter

        showFavorite()
    }

    private fun showFavorite() {
        database.use {
            val result = select(NewsItem.TABLE_NEWS_FAVORITE)
            val favorite = result.parseList(classParser<NewsItem>())
            listAdapter.setData(favorite)
        }
    }

    override fun onFavoriteItemSelected(position: Int) {
        showFavorite()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}