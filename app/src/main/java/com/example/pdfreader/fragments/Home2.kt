package com.example.pdfreader.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfreader.viewmodels.Home2ViewModel
import com.example.pdfreader.R


class Home2 : Fragment() {

    companion object {
        fun newInstance() = Home2()
    }

    private lateinit var viewModel: Home2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Home2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
