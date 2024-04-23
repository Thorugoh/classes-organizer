import java.lang.Exception

fun main(args: Array<String>) {
    populateAgenda()
}

fun populateAgenda(){
    val port = Subject(name = "POR", 4)
    val math = Subject(name = "MAT", 4)
    val geog = Subject(name = "GEO", 3)
    val hist = Subject(name = "HIS", 3)
    val cien = Subject(name = "CIE", 3)

    val class1 = Class("Class1")
    val class2 = Class("Class2")
    val class3 = Class("Class3")
    val class4 = Class("Class4")

    val subjects = arrayOf(port, math, geog, hist, cien)
    val classes = arrayOf(class1, class2, class3, class4)

    classes.forEach { c ->
        c.addSubject(subjects, classes)
    }

    classes.forEach { c ->
        val sortedAgenda = c.week.toSortedMap() // Sort the agenda by day
        val agendaString = sortedAgenda.entries.joinToString(" ") { (day, dayInfo) ->
            "$day=${dayInfo.toString()}"
        }
        println("${c.name}: $agendaString")
        println("totalHours: ${c.getHoursFromWeek()}")
    }
}