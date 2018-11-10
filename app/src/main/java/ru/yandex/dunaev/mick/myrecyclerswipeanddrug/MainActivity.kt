package ru.yandex.dunaev.mick.myrecyclerswipeanddrug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.dunaev.mick.myrecyclerswipeanddrug.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindign: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val model: MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        bindign.viewModel = model
        bindign.layoutManager = LinearLayoutManager(this)
        itemTouchHelper = ItemTouchHelper(BaseSwipeDragHelper(model.adapter::onSwipe, model.adapter::onMove))
        itemTouchHelper.attachToRecyclerView(bindign.recycler)
        model.adapter.onStartDrag = {v -> onTouchDrag(v)}
    }

    fun onTouchDrag(holder: RecyclerView.ViewHolder){
        itemTouchHelper.startDrag(holder)
    }
}
