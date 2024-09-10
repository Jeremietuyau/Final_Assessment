package com.example.final_assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Welcomescreen : Fragment() {

    private lateinit var welcomeMessageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcomescreen, container, false)
        welcomeMessageTextView = view.findViewById(R.id.welcomeMessageTextView)

        // Get the username from the arguments
        val username = arguments?.getString("username")
        welcomeMessageTextView.text = "Welcome $username!"

        return view
    }

    companion object {
        fun newInstance(username: String): Welcomescreen {
            val fragment = Welcomescreen()
            val args = Bundle()
            args.putString("username", username)
            fragment.arguments = args
            return fragment
        }
    }
}
