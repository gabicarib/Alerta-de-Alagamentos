package com.alerta.mobile_ufrpe.alerta.Helper

import android.content.Context
import android.content.SharedPreferences

class Preferencias(private val context: Context) {
    private val preferences: SharedPreferences
    private val NOME_ARQUIVO = "alertaPe.preferencias"
    private val MODE = 0
    private val editor: SharedPreferences.Editor

    private val CHAVE_IDENTIFICADOR = "identificarUsuarioLogado"
    private val CHAVE_NOME = "nomeUsuarioLogado"

    val identificador: String?
        get() = preferences.getString(CHAVE_IDENTIFICADOR, null)

    val nome: String?
        get() = preferences.getString(CHAVE_NOME, null)

    init {
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE)

        editor = preferences.edit()
    }

    fun salvarUsuarioPreferencias(identificadorUsuario: String, nomeUsuario: String) {
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario)
        editor.putString(CHAVE_NOME, nomeUsuario)

        editor.commit()
    }
}
