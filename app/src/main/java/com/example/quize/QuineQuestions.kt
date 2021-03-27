package com.example.quize

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quize_questions.*
import java.util.*
import kotlin.collections.ArrayList as KotlinCollectionsArrayList

class QuineQuestions : AppCompatActivity(), View.OnClickListener {

    private var  mCurrentPosition: Int = 1
    private var mQuestionsList: KotlinCollectionsArrayList<Q>? = null
    private var mSelectedOptionPosition: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quize_questions)

        mQuestionsList = Constants.getQ()

        setQuestion()

        option_one.setOnClickListener(this)
        option_two.setOnClickListener(this)
        option_Three.setOnClickListener(this)
        option_Four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)



    }
    private fun setQuestion(){
        

         mCurrentPosition=1
        val question: Q?= mQuestionsList!![mCurrentPosition-1]

        defaultOptionsView()
        if (mCurrentPosition == mQuestionsList!!.size){
            btn_submit.text = "FINISH"
        }else{
            btn_submit.text = "SUBMIT"
        }

        progrBar.progress=mCurrentPosition
        progress.text = "$mCurrentPosition"+ "/" + progrBar.max
        Q_id.text = question!!.Q
        Q_image.setImageResource(question.image)

        option_one.text =question.optionOne
        option_two.text =question.optionTwo
        option_Three.text =question.optionThree
        option_Four.text =question.optionFour


    }
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, option_one)
        options.add(1, option_two)
        options.add(2, option_Three)
        options.add(3, option_Four)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.defult_option_broder_bg
            )

       }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.option_one ->{
                selectedOptionView(option_one, 1)
            }
            R.id.option_two ->{
                selectedOptionView(option_two, 2)
            }
            R.id.option_Three ->{
                selectedOptionView(option_Three, 3)
            }
            R.id.option_Four ->{
                selectedOptionView(option_Four, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(this, "You have successfully completed the Quiz",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.worng_option_broder_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_broder_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        btn_submit.text = "FINISH"
                    }else{
                        btn_submit.text = " GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0

                }

            }

        }

    }
    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1->{
                option_one.background = ContextCompat.getDrawable(
                     this, drawableView
                )
            }
            2->{
                option_two.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
            3->{
                option_Three.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
            1->{
                option_Four.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
        }

    }
    private fun selectedOptionView(tv: TextView,
                                   selectedOptionNumber: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.typeface = Typeface.DEFAULT
        tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_broder_bg
        )


    }
}
