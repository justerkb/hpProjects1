//Description
//Our game is almost ready! Now let's combine what we’ve learned in the previous stages to make a game of tic-tac-toe that two players can play from the beginning (with an empty grid) through to the end (until there is a draw, or one of the players wins).
//
//The first player has to play as X and their opponent plays as O.
//
//Objectives
//In this stage, you should write a program that:
//
//Prints an empty grid at the beginning of the game.
//Creates a game loop where the program asks the user to enter the cell coordinates, analyzes the move for correctness and shows a grid with the changes if everything is okay.
//Ends the game when someone wins or there is a draw.
//You need to output the final result at the end of the game. Good luck!
//
//Example
//The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.
//
//Example 1.
//
//---------
//|       |
//|       |
//|       |
//---------
//> 2 2
//---------
//|       |
//|   X   |
//|       |
//---------
//> 2 2
//This cell is occupied! Choose another one!
//> two two
//You should enter numbers!
//> 1 4
//Coordinates should be from 1 to 3!
//> 1 1
//---------
//| O     |
//|   X   |
//|       |
//---------
//> 3 3
//---------
//| O     |
//|   X   |
//|     X |
//---------
//> 2 1
//---------
//| O     |
//| O X   |
//|     X |
//---------
//> 3 1
//---------
//| O     |
//| O X   |
//| X   X |
//---------
//> 2 3
//---------
//| O     |
//| O X O |
//| X   X |
//---------
//> 3 2
//---------
//| O     |
//| O X O |
//| X X X |
//---------
//X wins

import java.util.*
fun main() {
    val list2d: MutableList<MutableList<Char>> = MutableList(3) {
        mutableListOf(' ', ' ', ' ')
        mutableListOf(' ', ' ', ' ')
        mutableListOf(' ', ' ', ' ')
    }

    fun banner() {
        println("---------")
        println("| ${list2d[0].joinToString(" ")} |")
        println("| ${list2d[1].joinToString(" ")} |")
        println("| ${list2d[2].joinToString(" ")} |")
        println("---------")
    }
    fun o3(): Boolean {
        return if (list2d[0].joinToString("") == "OOO" ||
            list2d[1].joinToString("") == "OOO" ||
            list2d[2].joinToString("") == "OOO"
        ) {
            true
        } else if(list2d[0][0].toString() + list2d[1][0].toString() + list2d[2][0].toString() == "OOO"||
            list2d[0][1].toString() + list2d[1][1].toString() + list2d[2][1].toString() == "OOO" ||
            list2d[0][2].toString() + list2d[1][2].toString() + list2d[2][2].toString() == "OOO"
        ) {
            true
        } else list2d[0][0].toString() + list2d[1][1].toString() + list2d[2][2] == "OOO" ||
                list2d[0][2].toString() + list2d[1][1].toString() + list2d[2][0] == "OOO"
    }
    fun x3(): Boolean {
        return if (list2d[0].joinToString("") == "XXX" ||
            list2d[1].joinToString("") == "XXX" ||
            list2d[2].joinToString("") == "XXX"
        ) {
            true
        } else if(list2d[0][0].toString() + list2d[1][0].toString() + list2d[2][0].toString() == "XXX"||
            list2d[0][1].toString() + list2d[1][1].toString() + list2d[2][1].toString() == "XXX" ||
            list2d[0][2].toString() + list2d[1][2].toString() + list2d[2][2].toString() == "XXX"
        ) {
            true
        } else list2d[0][0].toString() + list2d[1][1].toString() + list2d[2][2] == "XXX" ||
                list2d[0][2].toString() + list2d[1][1].toString() + list2d[2][0] == "XXX"
    }
    banner()
    var s = 1
    do {
        print("Enter the coordinates:")
        val scanner = Scanner(System.`in`)
        val x = scanner.nextInt()
        val y = scanner.nextInt()
        if (x > 3 || y > 3) {
            println("Coordinates should be from 1 to 3!")
        } else if (list2d[x - 1][y - 1] == ' ') {
            if(s == 1) {
                list2d[x - 1][y - 1] = 'X'
                banner()
                s = 0
            } else {
                list2d[x - 1][y - 1] = 'O'
                s = 1
                banner()
            }
        } else if (list2d[x - 1][y - 1] != ' ') {
            println("This cell is occupied! Choose another one!")
        } else {
            println("You should enter numbers!")
        }
    } while (!x3() && !o3())
    if(x3()) {
        println("X wins")
    } else {
        println("O wins")
    }
}