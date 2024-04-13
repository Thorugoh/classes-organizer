fun main(args: Array<String>) {
    populateAgenda()
}

fun populateAgenda(){
    val port = Subject(name = "Portugues")
    val math = Subject(name = "Matematica")
    val geog = Subject(name = "Geografia")
    val hist = Subject(name = "Historia")
    val cien = Subject(name = "Ciencias")

    val class1 = Class("Class1", agenda = mutableMapOf())
    val class2 = Class("Class2", agenda = mutableMapOf())
    val class3 = Class("Class3", agenda = mutableMapOf())
    val class4 = Class("Class4", agenda = mutableMapOf())

    val subjects = arrayOf(port, math, geog, hist, cien)
    val classes = arrayOf(class1, class2, class3, class4)

    var currentSubject = 0;
    var firstSubjectDay = 0
    classes.forEach { c ->
        var currentDay = firstSubjectDay

        while(currentDay <= subjects.size - 1){
            if(currentSubject >= subjects.size) break;
            if(c.agenda[currentDay] == null){
                c.agenda[currentDay] = Day(subject = subjects[currentSubject])
                currentSubject++
                currentDay++;
            }
        }

        if(firstSubjectDay > 0){
            currentDay = 0
            while (currentDay < firstSubjectDay) {
                c.agenda[currentDay] = Day(subject = subjects[currentSubject])
                currentDay++
                currentSubject++
            }
        }

        currentSubject = 0
        firstSubjectDay++
    }

    classes.forEach { c ->
        val sortedAgenda = c.agenda.toSortedMap() // Sort the agenda by day
        val agendaString = sortedAgenda.entries.joinToString(" ") { (day, dayInfo) ->
            "$day=${dayInfo.subject.name}"
        }
        println("${c.name}: $agendaString")
    }
}