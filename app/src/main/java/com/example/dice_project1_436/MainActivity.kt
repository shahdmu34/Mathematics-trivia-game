package com.example.dice_project1_436

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dice_project1_436.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var p1Total = 0     //score total for play
    private var p2Total = 0
    private var jackpotTTL =5
    private var Cresult = 0         //intilize varible holds correct answer
    private var p1Turn = true          //bool if player 1 turn


    private var points = 0          //tracks points per question


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set button eventhandler
        binding.btrollDie.setOnClickListener {
            var dicevalue = Random.nextInt(1, 7)    //generates dice face

            var imageName = "@drawable/dice" + dicevalue

            var resourceID = resources.getIdentifier(imageName, "drawable", packageName)

            binding.iveDie.setImageResource(resourceID)     //displays randomized image of dice


            var value1 = 0           //intillizing value for question
            var value2 = 0


            when(dicevalue){
                1 -> {         //if dice is 1

                    value1 = Random.nextInt(0, 100)
                    value2 = Random.nextInt(0, 100)     //randomized value

                    Cresult = value1 + value2          //addition question and store result
                    val message = " $value1 + $value2 = "        //set message

                    binding.questiontv.text = message       //display message

                    points =1          //points for dice 1

                }
                2-> {       //dice face 2
                    value1 = Random.nextInt(0, 100)
                    value2 = Random.nextInt(0, 100)    //randomized value

                    Cresult = value1 - value2       //subtraction question and store result

                    val message = " $value1 - $value2 = "      //set message

                    binding.questiontv.text = message         //display message

                    points = 2       //points for dice 2

                }
                3-> {         //dice 3
                    value1 = Random.nextInt(0, 21)      //randomized values 1-20
                    value2 = Random.nextInt(0, 21)

                    Cresult = value1 * value2          //set correct answer

                    val message = " $value1 * $value2 = "     //set message

                    binding.questiontv.text = message       //display message

                    points = 3      //points for dice 3
                }
                4->{   //dice 4
                    var randomTurn = Random.nextInt(1,4)         //reroll
                    when(randomTurn){          //rerolling the dice
                        1->{
                            value1 = Random.nextInt(0, 100)
                            value2 = Random.nextInt(0, 100)

                            Cresult = value1 + value2
                            val message = " $value1 + $value2 = "

                            binding.questiontv.text = message

                            points = 1 * 2           //double points given
                        }
                        2->{
                            value1 = Random.nextInt(0, 100)
                            value2 = Random.nextInt(0, 100)

                            Cresult = value1 - value2
                            val message = " $value1 - $value2 = "

                            binding.questiontv.text = message

                            points = 2 * 2
                        }
                        3->{
                            value1 = Random.nextInt(0, 21)
                            value2 = Random.nextInt(0, 21)

                            Cresult = value1 * value2
                            val message = " $value1 * $value2 = "

                            binding.questiontv.text = message

                            points = 3 * 2
                        }
                    }
                }
                5->{         //dice 4

                    val message = "LOSE TURN"      //set message to lose turn

                    binding.questiontv.text = message

                    points = 0
                    p1Turn = !p1Turn       //switch player turn
                    if(p1Turn){
                        binding.playerTurn.text = "Player 1"     //display player turn
                    }else{
                        binding.playerTurn.text = "Player 2"
                    }

                }
                6->{          //dice 6
                    var randomTurn = Random.nextInt(1,4)       //reroll dice
                    when(randomTurn) {
                        1 -> {
                            value1 = Random.nextInt(0, 100)
                            value2 = Random.nextInt(0, 100)

                            Cresult = value1 + value2
                            val message = " $value1 + $value2 = "

                            binding.questiontv.text = message

                            points = jackpotTTL   //points are the jackpot total
                        }
                        2 -> {
                            value1 = Random.nextInt(0, 100)
                            value2 = Random.nextInt(0, 100)

                            Cresult = value1 - value2
                            val message = " $value1 - $value2 = "

                            binding.questiontv.text = message

                            points = jackpotTTL
                        }
                        3 -> {
                            value1 = Random.nextInt(0, 21)
                            value2 = Random.nextInt(0, 21)

                            Cresult = value1 * value2
                            val message = " $value1 * $value2 = "

                            binding.questiontv.text = message

                            points = jackpotTTL
                        }

                    }
                }
            }

        }
        binding.btnGuess.setOnClickListener {     //guess button

            val userAns = binding.answerInput.text.toString().toInt()     //set as the user input
            binding.jackpotScore.text = jackpotTTL.toString()

            if (p1Total >= 20 && p1Total > p2Total) {        //if the player has 20 points or more they win
                binding.playerTurn.text = "Player 1 WON!"        //display message
                reset()                                          //reseting the game
            } else if (p2Total >= 20 && p2Total > p1Total) {
                binding.playerTurn.text = "Player 2 WON!"
                reset()
            } else {
                if (Cresult == userAns) {          //if the user input answer matches the correct answer
                    if (p1Turn) {
                        p1Total += points          //updates the player total as the points won
                        binding.p1Score.text = p1Total.toString()             //set player total

                        if (points == jackpotTTL && jackpotTTL > 0) {           //if player wins jackpot
                            // Reset the jackpot
                            jackpotTTL = 5
                            binding.jackpotScore.text = jackpotTTL.toString()
                        }

                    }
                    else {
                        p2Total += points
                        binding.p2Score.text = p2Total.toString()

                        if (points == jackpotTTL && jackpotTTL > 0) {
                            // Reset the jackpot
                            jackpotTTL = 5
                            binding.jackpotScore.text = jackpotTTL.toString()
                        }
                    }

                }
                else {
                    jackpotTTL += points            //if player answers question incorrectly, points go to jackpot
                    binding.jackpotScore.text = jackpotTTL.toString()
                }

                p1Turn = !p1Turn       //sets player turn
                if (p1Turn) {
                    binding.playerTurn.text = "Player 1"      //displays player turn
                } else {
                    binding.playerTurn.text = "Player 2"
                }

            }


        }

    }

    private fun reset(){          //function to reset game
        p1Total = 0         //set player points to 0
        p2Total = 0
        jackpotTTL = 5          //set the jackpot back to 5
        binding.p1Score.text = "0"
        binding.p2Score.text = "0"
        binding.jackpotScore.text = jackpotTTL.toString()
    }
}




