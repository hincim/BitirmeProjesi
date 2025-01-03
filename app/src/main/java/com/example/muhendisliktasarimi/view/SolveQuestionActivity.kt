package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySolveQuestionBinding
import com.example.muhendisliktasarimi.domain.model.FalseWords
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SolveQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolveQuestionBinding
    private lateinit var viewModel: WordsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var questions : Deferred<List<Words>>
    private var questionsSize = 0
    private lateinit var falseOptions: List<Words>
    private lateinit var correctQuestion: Words
    private lateinit var allOptions:HashSet<Words>
    private var questionCounter = 0
    private var correctCounter = 0
    private var falseCounter = 0
    var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolveQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        fireStore = Firebase.firestore
        viewModel = ViewModelProvider(this)[WordsViewModel::class.java]

        questions = viewModel.getRandomWord()
        allOptions = HashSet()
        readCSVFile()
        deleteCSVFile()
        uploadQuestion()
        val username = auth.currentUser?.email?.substringBefore('@')
        binding.textViewUserName.text = "Kullanıcı adı: "+username

        ObjectAnimator.ofFloat(binding.textViewUserName,"translationY",-1500.0f, 0.0f).apply {
            duration = 1000

        }.start()

        ObjectAnimator.ofFloat(binding.buttonAnswer1,"translationX",1500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()

        }.start()
        ObjectAnimator.ofFloat(binding.buttonAnswer2,"translationX",-1500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()

        }.start()
        ObjectAnimator.ofFloat(binding.buttonAnswer3,"translationX",1500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()

        }.start()
        ObjectAnimator.ofFloat(binding.buttonAnswer4,"translationX",-1500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()

        }.start()

        binding.buttonAnswer1.setOnClickListener {

            closeButton(it)
            closeButton(binding.buttonAnswer2)
            closeButton(binding.buttonAnswer3)
            closeButton(binding.buttonAnswer4)

            val value = correctControl(binding.buttonAnswer1)
            if (value){
                it.setBackgroundResource(R.drawable.rounded_button_green)
            }else{
                it.setBackgroundResource(R.drawable.rounded_button_red)
            }
            if (correctQuestion.trWord == binding.buttonAnswer1.text){
                binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button_green)
            }else if (correctQuestion.trWord == binding.buttonAnswer2.text){
                binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button_green)

            }else if(correctQuestion.trWord == binding.buttonAnswer3.text){
                binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button_green)

            }else{
                binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button_green)
            }
            object : CountDownTimer(600,300){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    questionCountControl(it)
                }

            }.start()

        }
        binding.buttonAnswer2.setOnClickListener {
            closeButton(binding.buttonAnswer1)
            closeButton(it)
            closeButton(binding.buttonAnswer3)
            closeButton(binding.buttonAnswer4)

            val value = correctControl(binding.buttonAnswer2)
            if (value){
                it.setBackgroundResource(R.drawable.rounded_button_green)
            }else{
                it.setBackgroundResource(R.drawable.rounded_button_red)
            }
            if (correctQuestion.trWord == binding.buttonAnswer1.text){
                binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button_green)
            }else if (correctQuestion.trWord == binding.buttonAnswer2.text){
                binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button_green)

            }else if(correctQuestion.trWord == binding.buttonAnswer3.text){
                binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button_green)

            }else{
                binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button_green)
            }
            object : CountDownTimer(600,300){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    questionCountControl(it)
                }

            }.start()

        }
        binding.buttonAnswer3.setOnClickListener {
            closeButton(binding.buttonAnswer1)
            closeButton(binding.buttonAnswer2)
            closeButton(it)
            closeButton(binding.buttonAnswer4)
            val value = correctControl(binding.buttonAnswer3)
            if (value){
                it.setBackgroundResource(R.drawable.rounded_button_green)
            }else{
                it.setBackgroundResource(R.drawable.rounded_button_red)
            }
            if (correctQuestion.trWord == binding.buttonAnswer1.text){
                binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button_green)
            }else if (correctQuestion.trWord == binding.buttonAnswer2.text){
                binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button_green)

            }else if(correctQuestion.trWord == binding.buttonAnswer3.text){
                binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button_green)

            }else{
                binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button_green)
            }
            object : CountDownTimer(600,300){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    questionCountControl(it)
                }

            }.start()

        }
        binding.buttonAnswer4.setOnClickListener {
            closeButton(binding.buttonAnswer1)
            closeButton(binding.buttonAnswer2)
            closeButton(binding.buttonAnswer3)
            closeButton(it)

            val value = correctControl(binding.buttonAnswer4)
            if (value){
                it.setBackgroundResource(R.drawable.rounded_button_green)
            }else{
                it.setBackgroundResource(R.drawable.rounded_button_red)
            }
            if (correctQuestion.trWord == binding.buttonAnswer1.text){
                binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button_green)
            }else if (correctQuestion.trWord == binding.buttonAnswer2.text){
                binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button_green)

            }else if(correctQuestion.trWord == binding.buttonAnswer3.text){
                binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button_green)

            }else{
                binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button_green)
            }
            object : CountDownTimer(600,300){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    questionCountControl(it)
                }

            }.start()

        }

    }
    private fun deleteCSVFile() {
        val file = File(filesDir, "failed_words.csv")
        if (file.exists()) {
            file.delete() // Dosya her girişte sıfırlanır
        }
    }

    private fun readCSVFile() {
        val file = File(filesDir, "failed_words.csv")
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                val wordsList = lines.drop(1).map { line ->
                    val tokens = line.split(";")
                    val englishWord = tokens[0]
                    val turkishWord = tokens[1]
                    val difficultyLevel = tokens[2].toInt()
                    FalseWords(englishWord, turkishWord, difficultyLevel)
                }.toList()
                wordsList.forEach { words ->
                    println("İngilizce: ${words.engWord}, Türkçe: ${words.trWord}, Zorluk: ${words.difficultyLevel}")
                }
            }
        } else {

        }
    }    private fun saveWrongAnswerToCSV() {
        val file = File(filesDir, "failed_words.csv")
        val fos = openFileOutput("failed_words.csv", MODE_APPEND)

        try {
            // Eğer dosya yeni oluşturulduysa başlık ekle
            if (!file.exists()|| file.length() == 0L) {
                val header = "English word;Turkish word;Difficulty Level\n"
                fos.write(header.toByteArray())
            }

            // Zorluk seviyesini kelimenin uzunluğuna göre belirleme
            val difficultyLevel = when (correctQuestion.engWord?.length) {
                in 1..4 -> 1
                in 5..7 -> 2
                else -> 3
            }

            // Kelime verisini ekle
            val data = "${correctQuestion.engWord};${correctQuestion.trWord};$difficultyLevel\n"
            fos.write(data.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos.close()
        }
    }
    private fun uploadQuestion() {
        binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button)
        openButton(binding.buttonAnswer1)
        openButton(binding.buttonAnswer2)
        openButton(binding.buttonAnswer3)
        openButton(binding.buttonAnswer4)

        runBlocking {
            correctQuestion = questions.await()[questionCounter]
            questionsSize = questions.await().size
            falseOptions = viewModel.getRandomOptionsWord(correctQuestion.uuid).await()
        }
        binding.questionCount.text = "${questionCounter+1}/${questionsSize}"

        binding.textViewQuestion.text = correctQuestion.engWord
        allOptions = HashSet()
        allOptions.add(correctQuestion)
        allOptions.add(falseOptions[0])
        allOptions.add(falseOptions[1])
        allOptions.add(falseOptions[2])

        // Şıkları güncelle
        binding.buttonAnswer1.text = allOptions.elementAt(0).trWord
        binding.buttonAnswer2.text = allOptions.elementAt(1).trWord
        binding.buttonAnswer3.text = allOptions.elementAt(2).trWord
        binding.buttonAnswer4.text = allOptions.elementAt(3).trWord
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun questionCountControl(view: View){

        questionCounter ++

        if (questionCounter != questionsSize){
            uploadQuestion()
        }else{
            ObjectAnimator.ofFloat(binding.textViewUserName,"translationY",0.0f, -1500.0f).apply {
                duration = 1000

            }.start()
            ObjectAnimator.ofFloat(binding.buttonAnswer1,"translationX", 0.0f, 1500.0f).apply {
                duration = 700
                interpolator  = OvershootInterpolator()

            }.start()
            ObjectAnimator.ofFloat(binding.buttonAnswer2,"translationX", 0.0f, -1500.0f).apply {
                duration = 700
                interpolator  = OvershootInterpolator()

            }.start()
            ObjectAnimator.ofFloat(binding.buttonAnswer3,"translationX", 0.0f, 1500.0f).apply {
                duration = 700
                interpolator  = OvershootInterpolator()

            }.start()
            ObjectAnimator.ofFloat(binding.buttonAnswer4,"translationX", 0.0f, -1500.0f).apply {
                duration = 700

            }.start()
            object : CountDownTimer(600,300){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    val formattedDateTime = currentDateTime.format(formatter)
                    val scoreMap = hashMapOf<String, Any>()
                    scoreMap.put("score","${(correctCounter * 100)/questionsSize}")
                    scoreMap.put("userEmail",auth.currentUser?.email.toString())
                    scoreMap.put("date", formattedDateTime)
                    fireStore.collection("Score").add(scoreMap).addOnSuccessListener {
                        Snackbar.make(binding.root, "Skor kaydedildi", Snackbar.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this@SolveQuestionActivity,ResultActivity::class.java)
                    intent.putExtra("correctCounter",correctCounter)
                    intent.putExtra("questionSize",questionsSize)
                    startActivity(intent)
                    finish()
                }

            }.start()


        }
    }
    private fun correctControl(button: Button): Boolean {
        val buttonText = button.text
        val correctAnswer = correctQuestion.trWord
        binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button)

            if (buttonText == correctAnswer) {
                correctCounter++
                binding.correctCount.text = "Doğru: $correctCounter"
                return true
            } else {
                falseCounter++
                println("$correctAnswer fdfdfdfd")
                println("$buttonText fdfdfdfd")

                binding.falseCount.text = "Yanlış: $falseCounter"
//                try {
//                    if (correctQuestion.engWord?.length in 1..4){
//                        val file = File(filesDir, "failed_words.csv")
//
//                        if (!file.exists()) {
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "Ingilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 1 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }else{
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "Ingilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//
//
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 1 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }
//
//
//                    }else if (correctQuestion.engWord?.length in 5..7){
//
//                        val file = File(filesDir, "failed_words.csv")
//
//                        if (!file.exists()) {
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "İngilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 1 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }else{
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "Ingilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//
//
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 2 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }
//
//
//                    }else{
//
//                        val file = File(filesDir, "failed_words.csv")
//
//                        if (!file.exists()) {
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "İngilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 3 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }else{
//                            try {
//                                val fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                                val header = "İngilizce Kelime"+";"+"Turkcesi"+";"+"Zorluk Seviyesi"+"\n"
//                                fos.write(header.toByteArray())
//                                fos.close()
//                            } catch (e: java.lang.Exception) {
//                                e.printStackTrace()
//                            }
//                            data = correctQuestion.engWord + ";" + correctQuestion.trWord + ";" + 3 + "\n";
//                            fos = openFileOutput("failed_words.csv", MODE_APPEND)
//                            fos?.write(data.toByteArray())
//                            fos?.close()
//                        }
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()  // Hataları yakala ve yazdır
//                }
                saveWrongAnswerToCSV()
                return false
            }

    }

    private fun closeButton(button: View){
        button.isEnabled = false
    }
    private fun openButton(button: Button){
        button.isEnabled = true
    }
}