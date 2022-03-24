package com.coldani3.dangoniser.screens.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.FragmentEventBinding
import com.coldani3.dangoniser.databinding.FragmentEventListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEventBinding>(inflater,
            R.layout.fragment_event,container,false)
        return binding.root;
    }
}