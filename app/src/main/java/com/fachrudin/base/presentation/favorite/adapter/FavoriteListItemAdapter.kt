package com.fachrudin.base.presentation.favorite.adapter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fachrudin.base.R
import com.fachrudin.base.databinding.ItemNewsBinding
import com.fachrudin.base.presentation.mainpage.adapter.NewsListItemView
import com.fachrudin.base.presentation.mainpage.adapter.NewsListItemViewModel
import com.fachrudin.base.core.BaseRecycleViewAdapter
import com.fachrudin.base.core.BaseViewHolder
import com.fachrudin.base.core.ViewDataBindingOwner
import com.fachrudin.base.entity.NewsItem
import com.fachrudin.base.util.DateHelper
import com.fachrudin.base.util.database
import org.jetbrains.anko.db.delete

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
class FavoriteListItemAdapter : BaseRecycleViewAdapter<NewsItem>() {
    var listener: OnItemSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(parent.context, view, listener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsItem>, position: Int) {
        (holder as ViewHolder).bindData(getItem(position), position)
    }

    class ViewHolder(context: Context, view: View, private val listener: OnItemSelectedListener? = null) :
        BaseViewHolder<NewsItem>(context, view),
        NewsListItemView,
        ViewDataBindingOwner<ItemNewsBinding> {

        override lateinit var binding: ItemNewsBinding
        private var viewModel: NewsListItemViewModel? = null
        private var data: NewsItem? = null

        init {
            binding.vm = NewsListItemViewModel()
            binding.view = this
            viewModel = binding.vm
        }

        override fun bindData(data: NewsItem) {
            // ignore
        }

        fun bindData(data: NewsItem, position: Int) {
            this.data = data

            viewModel?.bIsFavorite?.set(true)

            data.id.let {
                viewModel?.bTextId?.set(it.toString())
            }

            data.title.let {
                viewModel?.bTextTitle?.set(it)
            }

            data.by.let {
                viewModel?.bTextAuthor?.set(it)
            }

            data.time.let {
                viewModel?.bTextDate?.set(DateHelper.dateMessageFormat(it, DateHelper.EEDDMMMYYYY_HHMMZZZ))
            }
        }

        override fun onClick(view: View) {
            data?.let {
                removeFromFavorite(it)
            }
        }

        private fun removeFromFavorite(data: NewsItem) {
            try {
                context.database.use {
                    delete(
                        NewsItem.TABLE_NEWS_FAVORITE, "(NEWS_ID = {id})",
                        "id" to data.id
                    )
                }
                Toast.makeText(context, context.getString(R.string.favorite_msg_remove), Toast.LENGTH_SHORT).show()
                viewModel?.bIsFavorite?.set(false)
                listener?.onFavoriteItemSelected(position)
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_error, e.localizedMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    interface OnItemSelectedListener {
        fun onFavoriteItemSelected(position: Int)
    }
}