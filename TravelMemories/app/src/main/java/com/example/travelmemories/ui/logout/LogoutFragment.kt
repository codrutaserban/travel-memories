package com.example.travelmemories.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.travelmemories.MainActivity
import com.example.travelmemories.R
import com.example.travelmemories.databinding.FragmentLogoutBinding
import com.example.travelmemories.ui.contactUs.ContactUsViewModel

class LogoutFragment : Fragment() {

    private var _binding: FragmentLogoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactUsViewModel =
            ViewModelProvider(this).get(ContactUsViewModel::class.java)

        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonAutentificate.setOnClickListener{
            findNavController(this).navigate(R.id.nav_login)
        }

        (activity as MainActivity?)!!.getFloatingActionButton()?.hide()
        (activity as MainActivity?)!!.showAppBar(false)
        return root
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity?)!!.showAppBar(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}