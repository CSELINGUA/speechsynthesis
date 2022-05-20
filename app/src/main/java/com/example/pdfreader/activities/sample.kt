/*
package com.example.pdfreader.activities


import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class ExampleAdapter internal constructor(exampleList: MutableList<ExampleItem>) :
    RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>(), Filterable {
    private val exampleList: List<ExampleItem>
    private val exampleListFull: List<ExampleItem>

    inner class ExampleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView1: TextView
        var textView2: TextView

        init {
            imageView = itemView.findViewById(R.id.image_view)
            textView1 = itemView.findViewById(R.id.text_view1)
            textView2 = itemView.findViewById(R.id.text_view2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.example_item,
            parent, false
        )
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem: ExampleItem = exampleList[position]
        holder.imageView.setImageResource(currentItem.getImageResource())
        holder.textView1.setText(currentItem.getText1())
        holder.textView2.setText(currentItem.getText2())
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ExampleItem> = ArrayList<ExampleItem>()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in exampleListFull) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll((results.values as List<*>))
            notifyDataSetChanged()
        }
    }

    init {
        this.exampleList = exampleList
        exampleListFull = ArrayList<Any?>(exampleList)
    }
}
*/
