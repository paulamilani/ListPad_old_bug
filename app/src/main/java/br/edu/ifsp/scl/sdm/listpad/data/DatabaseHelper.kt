package br.edu.ifsp.scl.sdm.listpad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.scl.sdm.listpad.model.Categoria
import br.edu.ifsp.scl.sdm.listpad.model.Item
import br.edu.ifsp.scl.sdm.listpad.model.Lista

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "listapad.db"
        private val DATABASE_VERSION = 1

        private val CATEGORIA = "Categoria"
        private val ID_CATEGORIA = "Id"
        private val DESC_CAT = "Descricao"

        private val LISTA = "Lista"
        private val ID_LISTA = "Id"
        private val NOME = "Nome"
        private val DESC_LISTA = "Descricao"
        private val URGENTE = "Urgente"
        private val CAT_FK = "Flag"

        private val ITEM = "Item"
        private val ID_ITEM = "id_item"
        private val DESC_ITEM = "Descricao"
        private val FLAG = "Flag"
        private val LISTA_FK = "Lista"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_CATEGORIA = "CREATE TABLE $CATEGORIA ($ID_CATEGORIA INTEGER PRIMARY KEY AUTOINCREMENT, $DESC_CAT TEXT)"
        val CREATE_TABLE_LISTA = "CREATE TABLE $LISTA ($ID_LISTA INTEGER PRIMARY KEY AUTOINCREMENT, $NOME TEXT, $DESC_LISTA TEXT, $URGENTE INTEGER, $CAT_FK INTEGER, FOREIGN KEY ($CAT_FK ) REFERENCES $CATEGORIA ($ID_CATEGORIA))"
        val CREATE_TABLE_ITEM = "CREATE TABLE $ITEM ($ID_ITEM INTEGER PRIMARY KEY AUTOINCREMENT, $DESC_ITEM TEXT, $FLAG TEXT, $LISTA_FK INTEGER, FOREIGN KEY ($LISTA_FK) REFERENCES $LISTA ($ID_LISTA))"

        db?.execSQL(CREATE_TABLE_ITEM)
        db?.execSQL(CREATE_TABLE_LISTA)
        db?.execSQL(CREATE_TABLE_CATEGORIA)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //upgrade versao
    }


    //INSERIR
    fun inserirCategoria(categoria: Categoria): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_CATEGORIA, categoria.id_categoria)
        values.put(DESC_CAT, categoria.descricao)
        val result = db.insert(CATEGORIA, null, values)
        db.close()
        return result
    }
    fun inserirLista(lista: Lista): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LISTA, lista.id_lista)
        values.put(NOME, lista.nome)
        values.put(DESC_LISTA, lista.descricao)
        values.put(URGENTE, lista.urgente)
        values.put(CAT_FK, lista.categoria)
        val result = db.insert(LISTA, null, values)
        db.close()
        return result
    }

    fun inserirItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(DESC_ITEM, item.descricao)
        values.put(FLAG, item.flag)
        values.put(LISTA, item.lista)
        val result = db.insert(ITEM, null, values)
        db.close()
        return result
    }

    //ATUALIZAR
    fun atualizarCategoria(categoria: Categoria): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_CATEGORIA, categoria.id_categoria)
        values.put(DESC_CAT, categoria.descricao)
        val result = db.update(CATEGORIA, values, "$ID_ITEM=?", arrayOf(categoria.id_categoria.toString()))
        db.close()
        return result
    }
    fun atualizarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LISTA, lista.id_lista)
        values.put(NOME, lista.nome)
        values.put(DESC_LISTA, lista.descricao)
        values.put(URGENTE, lista.urgente)
        values.put(CAT_FK, lista.categoria)
        val result = db.update(LISTA, values, "$ID_ITEM=?", arrayOf(lista.id_lista.toString()))
        db.close()
        return result
    }

    fun atualizarItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(DESC_ITEM, item.descricao)
        values.put(FLAG, item.flag)
        values.put(LISTA, item.lista)
        val result = db.update(ITEM, values, "$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }

    //DELETAR
    fun apagarCategoria(categoria: Categoria): Int {
        val db = this.writableDatabase
        val result = db.delete(CATEGORIA, "$ID_ITEM=?", arrayOf(categoria.id_categoria.toString()))
        db.close()
        return result
    }
    fun apagarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val result = db.delete(LISTA, "$ID_ITEM=?", arrayOf(lista.id_lista.toString()))
        db.close()
        return result
    }

    fun apagarItem(item: Item): Int {
        val db = this.writableDatabase
        val result = db.delete(ITEM, "$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }


    //LISTAR
    fun listarlista(): ArrayList<Lista> {
        val listarLista = ArrayList<Lista>()
        val query = "SELECT * FROM $LISTA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val c = Item(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            listarItem().add(c)
        }
        cursor.close()
        db.close()
        return listarlista()
    }

    fun listarItem(): ArrayList<Item> {
        val listarItem = ArrayList<Item>()
        val query = "SELECT * FROM $ITEM"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val c = Item(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            listarItem().add(c)
        }
        cursor.close()
        db.close()
        return listarItem()
    }

}
