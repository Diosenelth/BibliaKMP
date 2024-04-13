package domain

import org.diose.bibliacomposekmp.BibliaDatabase
import org.diose.bibliacomposekmp.Book_table

private var listBook : List<Book_table> = listOf()
private var bookImpl : Book_table? = null
private var chapterImpl : Long = 1
private var database : BibliaDatabase? = null
private var verseImpl : Long = 0
class RepositoryImpl : BibliaDBRepository {
    override fun getBooks(): List<Book_table> {
        return listBook
    }

    override fun setBooks(list : List<Book_table> ){
        listBook = list
    }

    override fun getDatabase(): BibliaDatabase {
        return database!!
    }

    override fun setDatabase(db: BibliaDatabase) {
        database = db
    }

    override fun getBook(): Book_table {
        return bookImpl!!
    }

    override fun setBook(book: Book_table) {
        bookImpl = book
    }

    override fun getChapter(): Long {
        return chapterImpl
    }

    override fun setChapter(chapter: Long) {
        chapterImpl = chapter
    }

    override fun getVerse(): Long {
        return verseImpl
    }

    override fun setVerse(verse: Long) {
        verseImpl = verse
    }
}