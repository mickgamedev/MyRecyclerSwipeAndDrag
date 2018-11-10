package ru.yandex.dunaev.mick.myrecyclerswipeanddrug

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel(){
    val adapter = ProductAdapter()

    init {
        adapter.setItems(Repository.priceList)
    }
}