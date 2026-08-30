package org.wit.shelfie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.shelfie.databinding.CardBookBinding
import org.wit.shelfie.models.BookModel

interface BookListener {
    fun onBookClick(book: BookModel)
}

class BookAdapter(private var books: List<BookModel>,
                  private val listener: BookListener) :
    RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = books[position]
        holder.bind(book, listener)
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding: CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel, listener: BookListener) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.root.setOnClickListener { listener.onBookClick(book) }
        }
    }
}
