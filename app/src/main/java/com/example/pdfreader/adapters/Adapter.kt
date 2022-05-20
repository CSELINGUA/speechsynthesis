package com.example.pdfreader.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.models.Model1
import com.example.pdfreader.R
import com.example.pdfreader.activities.ViewerActivity
import kotlinx.android.synthetic.main.pdf_file_holder.view.*
import kotlin.collections.ArrayList

class Adapter: RecyclerView.Adapter<PDFViewHolder>(),Filterable{

//    private lateinit var pdfFilter: Filter
    private lateinit var items: ArrayList<Model1>
    private lateinit var overflowItems: ArrayList<Model1>

    fun submitList(pdfList: ArrayList<Model1>){
        items = pdfList
        overflowItems = ArrayList(pdfList)
    }
    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) {
        when(holder){
            is PDFViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        return PDFViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pdf_file_holder,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return pdfFilter

    }
    private val pdfFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Model1> = java.util.ArrayList<Model1>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(overflowItems)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in overflowItems) {
                    if (item.name!!.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            items.clear()
            items.addAll((results.values as List<Model1>))
            notifyDataSetChanged()
        }
    }
}

class PDFViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    var pdfName = itemView.pdfname!!
    var pdfDescription = itemView.pdfdescription!!

    fun bind(item: Model1){
        pdfName.text = item.name
        pdfDescription.text = item.size.toString()

        itemView.setOnClickListener{

            val intent = Intent(itemView.context, ViewerActivity::class.java)
            intent.putExtra("uri", item.uri.toString())
            startActivity(itemView.context,intent,null)
        }
    }
}

interface onPDFItemClickListener {
    fun onItemClick(items: Model1, position: Int)
}
