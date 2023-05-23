package com.example.travelmemories

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton=findViewById<Button>(R.id.signInButton)
        //val signInButton=findViewById(R.id.signInButton) as Button
        signInButton.setOnClickListener(this);

        var usernameEditText=findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText= findViewById<EditText>(R.id.editTextPassword)


    }

    override fun onClick(v: View?) {
        val username = findViewById<EditText>(R.id.editTextUsername)
        val password = findViewById<EditText>(R.id.editTextPassword)
        var usernameError = findViewById<TextView>(R.id.textViewUsernameError)
        var passwordError = findViewById<TextView>(R.id.textViewPasswordError)
        var result = findViewById<TextView>(R.id.signInMessage)

        if (v!!.id == R.id.signInButton) {
            var okUsername = false;
            var okPassword = false;

            if (username.text.toString().length == 0) {
                usernameError.text = "Username cannot be empty!";
                okUsername = false;
            } else if (username.text.toString().length < 4) {
                usernameError.text = "Username is too short!";
                okUsername = false;
            } else {
                usernameError.text = "";
                okUsername = true;
            }


            if (password.text.toString().length == 0) {
                passwordError.text = "Password cannot be empty!";
                okPassword = false;
            } else if (password.text.toString().length < 4) {
                passwordError.text = "Password is too short! ";
                okPassword = false;
            } else {
                passwordError.text = "";
                okPassword = true;
            }


            if(okPassword && okUsername)
                if(username.text.toString()== "admin" && password.text.toString()=="password") {
                    result.text = "Login successfull!";
                    result.setTextColor(getColor(R.color.green));
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent);
                }
                else
                {
                    result.text="Login failed.\n Username or password is incorrect!";
                    result.setTextColor(getColor(R.color.red));
                }

            else
                result.text="";
        }
    }
}