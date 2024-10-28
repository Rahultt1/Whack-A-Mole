package com.example.whackamole

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import kotlin.random.Random

class WhackAMoleGame {

    private var score: Int = 0
    private val moleAppearanceInterval = 3000L
    private lateinit var moleViews: List<ImageView>
    private var isGameRunning: Boolean = false
    private var numMolesShown = 0
    private val maxNumMoles = 4
    private val delayBetweenAppearances = 200L
    private val holeDownDuration = 790L
    private var scoreCallback: ((Int) -> Unit)? = null

    fun startGame(moleViews: List<ImageView>) {
        this.moleViews = moleViews

        // Initialize game variables
        score = 0
        isGameRunning = true

        // Start game loop or timer
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isGameRunning) {
                    val numberOfMoles = Random.nextInt(1, 4)
                    val moleIndices = mutableListOf<Int>()
                    while (moleIndices.size < numberOfMoles) {
                        val moleIndex = Random.nextInt(moleViews.size)
                        if (moleIndex !in moleIndices) {
                            moleIndices.add(moleIndex)
                            showMole(moleIndex)
                        }
                    }

                    handler.postDelayed(this, moleAppearanceInterval)
                }
            }
        }, 0)
    }

    fun stopGame() {
        isGameRunning = false
    }

    private fun showMole(moleIndex: Int) {
        if (numMolesShown < maxNumMoles) {
            val moleView = moleViews[moleIndex]
            if (moleView.tag != "up") {
                moleView.setImageResource(R.drawable.mole_up)
                moleView.tag = "up"
                numMolesShown++

                val startY = moleView.height.toFloat()
                val endY = 0f
                val translateAnimation = TranslateAnimation(0f, 0f, startY, endY)
                translateAnimation.duration = 200
                moleView.startAnimation(translateAnimation)

                moleView.setOnClickListener {
                    if (isGameRunning && moleView.tag == "up") {
                        score++
                        scoreCallback?.invoke(score)
                        moleView.setImageResource(R.drawable.mole_down)
                        moleView.tag = "down"
                        Handler().postDelayed({
                            moleView.setImageResource(R.drawable.hole)
                            moleView.tag = "down"
                            numMolesShown--
                        }, 1000L)
                    }
                }

                Handler().postDelayed({
                    if (moleView.tag == "up") {
                        moleView.setImageResource(R.drawable.hole)
                        moleView.tag = "down"
                        numMolesShown--
                    }
                }, holeDownDuration)
            }
        }
    }

    fun whackMole(index: Int) {

    }

    fun setScoreCallback(callback: ((Int) -> Unit)) {
        scoreCallback = callback
    }

    // ... (rest of the WhackAMoleGame code) ...
}
