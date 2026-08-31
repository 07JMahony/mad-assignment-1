package org.wit.library.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
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
    private var editing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = getString(R.string.title_addBook)
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        // NumberPicker has no XML attributes for its bounds, so set them here.
        binding.bookRating.minValue = 0
        binding.bookRating.maxValue = 5

        // Arriving with a book attached means we are editing rather than adding.
        if (intent.hasExtra(EXTRA_BOOK)) {
            editing = true
            book = intent.extras?.getParcelable(EXTRA_BOOK)!!
            binding.bookTitle.setText(book.title)
            binding.bookAuthor.setText(book.author)
            selectGenre(book.genre)
            binding.bookRating.value = book.rating
            binding.bookRead.isChecked = book.read
            binding.btnAdd.setText(R.string.button_saveBook)
            binding.toolbarAdd.title = getString(R.string.title_editBook)
        }

        binding.btnAdd.setOnClickListener {
            // Trim first so a field of spaces is treated as empty, not as a valid title.
            book.title = binding.bookTitle.text.toString().trim()
            book.author = binding.bookAuthor.text.toString().trim()
            book.genre = binding.bookGenre.selectedItem.toString()
            book.rating = binding.bookRating.value
            book.read = binding.bookRead.isChecked
            when {
                book.title.isEmpty() ->
                    Snackbar.make(it, R.string.enter_book_title, Snackbar.LENGTH_LONG).show()
                book.author.isEmpty() ->
                    Snackbar.make(it, R.string.enter_book_author, Snackbar.LENGTH_LONG).show()
                else -> {
                    if (editing) {
                        app.books.update(book.copy())
                    } else {
                        app.books.create(book.copy())
                    }
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // There is nothing to delete until the book actually exists.
        menu.findItem(R.id.item_delete).isVisible = editing
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
                return true
            }
            R.id.item_delete -> {
                confirmDelete()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** Moves the spinner to a saved genre, leaving it on the default if it is not in the list. */
    private fun selectGenre(genre: String) {
        val position = resources.getStringArray(R.array.genres).indexOf(genre)
        if (position >= 0) {
            binding.bookGenre.setSelection(position)
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setMessage(R.string.confirm_deleteBook)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                app.books.delete(book)
                setResult(RESULT_OK)
                finish()
            }
            .setNegativeButton(R.string.action_cancel, null)
            .show()
    }

    companion object {
        const val EXTRA_BOOK = "book_edit"
    }
}
