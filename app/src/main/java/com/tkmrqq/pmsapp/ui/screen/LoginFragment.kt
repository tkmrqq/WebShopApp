package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tkmrqq.pmsapp.R

class LoginFragment(
    private val onLoginResult: (Boolean) -> Unit,
    private val onSignUpClick: () -> Unit
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signupButton = view.findViewById<Button>(R.id.signUpButton)
        val usernameField = view.findViewById<EditText>(R.id.etUsername)
        val passwordField = view.findViewById<EditText>(R.id.etPassword)

        loginButton.setOnClickListener {
            val enteredUsername = usernameField.text.toString()
            val enteredPassword = passwordField.text.toString()

            if (enteredUsername == "user" && enteredPassword == "password") {
                // Сохраняем статус логина в SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("user_prefs", 0)
                with(sharedPreferences.edit()) {
                    putBoolean("is_logged_in", false)
                    apply()
                }
                onLoginResult(true)
            } else {
                onLoginResult(false)
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            onSignUpClick()
        }
    }
}
