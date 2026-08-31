package org.wit.library.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.library.R
import org.wit.library.databinding.CardBookBinding
import org.wit.library.models.BookModel

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
            binding.bookGenre.text = book.genre
            binding.bookRating.text = if (book.rating == 0) {
                binding.root.context.getString(R.string.rating_none)
            } else {
                "★".repeat(book.rating)
            }
            binding.bookStatus.setText(
                if (book.read) R.string.status_read else R.string.status_unread
            )
            binding.root.setOnClickListener { listener.onBookClick(book) }
        }
    }
}
