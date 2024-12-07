package com.example.muhendisliktasarimi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySignUpBinding
import com.example.muhendisliktasarimi.databinding.ActivitySolveQuestionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
        binding.passwordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
        binding.passwordAgainLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val passwordAgain = binding.passwordAgainInput.text.toString().trim()


            if (email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()){
                if (password == passwordAgain){
                    binding.progressBar3.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnFailureListener {
                            Snackbar.make(binding.root, "Hata", Snackbar.LENGTH_SHORT).show()
                            binding.linearLayout.visibility = View.VISIBLE
                            binding.progressBar3.visibility = View.GONE
                        }
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                binding.progressBar3.visibility = View.GONE
                                startActivity(Intent(this,MainActivity::class.java))
                                ActivityCompat.finishAffinity(this)
                            }else if(it.exception!!.message == "The email address is already in use by another account."){
                                Snackbar.make(binding.root, "Aynı hesapla kullanıcı var", Snackbar.LENGTH_SHORT).show()
                                binding.linearLayout.visibility = View.VISIBLE
                            }else if (it.exception!!.message == "The email address is badly formatted."){
                                Snackbar.make(binding.root, "Geçerli bir email adresi girin", Snackbar.LENGTH_SHORT).show()
                            }else{
                                Snackbar.make(binding.root, "Hata", Snackbar.LENGTH_SHORT).show()
                                binding.linearLayout.visibility = View.VISIBLE
                            }
                        }
                }else{
                    Snackbar.make(binding.root, "Şifreler aynı değil", Snackbar.LENGTH_SHORT).show()
                }

            }else{
                Snackbar.make(binding.root, "Boş alanları doldurun", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}