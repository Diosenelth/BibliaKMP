package domain

import org.diose.bibliacomposekmp.BibliaDatabase
import org.diose.bibliacomposekmp.Book_table

interface BibliaDBRepository {
    fun getBooks(): List<Book_table>
    fun setBooks(list: List<Book_table>)

    fun getDatabase(): BibliaDatabase
    fun setDatabase(db : BibliaDatabase)

    fun getBook(): Book_table
    fun setBook(book : Book_table)

    fun getChapter(): Long

    fun setChapter(chapter : Long)

    fun getVerse(): Long

    fun setVerse(verse : Long)
}