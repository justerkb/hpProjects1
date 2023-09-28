package converter

import converter.MeasurementCategory.*
import converter.MeasurementUnit.*
import java.util.*

class Converter(private val source: MeasurementUnit, private val target: MeasurementUnit) {
    fun convert(amount: Double) = when (source to target) {
        F to C -> (amount - 32) * 5 / 9
        C to F -> amount * 9 / 5 + 32
        K to C -> amount - 273.15
        C to K -> amount + 273.15
        F to K -> (amount + 459.67) * 5 / 9
        K to F -> amount * 9 / 5 - 459.67
        else -> source.standardUnits * amount / target.standardUnits
    }
}

enum class MeasurementCategory(val allowNegative: Boolean = false) {
    LENGTH,
    WEIGHT,
    TEMPERATURE(true);

    val title: String
        get() = name.toLowerCase().capitalize()
}

enum class MeasurementUnit(val category: MeasurementCategory, private val names: Array<String>, val standardUnits: Double = 1.0) {
    M(LENGTH, arrayOf("m", "meter", "meters")),
    KM(LENGTH, arrayOf("km", "kilometer", "kilometers"), 1000.0),
    CM(LENGTH, arrayOf("cm", "centimeter", "centimeters"), 0.01),
    MM(LENGTH, arrayOf("mm", "millimeter", "millimeters"), 0.001),
    MI(LENGTH, arrayOf("mi", "mile", "miles"), 1609.35),
    YD(LENGTH, arrayOf("yd", "yard", "yards"), 0.9144),
    FT(LENGTH, arrayOf("ft", "foot", "feet"), 0.3048),
    IN(LENGTH, arrayOf("in", "inch", "inches"), 0.0254),

    G(WEIGHT, arrayOf("g", "gram", "grams")),
    KG(WEIGHT, arrayOf("kg", "kilogram", "kilograms"), 1000.0),
    MG(WEIGHT, arrayOf("mg", "milligram", "milligrams"), 0.001),
    LB(WEIGHT, arrayOf("lb", "pound", "pounds"), 453.592),
    OZ(WEIGHT, arrayOf("oz", "ounce", "ounces"), 28.3495),

    C(TEMPERATURE, arrayOf("c", "degree Celsius", "degrees Celsius", "celsius", "dc")),
    F(TEMPERATURE, arrayOf("f", "degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df")),
    K(TEMPERATURE, arrayOf("k", "Kelvin", "Kelvins"));

    private val singular: String
        get() = names[1]

    val plural: String
        get() = names[2]

    fun canConvert(to: MeasurementUnit) = category == to.category

    fun onlyPositive() = !category.allowNegative

    fun render(amount: Double) = "$amount " + if (amount != 1.0) plural else singular

    companion object {
        fun fromName(name: String) = values().first {
            // Improve it.
            name.toLowerCase() in it.names.map(String::toLowerCase)
        }
    }
}

fun convert(input: String): String {
    val regex = "^(?<number>[-\\d.]+) (?<source>[\\w ]+?) (?:\\w+) (?<target>(degrees? )?[\\w]+)$"
    val match = regex.toRegex(RegexOption.IGNORE_CASE).find(input) ?: return "Parse error\n"

    val source: MeasurementUnit?
    val target: MeasurementUnit?

    // Validate conversion.
    source = try {
        MeasurementUnit.fromName(match.groupValues[2])
    } catch (e: Exception) {
        null
    }
    target = try {
        MeasurementUnit.fromName(match.groupValues[3])
    } catch (e: Exception) {
        null
    }
    if (source == null || target == null || ! source.canConvert(target)) {
        return "Conversion from ${source?.plural ?: "???"} to ${target?.plural ?: "???"} is impossible\n"
    }

    val amount = match.groupValues[1].toDouble()

    // Validate amount.
    if (source.onlyPositive() && amount < 0) {
        return "${source.category.title} shouldn't be negative\n"
    }

    // Perform conversion.
    val result = Converter(source, target).convert(amount)

    return "${source.render(amount)} is ${target.render(result)}\n"
}

fun main() {
    val scanner = Scanner(System.`in`)
    do {
        print("Enter what you want to convert (or exit): ")
        val input = scanner.nextLine()
        if (input == "exit") break
        print(convert(input))
    } while (true)
}

//               here are examples of outputs


//Enter what you want to convert (or exit): 1 degree Celsius to kelvins
//1.0 degree Celsius is 274.15 kelvins
//
//Enter what you want to convert (or exit): -272.15 dc to K
//-272.15 degrees Celsius is 1.0 kelvin
//
//Enter what you want to convert (or exit): 1 kn to feet
//Conversion from ??? to feet is impossible
//
//Enter what you want to convert (or exit): 1 km to feet
//1.0 kilometer is 3280.839895013123 feet
//
//Enter what you want to convert (or exit): 3 pount to ounces
//Conversion from ??? to ounces is impossible
//
//Enter what you want to convert (or exit): 3 pound to ounces
//3.0 pounds is 47.99999999999999 ounces
//
//Enter what you want to convert (or exit): 3 kelvins to grams
//Conversion from kelvins to grams is impossible
//
//Enter what you want to convert (or exit): exit

//Enter what you want to convert (or exit): 1 F in K
//1.0 degree Fahrenheit is 255.92777777777778 kelvins
//
//Enter what you want to convert (or exit): 1 K in F
//1.0 kelvin is -457.87 degrees Fahrenheit
//
//Enter what you want to convert (or exit): 1 C in K
//1.0 degree Celsius is 274.15 kelvins
//
//Enter what you want to convert (or exit): 1 K in C
//1.0 kelvin is -272.15 degrees Celsius
//
//Enter what you want to convert (or exit): 1 F in C
//1.0 degree Fahrenheit is -17.22222222222222 degrees Celsius
//
//Enter what you want to convert (or exit): 1 C in F
//1.0 degree Celsius is 33.8 degrees Fahrenheit
//
//Enter what you want to convert (or exit): one boa in parrots
//Parse error
//
//Enter what you want to convert (or exit): please convert distance to the Moon to steps
//Parse error
//
//Enter what you want to convert (or exit): many things to improve!
//Parse error
//
//Enter what you want to convert (or exit): exit