package manchester.united.squad

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import manchester.united.squad.databinding.ActivityPlugBinding


class PlugActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlugBinding
    private var topCard = R.drawable.acinfeev
    private val MU = listOf(
        R.drawable.antony, // есть
        R.drawable.antonymarsayal,// есть
        R.drawable.casemiro,// есть
        R.drawable.garrymagyar,// есть
        R.drawable.jonyevans,// есть
        R.drawable.kobbemainu,// есть
        R.drawable.lukshow,// есть
        R.drawable.mesonmayunt,// есть
        R.drawable.serhiorehilon,// есть
        R.drawable.diigedalot,  //есть
        R.drawable.victorvendelef,// есть
    )
    private var countAnswer = 0
    private var countCard = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlugBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        val viewModel = ViewModelProviders
            .of(this)[SwipeRightViewModel::class.java]

        viewModel
            .modelStream
            .observe(this, Observer {
                bindCard(it)
            })

        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenPass,
                    R.id.offScreenLike -> {
                        countCard++
                        if (countCard > 20) {
                            val text = if (countAnswer >= 20) "You have won! You guessed all the players"
                                else "You've lost! You didn't guess all the players"
                            val builder = AlertDialog.Builder(this@PlugActivity)
                                .setTitle(text)
                                .setMessage("Do you want to repeat or go back?")
                                .setPositiveButton("Repeat") { dialog, _ ->
                                    restartActivity(this@PlugActivity)
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Go back") { dialog, _ ->
                                    startActivity(Intent(this@PlugActivity, LoadingActivity::class.java))
                                    dialog.dismiss()
                                }
                                .show()
                        }
                        if (currentId == R.id.offScreenLike) {
                            Log.d("PA", "mu.contains - ${MU.contains(topCard)}")
                            if (MU.contains(topCard))
                               countAnswer++
                        }
                        if (currentId == R.id.offScreenPass) {
                            Log.d("PA", "!mu.contains - ${!MU.contains(topCard)}")
                            if (!MU.contains(topCard))
                                countAnswer++
                        }
                        Log.d("PA", "countAnswer - $countAnswer")
                        Log.d("PA", "countCard - $countCard")

                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.rest, R.id.like)
                        viewModel.swipe()
                    }
                }
            }

        })

//        binding.likeButton.setOnClickListener {
//            binding.motionLayout.transitionToState(R.id.like)
//        }
//
//        binding.passButton.setOnClickListener {
//            binding.motionLayout.transitionToState(R.id.pass)
//        }
    }

    private fun bindCard(model: SwipeRightModel) {
        topCard = model.top.imageResourceId
        binding.topCard.setBackgroundResource(model.top.imageResourceId)
        binding.bottomCard.setBackgroundResource(model.bottom.imageResourceId)
    }

    fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate()
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }

}
