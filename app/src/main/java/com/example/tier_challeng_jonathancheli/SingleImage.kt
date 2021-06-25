package com.example.tier_challeng_jonathancheli

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SingleImage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_image)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val myImage = findViewById<ImageView>(R.id.single_image)
        val text = findViewById<TextView>(R.id.textView_single)

        var image  = intent.getIntExtra("image",0)!!

        text.text = getString(R.string.vision)

        myImage.setImageResource(image)
        if(myImage!= null) {
            progressBar.visibility= View.GONE

        }

    }

    

}




