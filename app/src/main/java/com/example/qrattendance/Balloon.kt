package com.example.qrattendance

import android.app.Activity
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

fun Activity.createBalloon(msg: String): Balloon {
    val balloon = com.skydoves.balloon.createBalloon(this) {
        setWidthRatio(1.0f)
        setHeight(BalloonSizeSpec.WRAP)
        setText(msg)
        setTextColorResource(R.color.white)
        setTextSize(15f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setArrowSize(10)
        setArrowPosition(0.5f)
        setPadding(12)
        setCornerRadius(8f)
        setBackgroundColorResource(R.color.colorAccent)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setLifecycleOwner(this.lifecycleOwner)
        build()
    }
    return balloon
}