package com.alerta.mobile_ufrpe.alerta.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alerta.mobile_ufrpe.alerta.DAO.ConfiguracaoFirebase
import com.alerta.mobile_ufrpe.alerta.Entidades.Usuarios
import com.alerta.mobile_ufrpe.alerta.Helper.Base64Custom
import com.alerta.mobile_ufrpe.alerta.Helper.Preferencias
import com.alerta.mobile_ufrpe.alerta.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroActivity : AppCompatActivity() {

    private var usuarios: Usuarios? = null
    private var autenticacao: FirebaseAuth? = null

    private var edtCadEmail: EditText? = null
    private var edtCadSenha: EditText? = null
    private var edtCadConfSenha: EditText? = null
    private var edtCadNome: EditText? = null

    private var btnGravar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        edtCadEmail = findViewById<View>(R.id.edtCadEmail) as EditText
        edtCadSenha = findViewById<View>(R.id.edtCadSenha) as EditText
        edtCadConfSenha = findViewById<View>(R.id.edtCadConfSenha) as EditText
        edtCadNome = findViewById<View>(R.id.edtCadNome) as EditText

        btnGravar = findViewById<View>(R.id.btnGravar) as Button

        btnGravar!!.setOnClickListener {
            if (edtCadSenha!!.text.toString() == edtCadConfSenha!!.text.toString()) {

                usuarios = Usuarios()
                usuarios!!.email = edtCadEmail!!.text.toString()
                usuarios!!.senha = edtCadSenha!!.text.toString()
                usuarios!!.confSenha = edtCadConfSenha!!.text.toString()
                usuarios!!.nome = edtCadNome!!.text.toString()

                cadastrarUsuario()

            } else {
                Toast.makeText(this@CadastroActivity, "As senhas não são correspondentes", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.firebaseAutenticacao
        autenticacao!!.createUserWithEmailAndPassword(
                usuarios!!.email,
                usuarios!!.senha

        ).addOnCompleteListener(this@CadastroActivity) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@CadastroActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show()

                val identificadorUsuario = Base64Custom.codificarBase64(usuarios!!.email)
                val usuarioFirebase = task.result.user
                usuarios!!.id = identificadorUsuario
                usuarios!!.salvar()

                val preferencias = Preferencias(this@CadastroActivity)
                preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios!!.nome)

                abrirLoginUsuario()

            } else {
                val errorExcecao: String
                try {
                    throw task.getException()!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    errorExcecao = "Digite uma senha mais forte contendo no mínimo 8 caracteres de letras e números"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    errorExcecao = "O e-mail digitado não é valido, digite um novo e-mail"
                } catch (e: FirebaseAuthUserCollisionException) {
                    errorExcecao = "Esse e-mail já esta cadastrado no sistema"
                } catch (e: Exception) {
                    errorExcecao = "Erro ao efetuar cadasto"
                }

                Toast.makeText(this@CadastroActivity, "Error $errorExcecao", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun abrirLoginUsuario() {
        val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
