package br.edu.ifsp.scl.sdm.listpad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.scl.sdm.listpad.model.Item

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME="listapad.db"
        private val DATABASE_VERSION=1
        private  val TABLE_NAME = "item"
        private val ID_ITEM = "id_item"
        private val DESCRICAO = "descricao"
        private val FLAG = "flag"
        private val LISTA ="lista"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
         val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID_ITEM INTEGER PRIMARY KEY AUTOINCREMENT, $DESCRICAO TEXT, $FLAG TEXT, $LISTA INTEGER)"
        p0?.execSQL(CREATE_TABLE)
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //upgrade versao
    }

    //post
    fun inserirItem(item: Item): Long
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(DESCRICAO, item.descricao)
        values.put(FLAG, item.flag)
        values.put(LISTA, item.lista)
        val result = db.insert(TABLE_NAME,null,values)
        db.close()
        return result
    }

    //put
    fun atualizarItem(item: Item): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(DESCRICAO, item.descricao)
        values.put(FLAG, item.flag)
        values.put(LISTA, item.lista)
        val result = db.update(TABLE_NAME,values,"$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }

    //delete
    fun apagarItem(item: Item):Int{
        val db = this.writableDatabase
        val result =db.delete(TABLE_NAME,"$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }


    //get
    fun listarItem():ArrayList<Item>
    {
        val listarItem = ArrayList<Item>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext())
        {
            val c = Item (cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3))
            listarItem().add(c)
        }
        cursor.close()
        db.close()
        return listarItem()
    }

}
