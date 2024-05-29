package com.example.muhendisliktasarimi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySignInBinding
import com.example.muhendisliktasarimi.databinding.ActivitySolveQuestionBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)
        binding.passwordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.black)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.progressBar2.visibility = View.VISIBLE
                binding.linearLayout.visibility = View.GONE
                binding.textViewSignUp.visibility = View.GONE
                firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnFailureListener {
                        if (it.message == "The email address is badly formatted."){
                            Toast.makeText(this,"Geçerli bir email adresi girin", Toast.LENGTH_SHORT).show()
                        }else if (it.message == "The supplied auth credential is incorrect, malformed or has expired."){
                            Toast.makeText(this,"Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Hata", Toast.LENGTH_SHORT).show()
                        }
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE
                        binding.progressBar2.visibility = View.GONE
                    }
                    .addOnCompleteListener {
                    if (it.isSuccessful){
                        binding.progressBar2.visibility = View.GONE
                        binding.textViewSignUp.visibility = View.GONE
                        Toast.makeText(this,"Giriş yapıldı", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else if (it.exception!!.message == "The email address is badly formatted."){
                        Toast.makeText(this,"Geçerli bir email adresi girin", Toast.LENGTH_SHORT).show()
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE
                    }else if (it.exception!!.message == "The supplied auth credential is incorrect, malformed or has expired."){
                        Toast.makeText(this,"Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show()
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE
                    }else{
                        Toast.makeText(this,"Hata", Toast.LENGTH_SHORT).show()
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE

                    }

                }
            }else{
                Toast.makeText(this,"Boş alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}