package domain

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.diose.bibliacomposekmp.BibliaDatabase
import org.diose.bibliacomposekmp.Book_table

class BibliaViewModel(private val repository : BibliaDBRepository) : ViewModel() {


    fun getBooks() : List<Book_table>{
        return repository.getBooks()
    }

    fun setBooks(listBooks: List<Book_table>){
        repository.setBooks(listBooks)
    }

    fun setBook(book: Book_table){
        repository.setBook(book)
    }

    fun getBook():Book_table{
        return repository.getBook()
    }


    fun setChapter(chapter: Long){
        repository.setChapter(chapter)
    }

    fun getChapter():Long{
        return repository.getChapter()
    }

    fun getDatabase():BibliaDatabase{
        return repository.getDatabase()
    }

    fun setDatabase(databaseVm: BibliaDatabase){
        repository.setDatabase(databaseVm)
    }

    fun getVerse():Int{
        return repository.getVerse()
    }

    fun setVerse(verse: Int){
        repository.setVerse(verse)
    }

}