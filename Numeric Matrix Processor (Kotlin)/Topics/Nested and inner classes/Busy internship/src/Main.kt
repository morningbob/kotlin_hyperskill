class Intern(val weeklyWorkload: Int) {
    val baseWorkload = 20

    class Salary {
        val basePay = 50
        val extraHoursPay = 2.8
    }

    val weeklySalary = Salary().basePay + (weeklyWorkload - 20)*Salary().extraHoursPay// put your code here
}