package com.example.rxkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.rxkotlin.adapter.UserAdapter
import com.example.rxkotlin.databinding.ActivityMainBinding
import com.example.rxkotlin.db.AppDatabase
import com.example.rxkotlin.entity.User
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var appDatabase: AppDatabase
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        userAdapter = UserAdapter()
        initViews()
    }

    @SuppressLint("CheckResult")
    private fun initViews() {
        appDatabase.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userAdapter.submitList(it)
                binding.progressBar.visibility = View.GONE
            }){

            }
        binding.rvMain.adapter = userAdapter

        binding.button.setOnClickListener {
            val user = User()
            user.username = binding.etUsername.text.toString().trim()
            user.password = binding.etUserPassword.text.toString().trim()
            appDatabase.userDao().addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Long: $it")
                    Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
                }) {
                    Log.d(TAG, "Throwable: $it")
                }
        }
    }
}