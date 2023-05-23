package com.example.travelmemories.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.travelmemories.MainActivity
import com.example.travelmemories.R
import com.example.travelmemories.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactUsViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signInButton.setOnClickListener {
            var okUsername = false;
            var okPassword = false;

            if (binding.editTextUsername.text.toString().isEmpty()) {
                binding.textViewUsernameError.text = "Username cannot be empty!";
                okUsername = false;
            } else if (binding.editTextUsername.text.toString().length < 4) {
                binding.textViewUsernameError.text = "Username is too short!";
                okUsername = false;
            } else {
                binding.textViewUsernameError.text = "";
                okUsername = true;
            }


            if (binding.editTextPassword.text.toString().isEmpty()) {
                binding.textViewPasswordError.text = "Password cannot be empty!";
                okPassword = false;
            } else if (binding.editTextPassword.text.toString().length < 4) {
                binding.textViewPasswordError.text = "Password is too short! ";
                okPassword = false;
            } else {
                binding.textViewPasswordError.text = "";
                okPassword = true;
            }


            if(okPassword && okUsername)
                if(binding.editTextUsername.text.toString()== "admin" && binding.editTextPassword.text.toString()=="password") {
                    binding.signInMessage.text = "Login successfull!";
                    activity?.let { it1 -> binding.signInMessage.setTextColor(it1.getColor(R.color.green)) };

                    NavHostFragment.findNavController(this).navigate(R.id.nav_home)

                }
                else
                {
                    binding.signInMessage.text="Login failed.\n Username or password is incorrect!";
                    activity?.let { it1 -> binding.signInMessage.setTextColor(it1.getColor(R.color.red)) };
                }

            else
                binding.signInMessage.text="";
        }



        (activity as MainActivity?)!!.getFloatingActionButton()?.hide()
        (activity as MainActivity?)!!.showAppBar(false)
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity?)!!.showAppBar(true)
    }
}