package com.eth.refiq.ui.ownerpanel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.eth.refiq.ui.ownerpanel.TopicOwnerModeratorFragment
import com.eth.refiq.ui.ownerpanel.TopicOwnerShareFragment

class TopicOwnerPanelAdapter constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return if (position == 0){
            "Moderators"
        } else {
            "Share settings"
        }
    }

    override fun getItem(position: Int): Fragment {
        return if (position==0){
            TopicOwnerModeratorFragment()
        }else {
            TopicOwnerShareFragment()
        }
    }

}