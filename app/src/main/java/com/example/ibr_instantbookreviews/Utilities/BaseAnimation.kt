package com.example.ibr_instantbookreviews.Utilities

import android.animation.Animator
import android.util.Log
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class BaseAnimation {

    fun animatorEnd(techniques: Techniques, duration: Long, view : View, visibility : Int) {
        YoYo.with(techniques)
            .duration(duration)
            .withListener (object : AnimationFixer {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = visibility
                }
                override fun onAnimationStart(animation: Animator?) {
                    Log.d("Started", "Start")
                }
            })
            .playOn(view)
    }

    fun animatorStart(techniques: Techniques, duration: Long, view : View, visibility : Int) {
        YoYo.with(techniques)
            .duration(duration)
            .withListener (object : AnimationFixer {
                override fun onAnimationEnd(animation: Animator?) {
                    Log.d("Ended", "End")
                }
                override fun onAnimationStart(animation: Animator?) {
                    view.visibility = visibility
                }
            })
            .playOn(view)
    }
}
