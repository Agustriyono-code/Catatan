package com.istiqomah.catatan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.istiqomah.catatan.databinding.ActivityMainBinding
import com.istiqomah.catatan.model.InternalFileRepository
import com.istiqomah.catatan.model.Note
import com.istiqomah.catatan.model.NoteRepository

class MainActivity : AppCompatActivity() {
    //perrintah yg terhubung ke class kotlin note repository dan internal file repository
    private val repo: NoteRepository by lazy { InternalFileRepository(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //terhubung ke binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //jika tombol ditulis maka halaman sudah siap dalam beroperasi
        binding.btnWrite.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    repo.addNote(
                        Note(
                            binding.editFileName.text.toString(),
                            binding.editTeksCatatan.text.toString()
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "File Write Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
        //jika tombol baca di klik maka akan muncul catatn yg telah di masukan dgn syarat nama file dimasukan
        binding.btnRead.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    val note = repo.getNote(binding.editFileName.text.toString())
                    binding.editTeksCatatan.setText(note.noteText)
                } catch (e: Exception) {
                    Toast.makeText(this, "File Read Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
        //jika tombol delete di klik maka akan data catatan akan langsung terhapus semua
        binding.btnDelete.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    if (repo.deleteNote(binding.editFileName.text.toString())) {
                        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "File Could Not Be Deleted", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "File Delete Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
    }
}