package com.chirag.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chirag.notekeeper.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    private var mNotesAdapter: NoteRecyclerAdapter? = null
    private var courseRecyclerAdapter: CourseRecyclerAdapter? = null
    private lateinit var notesLayoutManager: RecyclerView.LayoutManager
    private lateinit var courseLayoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.appBarMain.toolbar)

        val actionBar = this.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        mBinding.appBarMain.fab.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NoteActivity::class.java
                )
            )
        }
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle.isDrawerIndicatorEnabled = true
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_launcher_background)
        }
        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notes, R.id.nav_courses
            ), mBinding.drawerLayout
        )

        initializeDisplayContent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    private fun initializeDisplayContent() {
        notesLayoutManager = LinearLayoutManager(this)
        listItems.layoutManager = notesLayoutManager

        courseLayoutManager = GridLayoutManager(this, 2)

        val notes: ArrayList<NoteInfo>? = DataManager.instance?.notes
        val courses: ArrayList<CourseInfo>? = DataManager.instance?.courses

        mNotesAdapter = this.let { notes?.let { it1 -> NoteRecyclerAdapter(it, it1) } }
        courseRecyclerAdapter = courses?.let { CourseRecyclerAdapter(this, it) }
        listItems.adapter = mNotesAdapter
    }

    override fun onResume() {
        super.onResume()
        mNotesAdapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.nav_notes) {
            displayNotes()
        } else if (id == R.id.nav_courses) {
            displayCourses()
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayNotes() {
        listItems.layoutManager = notesLayoutManager
        listItems.adapter = mNotesAdapter

        selectNavigationMenuItem(R.id.nav_notes)
    }

    private fun selectNavigationMenuItem(id: Int) {
        val menu: Menu = mBinding.navView.menu
        menu.findItem(id).isChecked = true
    }

    private fun displayCourses() {
        listItems.layoutManager = courseLayoutManager
        listItems.adapter = courseRecyclerAdapter

        selectNavigationMenuItem(R.id.nav_courses)
    }
}
