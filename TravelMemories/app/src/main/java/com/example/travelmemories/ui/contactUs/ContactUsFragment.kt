package com.example.travelmemories.ui.contactUs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelmemories.MainActivity
import com.example.travelmemories.databinding.FragmentContactUsBinding


class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null

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

        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textContactUs
        contactUsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        (activity as MainActivity?)!!.getFloatingActionButton()?.hide()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}