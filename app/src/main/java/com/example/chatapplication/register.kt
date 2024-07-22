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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class register : AppCompatActivity() {
    private lateinit var edt_name: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var edt_phone: EditText
    private lateinit var btn_register: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        edt_name = findViewById(R.id.edt_name)
        edt_email = findViewById(R.id.edt_email)
        edt_phone = findViewById(R.id.edt_Phone)
        edt_pass = findViewById(R.id.edt_pass)
        btn_register = findViewById(R.id.btn_register)

        mAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {

            val name = edt_name.text.toString()
            val email = edt_email.text.toString()
            val password = edt_pass.text.toString()
            //logic for register

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@register, MainActivity2::class.java)
                        finish()
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@register, "User is not created", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

        private fun addUserToDatabase(name: String, email: String, uid: String?) {
            mDbRef = FirebaseDatabase.getInstance().getReference()

            mDbRef.child("user").child(uid!!).setValue(User(name,email,uid))
        }
}