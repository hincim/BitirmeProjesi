package com.example.muhendisliktasarimi.view

import DictionaryResponse
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.data.remote.dto.WordsAPI
import com.example.muhendisliktasarimi.data.remote.dto.search_words.Tr
import com.example.muhendisliktasarimi.databinding.FragmentWordAddBinding
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.util.Constants
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WordAddFragment : Fragment(R.layout.fragment_word_add) {

    private var _fragmentBinding: FragmentWordAddBinding? = null
    private lateinit var viewModel: WordsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWordAddBinding.bind(view)
        _fragmentBinding = binding


        val sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            // Eğer uygulama yeni başlatıldıysa animasyonu başlat
            ObjectAnimator.ofFloat(binding.welcomeBack, "alpha", -3.0f, 1.0f).apply {
                duration = 1000
            }.start()

            // Bir daha animasyonun oynatılmaması için isFirstTime değerini false yap
            with(sharedPref.edit()) {
                putBoolean("isFirstTime", false)
                apply()
            }
        } else {
            // Animasyon oynatılmayacaksa, yazıyı sabit göster
            binding.welcomeBack.alpha = 1.0f
        }


        viewModel = ViewModelProvider(requireActivity())[WordsViewModel::class.java]
        /*
                    binding.fabBack.setOnClickListener {
                    findNavController().popBackStack()
                }*/

        binding.progressBar6.visibility = View.GONE

        binding.imageButton.setOnClickListener {

            val tempText = binding.editTextEng.text.toString()
            binding.editTextEng.setText(binding.editTextTr.text.toString())
            binding.editTextTr.setText(tempText)

            val engY = binding.editTextEng.y
            val trY = binding.editTextTr.y

            // Animasyon oluştur
            val animEng = ObjectAnimator.ofFloat(binding.editTextEng, "y", trY)
            val animTr = ObjectAnimator.ofFloat(binding.editTextTr, "y", engY)

            val currentRotation = binding.imageButton.rotation
            val rotateAnim = ObjectAnimator.ofFloat(binding.imageButton, "rotation", currentRotation, currentRotation + 180f)
            // Animasyonları birleştir
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animEng, animTr, rotateAnim)
            animatorSet.duration = 500 // 500ms animasyon süresi
            animatorSet.start()


            animatorSet.addListener(object : android.animation.Animator.AnimatorListener {

                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.editTextEng.text.clear()
                    binding.editTextTr.text.clear()

                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })


        }
        binding.buttonSave.setOnClickListener {
            val eng = binding.editTextEng.text.toString().trim().lowercase()
            val tr = binding.editTextTr.text.toString().trim().lowercase()
            if (eng.isEmpty() || tr.isEmpty()) {
                Snackbar.make(binding.root, "Boş değer girilmemeli", Snackbar.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val exists = viewModel.checkIfWordExists(eng)
                    withContext(Dispatchers.Main) {
                        if (!exists) {
                            viewModel.saveInSQLiteWord(Words(eng, tr))
                            Snackbar.make(binding.root, "Kelime eklendi", Snackbar.LENGTH_SHORT).show()
                            Navigation.findNavController(it).popBackStack()
                        } else {
                            Snackbar.make(binding.root, "Kelime zaten mevcut", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                }

//                val listWords = arrayListOf<Words>()
//                val words = Words(eng, tr)
//                listWords.add(words)
//                viewModel.saveInSQLite(listWords)
            }
        }

        fun fetchTranslation(text: String, direction: String) {
            if (text.isEmpty()) {
                Snackbar.make(binding.root, "Kelime giriniz", Snackbar.LENGTH_SHORT).show()
                return
            }
            binding.progressBar6.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.WORD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(WordsAPI::class.java)

            val call = service.getTranslation(direction, text)

            try {
                call.enqueue(object : Callback<DictionaryResponse> {
                    override fun onResponse(
                        call: Call<DictionaryResponse>,
                        response: Response<DictionaryResponse>
                    ) {
                        if (response.isSuccessful) {
                            val dictionaryResponse = response.body()
                            if (dictionaryResponse?.def != null && dictionaryResponse.def.isNotEmpty()) {
                                val firstDef = dictionaryResponse.def[0]
                                if (firstDef.tr != null && firstDef.tr.isNotEmpty()) {
                                    binding.progressBar6.visibility = View.GONE
                                    showCustomDialogBox(firstDef.tr)
                                } else {
                                    binding.progressBar6.visibility = View.GONE
                                    Snackbar.make(binding.root, "Çeviri bulunamadı", Snackbar.LENGTH_SHORT).show()
                                }
                            } else {
                                binding.progressBar6.visibility = View.GONE
                                Snackbar.make(binding.root, "Kelime bulunamadı", Snackbar.LENGTH_SHORT).show()
                            }
                        } else {
                            binding.progressBar6.visibility = View.GONE
                            Snackbar.make(binding.root, "Hata", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DictionaryResponse>, t: Throwable) {
                        binding.progressBar6.visibility = View.GONE
                        Snackbar.make(binding.root, "İnternet bağlantısında sorun var", Snackbar.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                binding.progressBar6.visibility = View.GONE
                Snackbar.make(binding.root, "Kelime bulunamadı", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.buttonSearch.setOnClickListener {
            val engY = binding.editTextEng.y // İngilizce EditText'in y konumu
            val trY = binding.editTextTr.y   // Türkçe EditText'in y konumu

            val (translationDirection, searchText) = if (engY < trY) {
                // İngilizce EditText üstte
                "en-tr" to binding.editTextEng.text.toString().trim()
            } else {
                // Türkçe EditText üstte
                "tr-en" to binding.editTextTr.text.toString().trim()
            }

            if (searchText.isEmpty()) {
                Snackbar.make(binding.root, "Lütfen arama yapmak için kelime giriniz", Snackbar.LENGTH_SHORT).show()
            } else {
                fetchTranslation(searchText, translationDirection)
            }
        }

    }

    private fun showCustomDialogBox(trList: List<Tr>) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog_word)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.d("TRANSLATIONS", trList.toString())

        val word2: TextView = dialog.findViewById(R.id.word2)
        val word3: TextView = dialog.findViewById(R.id.word3)
        val word4: TextView = dialog.findViewById(R.id.word4)
        val word5: TextView = dialog.findViewById(R.id.word5)
        val btnWord2: Button = dialog.findViewById(R.id.btnWord2)
        val btnWord3: Button = dialog.findViewById(R.id.btnWord3)
        val btnWord4: Button = dialog.findViewById(R.id.btnWord4)
        val btnWord5: Button = dialog.findViewById(R.id.btnWord5)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
        val word3Layout: View = dialog.findViewById(R.id.word3Layout)
        val word4Layout: View = dialog.findViewById(R.id.word4Layout)
        val word5Layout: View = dialog.findViewById(R.id.word5Layout)

        if (trList.size >= 4) {
            word5.text = "${trList[3].text.lowercase()}"
            word4.text = "${trList[2].text.lowercase()}"
            word3.text = "${trList[1].text.lowercase()}"
            word2.text = "${trList[0].text.lowercase()}"
        }else if (trList.size == 3) {
            word4.text = "${trList[2].text.lowercase()}"
            word3.text = "${trList[1].text.lowercase()}"
            word2.text = "${trList[0].text.lowercase()}"
            word5Layout.visibility = View.GONE
        }else if (trList.size == 2) {
            word3.text = "${trList[1].text.lowercase()}"
            word2.text = "${trList[0].text.lowercase()}"
            word5Layout.visibility = View.GONE
            word4Layout.visibility = View.GONE
        }else if (trList.size == 1) {
            word2.text = "${trList[0].text.lowercase()}"
            word5Layout.visibility = View.GONE
            word4Layout.visibility = View.GONE
            word3Layout.visibility = View.GONE
        }


        val isTrOnTop = _fragmentBinding?.editTextTr!!.y < _fragmentBinding?.editTextEng!!.y

        btnWord5.setOnClickListener {
            if (isTrOnTop) {
                _fragmentBinding?.editTextEng?.setText(word5.text)
            } else {
                _fragmentBinding?.editTextTr?.setText(word5.text)
            }
            dialog.dismiss()
        }
        btnWord4.setOnClickListener {
            if (isTrOnTop) {
                _fragmentBinding?.editTextEng?.setText(word4.text)
            } else {
                _fragmentBinding?.editTextTr?.setText(word4.text)
            }
            dialog.dismiss()
        }
        btnWord3.setOnClickListener {
            if (isTrOnTop) {
                _fragmentBinding?.editTextEng?.setText(word3.text)
            } else {
                _fragmentBinding?.editTextTr?.setText(word3.text)
            }
            dialog.dismiss()
        }
        btnWord2.setOnClickListener {
            if (isTrOnTop) {
                _fragmentBinding?.editTextEng?.setText(word2.text)
            } else {
                _fragmentBinding?.editTextTr?.setText(word2.text)
            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        _fragmentBinding = null
        super.onDestroy()
    }

}