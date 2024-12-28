package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muhendisliktasarimi.adapter.WordPairAdapter
import com.example.muhendisliktasarimi.databinding.ActivityAiactivityBinding
import com.example.muhendisliktasarimi.domain.model.WordPair
import com.example.muhendisliktasarimi.util.Constants
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.InputStreamReader

class AIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAiactivityBinding
    private lateinit var wordsPairAdapter: WordPairAdapter
    private lateinit var viewModel: WordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WordsViewModel::class.java]
        wordsPairAdapter = WordPairAdapter(viewModel)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = wordsPairAdapter

        fun fetchData() {
            if (!isNetworkAvailable()) {
                Snackbar.make(binding.root, "İnternet bağlantınızı kontrol edin", Snackbar.LENGTH_SHORT).show()
                binding.sw.isRefreshing = false
                binding.textView6.visibility = View.GONE
                onBackPressedDispatcher.onBackPressed()
                return
            }
            try {
                val animation = ObjectAnimator.ofInt(binding.progressBar4, "progress", 0, 25)
                animation.duration = 2000
                animation.start()

                val englishWords = mutableListOf<String>()

                // CSV dosyasını oku
                val file = File(filesDir, "failed_words.csv")
                if (!file.exists()) {
                    Snackbar.make(binding.root, "Yanlış cevap bulunamadı", Snackbar.LENGTH_SHORT).show()
                    onBackPressedDispatcher.onBackPressed() // Dosya yoksa geri dön
                    return
                }
                InputStreamReader(file.inputStream()).use { reader ->
                    val csvParser = CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())
                    for (record: CSVRecord in csvParser) {
                        val englishWord = record.get("English word")
                        englishWords.add(englishWord)
                    }
                }

                val sentence = "${englishWords.joinToString(", ")} ${Constants.WORD_QUESTİON}"
                println(sentence + "sentence")

                CoroutineScope(Dispatchers.Main).launch {
                    val animation = ObjectAnimator.ofInt(binding.progressBar4, "progress", 0, 100)
                    animation.duration = 2000
                    animation.start()

                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = "Constants.AI_API_KEY"
                    )

                    val response = generativeModel.generateContent(sentence)
                    println(response)
                    val responseText = response.text?.trimIndent()
                    println(responseText+ "cevap")
                    try {
                        if (!responseText.isNullOrEmpty()) {
                            // Cevabı satır bazında bölüp her satırı kontrol et
                            val wordsAfterSpace = responseText
                                .split("\n")
                                .take(5)
                                .mapNotNull { line ->
                                    val pair = line.split("-").map { it.trim() }
                                    if (pair.size == 2) WordPair(pair[0], pair[1]) else null
                                }

                            if (wordsAfterSpace.isNotEmpty()) {
                                wordsPairAdapter.wordsPairList = wordsAfterSpace
                                binding.rv.adapter = wordsPairAdapter
                                Snackbar.make(binding.root, "Yeni kelimeler bulundu", Snackbar.LENGTH_SHORT).show()
                            } else {
                                showNoWordsFoundSnack()
                            }
                        } else {
                            showNoWordsFoundSnack()
                        }

                    } catch (e: Exception) {
                        Snackbar.make(binding.root, "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
                    } finally {
                        binding.progressBar4.visibility = View.GONE
                        binding.textView6.visibility = View.GONE
                        binding.sw.isRefreshing = false
                    }
                }

            } catch (e: Exception) {
                Snackbar.make(binding.root, "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
                binding.progressBar4.visibility = View.GONE
                binding.textView6.visibility = View.GONE
                binding.sw.isRefreshing = false
            }
        }

        fetchData()
        binding.sw.setOnRefreshListener {
            fetchData()
        }
    }

    private fun showNoWordsFoundSnack() {
        Snackbar.make(binding.root, "Yeni kelime bulunamadı", Snackbar.LENGTH_SHORT).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
