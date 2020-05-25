package com.alerta.mobile_ufrpe.alerta.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.alerta.mobile_ufrpe.alerta.DAO.ConfiguracaoFirebase
import com.alerta.mobile_ufrpe.alerta.Entidades.Usuarios
import com.alerta.mobile_ufrpe.alerta.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private var edtEmail: EditText? = null
    private var edtSenha: EditText? = null
    private var btnLogar: Button? = null
    private var tvAbreCadastro: TextView? = null

    private var autenticacao: FirebaseAuth? = null
    private var usuarios: Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById<View>(R.id.edtEmail) as EditText
        edtSenha = findViewById<View>(R.id.edtSenha) as EditText
        btnLogar = findViewById<View>(R.id.btnEntrar) as Button
        tvAbreCadastro = findViewById<View>(R.id.tvAbreCadastro) as TextView

        btnLogar!!.setOnClickListener {
            if (edtEmail!!.text.toString() != "" && edtSenha!!.text.toString() != "") {
                usuarios = Usuarios()
                usuarios!!.email = edtEmail!!.text.toString()
                usuarios!!.senha = edtSenha!!.text.toString()

                validarLogin()

            } else {
                Toast.makeText(this@LoginActivity, "Preencha os campos de usuário e senha", Toast.LENGTH_SHORT).show()
            }
        }

        tvAbreCadastro!!.setOnClickListener { abreCadastroUsuario() }

    }

    private fun validarLogin() {
        autenticacao = ConfiguracaoFirebase.firebaseAutenticacao
        autenticacao!!.signInWithEmailAndPassword(usuarios!!.email, usuarios!!.senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                abrirTelaPrincipal()
                Toast.makeText(this@LoginActivity, "Login Efetuado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun abrirTelaPrincipal() {
        val intentAbrirTelaPrincipal = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intentAbrirTelaPrincipal)
    }

    fun abreCadastroUsuario() {
        val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
        startActivity(intent)
    }


}


