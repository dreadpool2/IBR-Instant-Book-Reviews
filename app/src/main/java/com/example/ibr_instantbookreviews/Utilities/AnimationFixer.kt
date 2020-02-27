package com.example.ibr_instantbookreviews.Utilities

import android.animation.Animator


interface AnimationFixer : Animator.AnimatorListener {
    override fun onAnimationCancel(animation: Animator?){}
    override fun onAnimationRepeat(animation: Animator?){}
}
