package br.edu.ifsp.scl.sdm.listpad.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.R
import br.edu.ifsp.scl.sdm.listpad.model.Lista

class ListaAdapter(val listaLista: ArrayList<Lista>) :
    RecyclerView.Adapter<ListaAdapter.ListaViewHolder>(), Filterable {

    var listener: ListaListener? = null
    var listaListaFilterable = ArrayList<Lista>()

    init {
        this.listaListaFilterable = listaLista
    }

    fun setClickListener(listener: ListaListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaAdapter.ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_item, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaAdapter.ListaViewHolder, position: Int) {
        holder.nomeVH.text = listaListaFilterable[position].nome
        holder.descVH.text = listaListaFilterable[position].descricao

    }

    override fun getItemCount(): Int {
        return listaListaFilterable.size
    }

    inner class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeVH = view.findViewById<TextView>(R.id.nome)
        val descVH = view.findViewById<TextView>(R.id.descricao)


        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ListaListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    listaListaFilterable = listaLista
                else {
                    val resultList = ArrayList<Lista>()
                    for (row in listaLista)
                        if (row.nome.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    listaListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listaListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listaListaFilterable = p1?.values as ArrayList<Lista>
                notifyDataSetChanged()
            }

        }
    }

}