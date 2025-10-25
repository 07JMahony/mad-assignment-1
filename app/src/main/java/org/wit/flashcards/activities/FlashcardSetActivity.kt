package org.wit.flashcards.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.flashcards.R
import org.wit.flashcards.databinding.ActivityFlashcardSetBinding
import org.wit.flashcards.main.MainApp
import org.wit.flashcards.models.FlashcardSetModel
import timber.log.Timber.i

class FlashcardSetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardSetBinding
    var flashcardSet = FlashcardSetModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityFlashcardSetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("flashcard_set_edit")) {
            edit = true
            flashcardSet = intent.extras?.getParcelable("flashcard_set_edit")!!
            binding.setName.setText(flashcardSet.name)
            binding.description.setText(flashcardSet.description)
            binding.btnAdd.setText(R.string.save_set)
        }

        binding.btnAdd.setOnClickListener() {
            flashcardSet.name = binding.setName.text.toString()
            flashcardSet.description = binding.description.text.toString()
            if (flashcardSet.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_set_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.flashcardSets.update(flashcardSet.copy())
                } else {
                    app.flashcardSets.create(flashcardSet.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_flashcard_set, menu)
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