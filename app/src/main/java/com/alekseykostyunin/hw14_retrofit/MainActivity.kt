package com.alekseykostyunin.hw14_retrofit

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.alekseykostyunin.hw14_retrofit.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonUpdate.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.getUser()
            }
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is State.Initial -> {
                        binding.progress.isVisible = true
                        binding.buttonUpdate.isEnabled = true
                    }

                    is State.Loading -> {
                        binding.progress.isVisible = true
                        binding.buttonUpdate.isEnabled = false
                    }

                    is State.Success -> {
                        binding.progress.isVisible = false
                        binding.buttonUpdate.isEnabled = true
                    }

                    is State.Error -> {
                        binding.progress.isVisible = false
                        binding.buttonUpdate.isEnabled = true
                        Toast.makeText(
                            this@MainActivity,
                            state.textError,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect { results ->
                results?.results?.firstOrNull()?.let { user ->
                    val gender = if (user.gender == "male") "мужской" else "женский"
                    binding.gender.text = "Пол: $gender"
                    binding.name.text = "Имя: ${user.name.first} ${user.name.last}"
                    binding.location.text =
                        "Адрес: ${user.location.street.name}, " +
                                "${user.location.city}, " +
                                "${user.location.state}, " +
                                "${user.location.country}"
                    binding.email.text = "Email: ${user.email}"
                    binding.dob.text = "Возраст: ${user.dob.age}"
                    binding.phone.text = "Телефон: ${user.phone}"
                    Glide.with(this@MainActivity)
                        .load(user.picture.large).into(binding.imageView)
                }
            }
        }

    }
}