package org.wit.flashcards.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.flashcards.R
import org.wit.flashcards.adapters.FlashcardSetAdapter
import org.wit.flashcards.adapters.FlashcardSetListener
import org.wit.flashcards.databinding.ActivityFlashcardSetListBinding
import org.wit.flashcards.main.MainApp
import org.wit.flashcards.models.FlashcardSetModel

class FlashcardSetListActivity : AppCompatActivity(), FlashcardSetListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFlashcardSetListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardSetListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FlashcardSetAdapter(app.flashcardSets.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FlashcardSetActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0, app.flashcardSets.findAll().size)
            }
        }

    override fun onFlashcardSetClick(flashcardSet: FlashcardSetModel) {
        val launcherIntent = Intent(this, FlashcardSetActivity::class.java)
        launcherIntent.putExtra("flashcard_set_edit", flashcardSet)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0, app.flashcardSets.findAll().size)
            }
        }
}