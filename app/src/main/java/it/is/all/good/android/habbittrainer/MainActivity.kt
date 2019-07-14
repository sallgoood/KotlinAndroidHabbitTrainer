package it.`is`.all.good.android.habbittrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import it.`is`.all.good.android.habbittrainer.repository.HabbitRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adapter -> defines data
        // RecyclerView -> implement 3 methods
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = HabbitAdapter(HabbitRepository(this).findAllHabbits())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_habbit) {
            switchContext(AddHabbitActivity::class.java)
        }

        return true
    }

    private fun switchContext(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }
}
