package br.edu.ifsp.scl.sdm.listpad.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.R
import br.edu.ifsp.scl.sdm.listpad.data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.model.Lista

class DetalheActivity : AppCompatActivity() {
    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        lista = this.intent.getSerializableExtra("lista") as Lista
        val nome = findViewById<EditText>(R.id.editTextNome)
        val descricao = findViewById<EditText>(R.id.editTextDescricao)

        nome.setText(lista.nome)
        descricao.setText(lista.descricao)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_alterar) {
            val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
            val descricao = findViewById<EditText>(R.id.editTextDescricao).text.toString()

            lista.nome = nome
            lista.descricao = descricao

            if (db.atualizarLista(lista) > 0)
                Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
            finish()
        }

        if (item.itemId == R.id.action_excluir) {
            if (db.apagarLista(lista) > 0)
                Toast.makeText(this, "Informações excluídas", Toast.LENGTH_LONG).show()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}