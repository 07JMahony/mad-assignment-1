package org.wit.library.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
        refreshList()
    }

    /** Redraws the list and swaps in the empty-state message when there is nothing to show. */
    private fun refreshList() {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        val isEmpty = app.books.findAll().isEmpty()
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Nothing to clear when the library is already empty.
        menu.findItem(R.id.item_clear).isVisible = app.books.findAll().isNotEmpty()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BookActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_clear -> confirmClear()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmClear() {
        AlertDialog.Builder(this)
            .setMessage(R.string.confirm_clearLibrary)
            .setPositiveButton(R.string.action_clear) { _, _ ->
                app.books.deleteAll()
                refreshList()
            }
            .setNegativeButton(R.string.action_cancel, null)
            .show()
    }

    override fun onBookClick(book: BookModel) {
        val launcherIntent = Intent(this, BookActivity::class.java)
        launcherIntent.putExtra(BookActivity.EXTRA_BOOK, book)
        getResult.launch(launcherIntent)
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                refreshList()
            }
        }
}
