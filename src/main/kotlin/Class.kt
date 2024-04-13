data class Subject(
    val name: String,
)

data class Day(
    val subject: Subject
){
    override fun toString(): String {
        return subject.name;
    }
}

data class Class(
    val name: String,
    val agenda: MutableMap<Int, Day>
){
    override fun toString(): String {
        var agendaStr = "$name: "
        agenda.forEach{
            day -> agendaStr+="$day "
        }
        return agendaStr;
    }
}