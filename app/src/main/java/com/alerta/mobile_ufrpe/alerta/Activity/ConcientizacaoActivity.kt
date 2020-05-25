package com.alerta.mobile_ufrpe.alerta.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.alerta.mobile_ufrpe.alerta.R
import com.github.barteksc.pdfviewer.PDFView

class ConcientizacaoActivity : AppCompatActivity() {

    internal lateinit var pdfView: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concientizacao)

        pdfView = findViewById<View>(R.id.concietizacaoPDF) as PDFView
        pdfView.fromAsset("cartilha_concientizacao.pdf").load()
    }

}

