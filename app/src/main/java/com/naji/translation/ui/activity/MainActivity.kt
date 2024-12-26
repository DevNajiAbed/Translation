package com.naji.translation.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.naji.translation.api.Resource
import com.naji.translation.databinding.ActivityMainBinding
import com.naji.translation.model.Language
import com.naji.translation.view_model.activty.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var langs: ArrayList<Language>
    private lateinit var langsNames: ArrayList<String>
    private lateinit var langsCodes: ArrayList<String>
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        langs = bringListOfLanguages()
        langsNames = bringLangsNames()
        langsCodes = bringLangsCodes()

        binding.apply {
            spinnerSourceLangs.apply {
                adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    langsNames
                )
            }
            spinnerTargetLangs.apply {
                adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    langsNames
                )
            }
            btnTranslate.setOnClickListener {
                val text = etTextToTranslate.text.toString().trim()
                if (text.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Insert anything to translate",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val sourceLangCode =
                    langsCodes[langsNames.indexOf(spinnerSourceLangs.selectedItem as String)]
                val targetLangCode =
                    langsCodes[langsNames.indexOf(spinnerTargetLangs.selectedItem as String)]
                viewModel.translate(text, sourceLangCode, targetLangCode)
            }
        }
        observeTranslationLiveData()
    }

    private fun observeTranslationLiveData() {
        viewModel.translationLiveData.observe(this) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    Log.e("nji", "MainActivity line ${Thread.currentThread().stackTrace[2].lineNumber}: ${it.msg}")
                    binding.apply {
                        btnTranslate.isClickable = true
                        progressBar.isVisible = false
                    }
                }

                is Resource.Loading -> {
                    binding.apply {
                        btnTranslate.isClickable = false
                        progressBar.isVisible = true
                    }
                }

                is Resource.Success -> {
                    binding.apply {
                        tvTranslatedText.text = it.data?.translatedText
                        btnTranslate.isClickable = true
                        progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun bringLangsCodes(): ArrayList<String> {
        return ArrayList<String>().apply {
            langs.forEach {
                add(it.code)
            }
        }
    }

    private fun bringLangsNames(): ArrayList<String> {
        return ArrayList<String>().apply {
            langs.forEach {
                add(it.name)
            }
        }
    }

    private fun bringListOfLanguages() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("languages", ArrayList::class.java)!! as ArrayList<Language>
        } else {
            intent.getSerializableExtra("languages") as ArrayList<Language>
        }

}