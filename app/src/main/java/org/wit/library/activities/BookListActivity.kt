package org.wit.library.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.library.R
import org.wit.library.adapters.BookAdapter
import org.wit.library.adapters.BookListener
import org.wit.library.databinding.ActivityBookListBinding
import org.wit.library.main.MainApp
import org.wit.library.models.BookModel

class BookListActivity : AppCompatActivity(), BookListener {

    private lateinit var app: MainApp
    private lateinit var binding: ActivityBookListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = BookAdapter(app.books.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BookActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBookClick(book: BookModel) {
        val launcherIntent = Intent(this, BookActivity::class.java)
        launcherIntent.putExtra(BookActivity.EXTRA_BOOK, book)
        getResult.launch(launcherIntent)
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
}
