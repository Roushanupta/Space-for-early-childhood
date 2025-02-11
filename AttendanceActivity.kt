class AttendanceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var attendanceAdapter: AttendanceAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var sessionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        recyclerView = findViewById(R.id.recycler_view)
        sessionId = intent.getStringExtra("SESSION_ID")!!

        db = FirebaseFirestore.getInstance()

        attendanceAdapter = AttendanceAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = attendanceAdapter

        loadStudents()
    }

    private fun loadStudents() {
        db.collection("sessions").document(sessionId).collection("students")
            .get()
            .addOnSuccessListener { documents ->
                val students = mutableListOf<Student>()
                for (document in documents) {
                    val student = document.toObject(Student::class.java)
                    students.add(student)
                }
                attendanceAdapter.submitList(students)
            }
    }
}
