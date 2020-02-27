package com.example.ibr_instantbookreviews.ResultActivityFragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RatingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentManagerResults(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int  = 100

    override fun getItem(i: Int): Fragment {
        var fragment = Fragment()

        when(i){
            0->{
                fragment = RatingFragment()
            }
            1->{
                fragment = DescriptionFragment()
            }
            2->{
                fragment = ReviewsFragment()
            }
            3->{
                fragment = PricingFragment()
            }
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}
