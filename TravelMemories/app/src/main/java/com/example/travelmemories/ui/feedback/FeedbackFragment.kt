package com.example.travelmemories.ui.feedback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelmemories.MainActivity
import com.example.travelmemories.databinding.FragmentFeedbackBinding
import com.example.travelmemories.ui.contactUs.ContactUsViewModel


class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null

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

        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, "codruta.serban98@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Demo GAD")
        intent.putExtra(Intent.EXTRA_TEXT, "Demo send feedback with email")
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, "Select email"))
        (activity as MainActivity?)!!.getFloatingActionButton()?.hide()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}