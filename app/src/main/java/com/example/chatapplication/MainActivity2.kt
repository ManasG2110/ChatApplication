package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    private lateinit var btn_login: Button
    private lateinit var btn_register: Button
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)
        edt_email = findViewById(R.id.edt_email)
        edt_pass = findViewById(R.id.edt_pass)
        mAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {

            val intent = Intent(this@MainActivity2, register::class.java)
            startActivity(intent)

        }
        btn_login.setOnClickListener {

            //logic for login
            val email = edt_email.text.toString()
            val password = edt_pass.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this@MainActivity2, dashboard::class.java)
                        finish()
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@MainActivity2, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}