package br.edu.ifsp.scl.sdm.listpad.model

import java.io.Serializable

class Item(
    var id_item: Int? = null,
    var descricao: String = "",
    var flag: String = "",
    var lista: String = ""
) : Serializable