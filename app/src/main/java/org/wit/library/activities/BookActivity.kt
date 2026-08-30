package org.wit.library.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.library.R
import org.wit.library.databinding.ActivityBookBinding
import org.wit.library.main.MainApp
import org.wit.library.models.BookModel

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var app: MainApp
    private var book = BookModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        binding.btnAdd.setOnClickListener {
            book.title = binding.bookTitle.text.toString()
            book.author = binding.bookAuthor.text.toString()
            if (book.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_book_title, Snackbar.LENGTH_LONG).show()
            } else {
                app.books.create(book.copy())
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
