package com.student.recipes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var totalOmega3Score = 0
    private var currentMealPoints = 0
    private var currentCustomerIndex = 0

    private val customers = arrayOf("👨‍🍳 Chef Alex", "👩‍⚕️ Dr. Elena (Dietitian)", "🏋️‍♂️ Mark (Athlete)")
    private val goals = arrayOf(
        "Goal: High Omega-3 meal (Target: 15+ PTS)",
        "Goal: Light meal with high protein (Target: 10+ PTS)",
        "Goal: Maximum Omega-3 boost (Target: 20+ PTS)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvCustomer = findViewById<TextView>(R.id.tvCustomer)
        val tvOrder = findViewById<TextView>(R.id.tvOrder)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        val btnPalamut = findViewById<Button>(R.id.btnPalamut)
        val btnLevrek = findViewById<Button>(R.id.btnLevrek)
        val btnOrkinos = findViewById<Button>(R.id.btnOrkinos)
        val btnSardalya = findViewById<Button>(R.id.btnSardalya)
        val btnServe = findViewById<Button>(R.id.btnServe)
        val btnNextCustomer = findViewById<Button>(R.id.btnNextCustomer)

        // Add Fish Points Logic
        btnPalamut.setOnClickListener {
            currentMealPoints += 5
            tvStatus.text = "Selected: Added Bonito (+5 PTS) | Meal Total: $currentMealPoints PTS"
        }

        btnLevrek.setOnClickListener {
            currentMealPoints += 2
            tvStatus.text = "Selected: Added Sea Bass (+2 PTS) | Meal Total: $currentMealPoints PTS"
        }

        btnOrkinos.setOnClickListener {
            currentMealPoints += 10
            tvStatus.text = "Selected: Added Tuna (+10 PTS) | Meal Total: $currentMealPoints PTS"
        }

        btnSardalya.setOnClickListener {
            currentMealPoints += 4
            tvStatus.text = "Selected: Added Sardine (+4 PTS) | Meal Total: $currentMealPoints PTS"
        }

        // Serve Meal & Accumulate Total Score
        btnServe.setOnClickListener {
            if (currentMealPoints > 0) {
                totalOmega3Score += currentMealPoints
                tvScore.text = "TOTAL OMEGA-3: $totalOmega3Score PTS"
                Toast.makeText(this, "Meal Served! Added $currentMealPoints Omega-3 Points", Toast.LENGTH_SHORT).show()
                tvStatus.text = "Status: Meal served successfully! Total added: $currentMealPoints PTS"
                currentMealPoints = 0
            } else {
                Toast.makeText(this, "Please select at least one fish first!", Toast.LENGTH_SHORT).show()
            }
        }

        // Switch Customer
        btnNextCustomer.setOnClickListener {
            currentCustomerIndex = (currentCustomerIndex + 1) % customers.size
            tvCustomer.text = "Customer: ${customers[currentCustomerIndex]}"
            tvOrder.text = goals[currentCustomerIndex]
            tvStatus.text = "Selected: No fish added yet"
            currentMealPoints = 0
        }
    }
}