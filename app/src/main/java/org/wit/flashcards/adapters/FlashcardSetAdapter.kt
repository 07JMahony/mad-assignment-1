package org.wit.flashcards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.flashcards.databinding.CardFlashcardSetBinding
import org.wit.flashcards.models.FlashcardSetModel

interface FlashcardSetListener {
    fun onFlashcardSetClick(flashcardSet: FlashcardSetModel)
}

class FlashcardSetAdapter(private var flashcardSets: List<FlashcardSetModel>,
                          private val listener: FlashcardSetListener) :
    RecyclerView.Adapter<FlashcardSetAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFlashcardSetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val flashcardSet = flashcardSets[holder.adapterPosition]
        holder.bind(flashcardSet, listener)
    }

    override fun getItemCount(): Int = flashcardSets.size

    class MainHolder(private val binding : CardFlashcardSetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(flashcardSet: FlashcardSetModel, listener: FlashcardSetListener) {
            binding.setName.text = flashcardSet.name
            binding.description.text = flashcardSet.description
            binding.root.setOnClickListener { listener.onFlashcardSetClick(flashcardSet) }
        }
    }
}