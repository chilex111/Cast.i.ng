package ng.i.cast.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ng.i.cast.R
import ng.i.cast.fragment.child_fragment.ChildAuditionFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AuditionFragment : Fragment() {
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
        val v =inflater.inflate(R.layout.fragment_audition, container, false)


        tabLayout = v.findViewById(R.id.tabLayout)
        viewPager = v.findViewById(R.id.view_pager)

        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.adapter = mSectionsPagerAdapter
        tabLayout!!.getTabAt(0)!!.text = "Available"
        tabLayout!!.getTabAt(1)!!.text = "Applied"

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
        return  v
    }


    inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            // getItem is called to newInstance the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when (position) {
                0 -> {
                    currentTab = 0
                    ChildAuditionFragment()
                    ChildAuditionFragment.newInstance("available")
                }
                1 -> {
                    currentTab = 1
                    ChildAuditionFragment()
                    ChildAuditionFragment.newInstance("applied")
                }
                else -> null
            }
            // return PlaceholderFragment.newInstance(position + 1);
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

    }
    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AuditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AuditionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
