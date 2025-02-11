class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recycler_view)
        db = FirebaseFirestore.getInstance()

        sessionAdapter = SessionAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sessionAdapter

        loadSessions()
    }

    private fun loadSessions() {
        db.collection("sessions")
            .get()
            .addOnSuccessListener { documents ->
                val sessions = mutableListOf<Session>()
                for (document in documents) {
                    val session = document.toObject(Session::class.java)
                    sessions.add(session)
                }
                sessionAdapter.submitList(sessions)
            }
    }
}
