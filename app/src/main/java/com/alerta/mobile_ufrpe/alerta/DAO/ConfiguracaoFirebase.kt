package com.alerta.mobile_ufrpe.alerta.DAO

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object ConfiguracaoFirebase {

    private var referenciaFirebase: DatabaseReference? = null
    private var autenticacao: FirebaseAuth? = null

    val firebase: DatabaseReference?
        get() {
            if (referenciaFirebase == null) {
                referenciaFirebase = FirebaseDatabase.getInstance().reference
            }
            return referenciaFirebase

        }

    val firebaseAutenticacao: FirebaseAuth?
        get() {
            if (autenticacao == null) {
                autenticacao = FirebaseAuth.getInstance()
            }
            return autenticacao
        }

}
