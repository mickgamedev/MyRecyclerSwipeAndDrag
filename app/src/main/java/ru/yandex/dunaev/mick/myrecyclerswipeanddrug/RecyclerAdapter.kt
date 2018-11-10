package ru.yandex.dunaev.mick.myrecyclerswipeanddrug

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.dunaev.mick.myrecyclerswipeanddrug.databinding.CardItemBinding
import java.util.*



class ProductAdapter : CategoryAdapter<PricePosition, CardItemBinding>(R.layout.card_item) {

    var onStartDrag:(RecyclerView.ViewHolder) -> Unit = {}

    override fun onBindViewHolder(holder: CategoryHolder<CardItemBinding>, position: Int) = with(holder.binding){
        holder.itemView.setOnClickListener { onItemClick(position) }
        tCtl.setOnTouchListener { v, event -> onTouchListener(v,event,holder)}
        data = getItem(position)
        executePendingBindings()
    }

    fun onTouchListener(v: View, event: MotionEvent, holder:RecyclerView.ViewHolder): Boolean{
        if (event.action == MotionEvent.ACTION_DOWN) onStartDrag(holder)
        return false
    }
}

abstract class  CategoryAdapter<T: Any,V: ViewDataBinding>(val layout: Int) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder<V>>(){
    private var items = mutableListOf<T>()
    var onItemClick: (Int) -> Unit = {v -> Log.v("NOT BINDING","v = $v")}

    fun setItems(list: MutableList<T>){
        items = list
        notifyDataSetChanged()
        Log.w("ADAPTER","setItems list size = ${list.size}")
    }
    fun getItem(position: Int) = items[position]
    fun getItems() = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder<V> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<V>(inflater, layout, parent,false)
        return CategoryHolder<V>(binding)
    }

    override fun getItemCount()= items.size

    fun onSwipe(i: Int){
        items.removeAt(i)
        notifyItemRemoved(i);
    }

    fun onMove(fromPosition: Int, toPosition: Int){
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    class CategoryHolder<V: ViewDataBinding>(val binding: V): RecyclerView.ViewHolder(binding.root)
}

class BaseSwipeDragHelper(val onSwipe:(Int)->Unit, val onMove: (Int, Int)->Unit): ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN // or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags,swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val c = viewHolder.adapterPosition
        val t = target.adapterPosition
        onMove(c,t)
        return true;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onSwipe(position)
    }

/*    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.itemView?.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(0)
    }*/

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val width = viewHolder.itemView.width.toFloat()
            val alpha = 1.0f - Math.abs(dX) / width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(
                c, recyclerView, viewHolder, dX, dY,
                actionState, isCurrentlyActive
            )
        }
    }
}