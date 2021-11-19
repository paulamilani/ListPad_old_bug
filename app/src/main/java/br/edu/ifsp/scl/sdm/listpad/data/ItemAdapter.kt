package br.edu.ifsp.scl.sdm.listpad.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.R
import br.edu.ifsp.scl.sdm.listpad.model.Item

class ItemAdapter (val itemLista:ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(),
    Filterable {

    var listener:ItemListener?=null

    var itemListaFilterable = ArrayList<Item>()

    init {
        this.itemListaFilterable = itemLista
    }

    fun setClickListener(listener:ItemListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return  ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.nomeVH.text = itemListaFilterable[position].descricao

    }

    override fun getItemCount(): Int {
        return itemListaFilterable.size
    }

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val nomeVH = view.findViewById<TextView>(R.id.nome)


        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ItemListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    itemListaFilterable = itemLista
                else
                {
                    val resultList = ArrayList<Item>()
                    for (row in itemLista)
                        if (row.descricao.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    itemListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                itemListaFilterable = p1?.values as ArrayList<Item>
                notifyDataSetChanged()
            }

        }
    }

}