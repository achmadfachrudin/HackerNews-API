package com.fachrudin.base.presentation.mainpage.adapter

import androidx.databinding.ObservableField
import com.fachrudin.base.core.BaseViewModel

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
class NewsListItemViewModel : BaseViewModel() {
    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
    var bTextAuthor = ObservableField<String>()
    var bTextDate = ObservableField<String>()
    var bIsFavorite = ObservableField<Boolean>(false)
}