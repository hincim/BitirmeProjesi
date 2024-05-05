package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySolveQuestionBinding
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class SolveQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolveQuestionBinding
    private lateinit var viewModel: WordsViewModel

    private lateinit var questions : Deferred<List<Words>>
    private var questionsSize = 0
    private lateinit var falseOptions: List<Words>
    private lateinit var correctQuestion: Words
    private lateinit var allOptions:HashSet<Words>
    private var questionCounter = 0
    private var correctCounter = 0
    private var falseCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolveQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WordsViewModel::class.java]

        questions = viewModel.getRandomWord()
        allOptions = HashSet()

        uploadQuestion()

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

    private fun uploadQuestion(){
        binding.buttonAnswer1.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer2.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer3.setBackgroundResource(R.drawable.rounded_button)
        binding.buttonAnswer4.setBackgroundResource(R.drawable.rounded_button)

        binding.questionCount.text = "${questionCounter+1}. Soru"

        runBlocking {
            correctQuestion = questions.await()[questionCounter]
            questionsSize = questions.await().size
            falseOptions = viewModel.getRandomOptionsWord(correctQuestion.uuid).await()
        }
        binding.textViewQuestion.text = correctQuestion.engWord
        allOptions = HashSet()
        allOptions.add(correctQuestion)
        allOptions.add(falseOptions[0])
        allOptions.add(falseOptions[1])
        allOptions.add(falseOptions[2])
        println(allOptions)


        binding.buttonAnswer1.text = allOptions.elementAt(0).trWord
        binding.buttonAnswer2.text = allOptions.elementAt(1).trWord
        binding.buttonAnswer3.text = allOptions.elementAt(2).trWord
        binding.buttonAnswer4.text = allOptions.elementAt(3).trWord
       /* viewModel.randomWords.observe(this, Observer {
            it?.let {

                questions = it
                if (questions.size<5){
                    finish()
                    Toast.makeText(this,"En az 5 kelime kaydedin", Toast.LENGTH_SHORT).show()
                 return@Observer
                }
                correctQuestion = questions[questionCounter]
                binding.questionCount.text = "${questionCounter + 1} / ${questions.size}"

                binding.textViewQuestion.text = correctQuestion.engWord

                viewModel.getRandomOptionsWord(correctQuestion.uuid)

            }

        })

        viewModel.randomOptionsWords.observe(this, Observer { res ->
            res?.let {
                println("Tüm seçenekler")
                println(res)
                println("Soru")
                println(correctQuestion.engWord)
                falseOptions = res

                allOptions.add(correctQuestion)
                allOptions.add(falseOptions[0])
                allOptions.add(falseOptions[1])
                allOptions.add(falseOptions[2])


                val options = allOptions.shuffled()

                binding.buttonAnswer1.text = options.elementAt(0).trWord
                binding.buttonAnswer2.text = options.elementAt(1).trWord
                binding.buttonAnswer3.text = options.elementAt(2).trWord
                binding.buttonAnswer4.text = options.elementAt(3).trWord
                println("karıştırılmış")
                println(options)
            }
        })*/
    }
    private fun questionCountControl(view: View){

        questionCounter ++

        if (questionCounter != questionsSize){
            uploadQuestion()
        }else{

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
                binding.falseCount.text = "Yanlış: $falseCounter"
                return false
            }

    }
}