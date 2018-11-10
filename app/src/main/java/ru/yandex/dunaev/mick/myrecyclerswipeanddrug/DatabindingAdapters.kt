package ru.yandex.dunaev.mick.myrecyclerswipeanddrug

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

@BindingAdapter("src")
fun ImageView.loadImage(url: String) {
    if (!url.isEmpty()) Picasso.get().load(url).into(this)
}

@BindingAdapter("visibility")
fun View.showVisibility(b: Boolean){
    visibility = if(b) View.VISIBLE else View.GONE
}

@BindingAdapter("adapter")
fun RecyclerView.recyclerAdapter(adp: CategoryAdapter<Any, ViewDataBinding>){
    adapter = adp
}

@BindingAdapter("layout_manager")
fun RecyclerView.recyclerManager(manager: RecyclerView.LayoutManager){
    layoutManager = manager
}