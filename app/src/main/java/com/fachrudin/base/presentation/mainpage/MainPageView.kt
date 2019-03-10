package com.fachrudin.base.presentation.mainpage

import androidx.recyclerview.widget.LinearLayoutManager
import com.fachrudin.base.core.BaseView

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
interface MainPageView : BaseView {
    var layoutManager: LinearLayoutManager
}