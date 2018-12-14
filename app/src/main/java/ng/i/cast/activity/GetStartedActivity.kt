package ng.i.cast.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ng.i.cast.R

class GetStartedActivity : AppCompatActivity() {
    private var buttonGetStarted: Button ?= null
    private var buttonSkip : Button ?= null

    private var dotsLayout: LinearLayout? = null
    private var layouts: IntArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        buttonGetStarted = findViewById(R.id.buttonGetStarted)
        buttonSkip = findViewById(R.id.buttonSkip)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        dotsLayout = findViewById(R.id.layoutDots)
        layouts = intArrayOf(R.layout.slider1, R.layout.slider2,R.layout.slider3)

        addBottomDots(0)
        buttonSkip!!.setOnClickListener {
            startActivity(Intent(this@GetStartedActivity, AuthActivity::class.java))
        }
        buttonGetStarted!!.setOnClickListener {
            signUpDialog()
        }
        val myViewPagerAdapter = MyViewPagerAdapter()
        viewPager.adapter = myViewPagerAdapter
        viewPager.addOnPageChangeListener(this.viewPagerListener)
        viewPager.offscreenPageLimit = 3

    }

    private fun signUpDialog() {
        val dialog = Dialog(this,R.style.Dialog)
        dialog.setContentView(R.layout.dialog_signup)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<Button>(R.id.buttonActor).setOnClickListener {
            startActivity(Intent(this, SignUpActorActivity::class.java))

        }
        dialog.findViewById<Button>(R.id.buttonDirector).setOnClickListener {
            startActivity(Intent(this, SignUpDirectorActivity::class.java))
        }
        dialog.show()
    }

    private var viewPagerListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(paramAnonymousInt: Int) {}

        override fun onPageScrolled(paramAnonymousInt1: Int, paramAnonymousFloat: Float, paramAnonymousInt2: Int) {}

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position ==(layouts!!.size - 0) ) {
                buttonSkip!!.visibility =View.GONE
                buttonGetStarted!!.visibility = View.VISIBLE
            }
            else{
                buttonSkip!!.visibility = View.VISIBLE
                buttonGetStarted!!.visibility = View.GONE
            }
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts!!.size)

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[currentPage]!!.setTextColor(colorsActive[currentPage])
        }
    }

    private inner class MyViewPagerAdapter internal constructor(): PagerAdapter() {

        override fun destroyItem(container: ViewGroup, position:Int, `object`:Any) {
            container.removeView(`object` as View)
        }

        override fun getCount():Int {
            return layouts!!.size
        }

        override fun instantiateItem(container:ViewGroup, position:Int):Any {

            val layoutInflater = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!
            val view = layoutInflater.inflate(layouts!![position], container, false)
            container.addView(view)
            return view
        }


        override fun isViewFromObject(view:View, `object`:Any):Boolean {
            return view === `object`
        }
    }

}
