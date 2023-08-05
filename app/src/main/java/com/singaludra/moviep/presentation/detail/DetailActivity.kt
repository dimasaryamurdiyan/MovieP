package com.singaludra.moviep.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singaludra.moviep.R
import com.singaludra.moviep.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}