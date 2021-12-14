package ru.dinikos.mynotes.mvp.fragments

import androidx.fragment.app.FragmentManager

interface ShowFragment {

    fun showFragment(manager: FragmentManager, containerViewId: Int)
}