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

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_salvar) {
            val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
            val descricao = findViewById<EditText>(R.id.editTextDescricao).text.toString()

            val c = Lista(null, nome, descricao)
            if (db.inserirLista(c) > 0)
                Toast.makeText(this, "Lista Inserida", Toast.LENGTH_LONG).show()
            finish()

        }

        return super.onOptionsItemSelected(item)
    }
}