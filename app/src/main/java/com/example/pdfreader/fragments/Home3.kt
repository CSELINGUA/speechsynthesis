package com.example.pdfreader.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfreader.viewmodels.Home3ViewModel
import com.example.pdfreader.R
import com.example.pdfreader.activities.SettingsActivity2
import kotlinx.android.synthetic.main.home3_fragment.*


class Home3 : Fragment() {

    companion object {
        fun newInstance() = Home3()
    }

    private lateinit var viewModel: Home3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home3_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Home3ViewModel::class.java)
        // TODO: Use the ViewModel


        layout_rate.setOnClickListener{

        }
        layout_share.setOnClickListener{

        }
        layout_settings.setOnClickListener {
            var intent = Intent(activity, SettingsActivity2
            ::class.java)
            startActivity(intent)
        }
    }

}
