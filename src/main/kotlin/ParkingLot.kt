//Description
//Now it's time to add some query commands.
//
//The command reg_by_color prints all registration numbers of cars of a particular color, taking color as a parameter. The color may be written in uppercase or lowercase letters. For example, reg_by_color BLACK. The answer should contain registration numbers separated by commas. The order should be the same as in the status command. If there are no cars of this color, the output should be like this: No cars with color BLACK were found..
//
//The command spot_by_color is similar to the previous one, but it prints the parking space numbers of all the cars of a particular color.
//
//The command spot_by_reg returns you the number of the spot where a car is located based on its registration number, for example, spot_by_reg KA-01. This command will also return an error message if your car was not found: No cars with registration number KA-01 were found. For convenience, let's suppose that all car registration numbers are unique.
//
//Example
//The symbol > represents the user input.
//
//> spot_by_color yellow
//Sorry, a parking lot has not been created.
//> create 4
//Created a parking lot with 4 spots.
//> park KA-01-HH-9999 White
//White car parked in spot 1.
//> park KA-01-HH-3672 White
//White car parked in spot 2.
//> park Rs-P-N-21 Red
//Red car parked in spot 3.
//> park Rs-P-N-22 Red
//Red car parked in spot 4.
//> reg_by_color GREEN
//No cars with color GREEN were found.
//> reg_by_color WHITE
//KA-01-HH-9999, KA-01-HH-3672
//> spot_by_color GREEN
//No cars with color GREEN were found.
//> spot_by_color red
//3, 4
//> spot_by_reg ABC
//No cars with registration number ABC were found.
//> spot_by_reg KA-01-HH-3672
//2
//> spot_by_reg Rs-P-N-21
//3
//> exit

fun main() {
    val list = MutableList(20) {true}
    val reg = mutableListOf<String?>()
    val colorCar = mutableListOf<String?>()
    val digits = mutableListOf<Int>()

    fun list_by_list(list: MutableList<Any?>, list2: MutableList<String?>, colorOrReg: String?, regOrC: String) {
        var s = 0
        for(i in 0 until list2.size) {
            if(list2[i].equals(colorOrReg, ignoreCase = true)) {
                if (list2.indexOfLast { it.equals(colorOrReg, ignoreCase = true) } == i) {
                    print("${list[i]}")
                    println()
                    s++
                } else {
                    print("${list[i]}, ")
                    s++
                }
            }
        }

        if(s == 0) println("No cars with $regOrC $colorOrReg were found.")
    }
    fun parked(colorOrNull: String?, numberCar: String?) {
        for(i in 0 until list.size) {
            if(list[i]) {
                println("$colorOrNull car parked in spot ${i + 1}.")
                list[i] = false
                reg[i] = numberCar
                colorCar[i] = colorOrNull
                digits[i] = i+1

                return
            }
        }
        println("Sorry, the parking lot is full.")
    }

    fun leave(numberCarOrnumber: Int?) {
        if(list[numberCarOrnumber!! - 1]) {
            println("There is no car in spot $numberCarOrnumber.")
        } else {
            println("Spot $numberCarOrnumber is free.")
            list[numberCarOrnumber.toInt() - 1] = true
            reg[numberCarOrnumber - 1] = ""
            colorCar[numberCarOrnumber - 1] = ""
            digits[numberCarOrnumber - 1] = 0

        }
    }

    fun create(num: Int?) {
        list.clear()
        reg.clear()
        colorCar.clear()
        digits.clear()

        println("Created a parking lot with $num spots.")
        repeat(num!!) {
            list.add(true)
            reg.add(" ")
            colorCar.add(" ")
            digits.add(0)
        }
    }

    fun status() {
        var s = 0
        for(i in 0 until list.size) {
            if(!list[i]) {
                println("${i + 1} ${reg[i]} ${colorCar[i]}")
                s++
            }
        }
        if(s == 0) println("Parking lot is empty.")
    }


    do{
        val input = readLine()!!.split(" ")

        if(input[0] == "create") {
            create(input[1].toInt())
            break
        }else if(input[0] == "exit") return else println("Sorry, a parking lot has not been created.")

    } while (true)

    do {
        val input = readLine()!!.split(" ")
        val pOrl: String? = input.getOrNull(0)
        val numberCarOrnumber: String? = input.getOrNull(1)
        val colorOrNull: String? = input.getOrNull(2)

        when(pOrl) {
            "exit" -> break
            "park" -> parked(colorOrNull, numberCarOrnumber)
            "create" -> create(numberCarOrnumber!!.toInt())
            "status" -> status()
            "leave" -> leave(numberCarOrnumber!!.toInt())
            "reg_by_color" -> list_by_list(reg.toMutableList(), colorCar, numberCarOrnumber, "color")
            "spot_by_color" -> list_by_list(digits.toMutableList(), colorCar, numberCarOrnumber, "color")
            "spot_by_reg" -> list_by_list(digits.toMutableList(), reg, numberCarOrnumber, "registration number")
        }
    } while (true)
}

