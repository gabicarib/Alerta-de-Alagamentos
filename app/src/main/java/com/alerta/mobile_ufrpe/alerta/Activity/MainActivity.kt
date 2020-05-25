package com.alerta.mobile_ufrpe.alerta.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alerta.mobile_ufrpe.alerta.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentManager: FragmentManager? = null
    private val usuarioFirebase: FirebaseAuth? = null
    private val autenticacao: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)


        //Fragments do Maps
        fragmentManager = supportFragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.container, MapsFragment(), "MapsFragment")
        transaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_sobre) {
            return true
        }
        if (id == R.id.action_sair) {
            //deslogarUsuario();
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFragment(fragment: Fragment, name: String) {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, fragment, name)
        transaction.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_previsão -> {

                val intentAbrirWebView = Intent(this, WebViewActivity::class.java)
                startActivity(intentAbrirWebView)
            }
            R.id.nav_notificações ->

                showFragment(MapsFragment(), "ExemploProviderV1")
            R.id.nav_conscientizacao -> {

                val intentAbrirConcientizacao = Intent(this, ConcientizacaoActivity::class.java)
                startActivity(intentAbrirConcientizacao)
            }
            R.id.nav_informacoes ->

                showFragment(MapsFragment(), "ExemploProviderV1")
            R.id.nav_configuracoes ->

                showFragment(MapsFragment(), "ExemploProviderV1")
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    /* private void deslogarUsuario() {
        usuarioFirebase.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
*/
}
