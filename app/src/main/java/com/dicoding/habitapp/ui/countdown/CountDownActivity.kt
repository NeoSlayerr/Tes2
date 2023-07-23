package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"

        val habit = intent.getParcelableExtra<Habit>(HABIT) as Habit

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

        val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

        //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
        viewModel.setInitialTime(habit.minutesFocus)
        viewModel.currentTimeString.observe(this){
            findViewById<TextView>(R.id.tv_count_down).text = it
        }
        viewModel.eventCountDownFinish.observe(this){
            updateButtonState(!it)
        }

        //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
        workManager = WorkManager.getInstance(this)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            viewModel.startTimer()
            val data = Data.Builder()
                .putInt(HABIT_ID, habit.id)
                .putString(HABIT_TITLE, habit.title)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .addTag(habit.title)
                .setInitialDelay(habit.minutesFocus, TimeUnit.MINUTES)
                .setInputData(data)
                .build()
            workManager.enqueue(oneTimeWorkRequest)
            updateButtonState(true)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            viewModel.resetTimer()
            workManager.cancelAllWorkByTag(habit.title)
            updateButtonState(false)
        }
    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}