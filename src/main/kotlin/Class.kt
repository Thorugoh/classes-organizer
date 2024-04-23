data class Subject(
    val name: String,
    val hoursPerWeek: Int,
)

data class Day(
    var subjects: MutableList<Subject?>,
    val className: String,
    val weekDay: Int,
){
    private val totalHours = 4;
    private var blankTimes = mutableListOf<Int>()

    private fun hasSubjectInOtherClassAtTime(time: Int, subjectName: String, classes: Array<Class>): Boolean{
        return  classes.any { classInfo ->
            classInfo.name != className &&
                    (classInfo.week[weekDay]?.subjects?.size ?: 0) > time &&
                    classInfo.week[weekDay]?.subjects?.get(time)?.name == subjectName
        }
    }
    fun addSubject(subject: Subject, classes: Array<Class>): Subject {
        if(subjects.size >= totalHours && blankTimes.isEmpty()) {
            return subject;
        }

        if(subject.hoursPerWeek <= 0){
            return Subject(subject.name, 0)
        }

        if(subjects.filter { it?.name == subject.name }.size >= 2) {
            return subject;
        }


        if(hasSubjectInOtherClassAtTime(time = subjects.size, subjectName = subject.name, classes = classes)) {
            subjects.add(null);
            blankTimes.add(subjects.size - 1)
        }else {
            if(blankTimes.isNotEmpty() && !hasSubjectInOtherClassAtTime(time = blankTimes.first(), subjectName = subject.name, classes = classes)){
                subjects[blankTimes.removeAt(0)] = Subject(subject.name, 1)
                return Subject(subject.name, subject.hoursPerWeek - 1)
            }else if(subjects.size < totalHours){
                subjects.add(Subject(subject.name, 1))
                return addSubject(Subject(subject.name, subject.hoursPerWeek - 1), classes)
            } else {
                return subject;
            }
        }
        return addSubject(Subject(subject.name, subject.hoursPerWeek), classes)
    }

    override fun toString(): String {
        var str = ""
        subjects.forEachIndexed { index, it ->
            str = if(index == 3) {
                str + it?.name + " ---"
            } else {
                str + it?.name + " "
            }
        }
        return str;
    }
}

data class Class(val name: String) {
    val week: Map<Int, Day> = mapOf(
        0 to Day(mutableListOf(), name, 0),
        1 to Day(mutableListOf(), name, 1),
        2 to  Day(mutableListOf(), name, 2),
        3 to Day(mutableListOf(), name, 3),
        4 to Day(mutableListOf(), name, 4),
    )

    fun addSubject(subjects: Array<Subject>, classes: Array<Class>) {
        val s = subjects.toMutableList()
        for((index, subject) in s.withIndex()) {
            var currentSubject = subject;
            for ((day, agenda) in week) {
                currentSubject = agenda.addSubject(currentSubject, classes)
                s[index] = currentSubject

            }
        }
    }

    fun getHoursFromWeek(): Int{
        var totalHours = 0;
        for((index, agenda) in week){
            totalHours += agenda.subjects.filterNotNull().size
        }
        return totalHours
    }

    override fun toString(): String {
        var weekStr = "$name: "
        week.forEach{
            day -> weekStr+="$day "
        }
        return weekStr;
    }
}