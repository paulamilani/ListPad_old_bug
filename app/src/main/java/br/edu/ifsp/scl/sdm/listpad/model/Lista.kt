package br.edu.ifsp.scl.sdm.listpad.model

import java.io.Serializable

class Lista(
    var id_lista: Int? = null,
    var nome: String = "",
    var descricao: String = "",
    var urgente: String = "",
    var categoria: String = ""
) : Serializable