package ng.i.cast.fragment

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import ng.i.cast.R
import ng.i.cast.fragment.child_fragment.AudioFragment
import ng.i.cast.fragment.child_fragment.ChildAuditionFragment
import ng.i.cast.fragment.child_fragment.ChildGalleryFragment
import ng.i.cast.fragment.child_fragment.VideoFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class GalleryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var viewPager: ViewPager?= null
    private var tabLayout  : TabLayout?= null
    private var currentTab = 0
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_gallery, container, false)

        tabLayout = v.findViewById(R.id.tabLayout)
        viewPager = v.findViewById(R.id.view_pager)

        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.adapter = mSectionsPagerAdapter
        tabLayout!!.getTabAt(0)!!.text = "Photo"
        tabLayout!!.getTabAt(1)!!.text = "Video"
        tabLayout!!.getTabAt(2)!!.text = "Audio"

        //tabLayout.getTabAt(0).setIcon(R.drawable.circle_blue);
        viewPager!!.setCurrentItem(currentTab, true)
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        return v
    }


    inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            // getItem is called to newInstance the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when (position) {
                0 -> {
                    currentTab = 0
                    ChildGalleryFragment()
                }
                1 -> {
                    currentTab = 1
                    VideoFragment()
                }
                2 ->{
                    currentTab = 2
                    AudioFragment()
                }
                else -> null
            }
            // return PlaceholderFragment.newInstance(position + 1);
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 3
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GalleryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                GalleryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
