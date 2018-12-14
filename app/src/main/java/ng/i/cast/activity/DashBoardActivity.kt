package ng.i.cast.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dash_board_.*
import kotlinx.android.synthetic.main.app_bar_dash_board.*
import ng.i.cast.R
import ng.i.cast.fragment.AuditionFragment
import ng.i.cast.fragment.GalleryFragment
import ng.i.cast.fragment.HomeFragment
import ng.i.cast.fragment.ProfileFragment

class DashBoardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var header: TextView?= null
    private var notify: ImageButton ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_)
        setSupportActionBar(toolbar)
        header = findViewById(R.id.textHeader)
        val text = "Dashboard"
        header!!.text = text
        supportFragmentManager.beginTransaction()
                .add(R.id.holder,HomeFragment())
                .addToBackStack(null)
                .commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard -> {
                header!!.text = getString(R.string.dash_board)

                supportFragmentManager.beginTransaction()
                        .replace(R.id.holder,HomeFragment())
                        .addToBackStack(null)
                        .commit()            }
            R.id.nav_gallery -> {
                header!!.text = getString(R.string.gallery)

                supportFragmentManager.beginTransaction()
                        .replace(R.id.holder,GalleryFragment())
                        .addToBackStack(null)
                        .commit()
            }
            R.id.nav_audition -> {
                header!!.text = getString(R.string.audition)

                supportFragmentManager.beginTransaction()
                        .replace(R.id.holder,AuditionFragment())
                        .addToBackStack(null)
                        .commit()
            }
            R.id.nav_profile -> {
                header!!.text = getString(R.string.profile)

                supportFragmentManager.beginTransaction()
                        .replace(R.id.holder,ProfileFragment())
                        .addToBackStack(null)
                        .commit()
            }
            R.id.nav_sign_out -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
