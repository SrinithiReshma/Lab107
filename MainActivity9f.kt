package com.example.practicepartone



import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var resultText: TextView
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        val textShown = findViewById<TextView>(R.id.new1)
        val dateNeed = findViewById<DatePicker>(R.id.calenview)

        dateNeed.setOnDateChangeListener { _, year, month, dayOfMonth ->
            showDatePicker(year, month, dayOfMonth)
        }

        private fun showDatePicker(year: Int, month: Int, day: Int) {
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                textShown.text = "Date: $selectedDay/${selectedMonth + 1}/$selectedYear"
            }, year, month, day)

            datePickerDialog.show()
        }
        val msg = findViewById<EditText>(R.id.messageBox)
        val emailBtn = findViewById<Button>(R.id.emailBtn)
        val whatsappBtn = findViewById<Button>(R.id.whatsappBtn)

        emailBtn.setOnClickListener {
            val text = msg.text.toString()
            val email = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("test@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Test")
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(email, "Send Email"))
        }

        whatsappBtn.setOnClickListener {
            val text = msg.text.toString()
            val whatsapp = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
                setPackage("com.whatsapp")
            }
            try {
                startActivity(whatsapp)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
        nameInput = findViewById(R.id.editName)
        ageInput = findViewById(R.id.editAge)
        emailInput = findViewById(R.id.editEmail)
        resultText = findViewById(R.id.textResult)

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()
            val email = emailInput.text.toString()
            val id = dbRef.push().key!!

            val user = User(id, name, age, email)
            dbRef.child(id).setValue(user)
                .addOnSuccessListener {
                    resultText.text = "User added!"
                }
                .addOnFailureListener {
                    resultText.text = "Failed to add user."
                }
        }

        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()
            val email = emailInput.text.toString()
            val id = email.replace(".", "_")  // Using email as key

            val user = User(id, name, age, email)
            dbRef.child(id).setValue(user)
                .addOnSuccessListener {
                    resultText.text = "User updated!"
                }
                .addOnFailureListener {
                    resultText.text = "Failed to update user."
                }
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val email = emailInput.text.toString()
            val id = email.replace(".", "_")

            dbRef.child(id).removeValue()
                .addOnSuccessListener {
                    resultText.text = "User deleted!"
                }
                .addOnFailureListener {
                    resultText.text = "Failed to delete user."
                }
        }

        findViewById<Button>(R.id.btnView).setOnClickListener {
            dbRef.get().addOnSuccessListener {
                val data = it.children.joinToString("\n") { snap ->
                    val user = snap.getValue(User::class.java)
                    "${user?.name}, ${user?.age}, ${user?.email}"
                }
                resultText.text = data
            }.addOnFailureListener {
                resultText.text = "Failed to fetch data."
            }
        }
    }
}