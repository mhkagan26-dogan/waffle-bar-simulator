package com.student.recipes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var totalChefScore = 0
    private var wafflesServedCount = 0

    private var currentWafflePoints = 0
    private var currentIngredients = mutableListOf<String>()
    private var hasBase = false

    private var customerIndex = 0

    private val customerNames = arrayOf(
        "👤 Emily (Berry Lover)",
        "👤 Marco (Chocolate Addict)",
        "👤 Chef Pierre (Gourmet Judge)"
    )

    private val customerGoals = arrayOf(
        "Order Goal: Wants Strawberry (+10 Bonus PTS)",
        "Order Goal: Wants Nutella or Choco Base (+10 Bonus PTS)",
        "Order Goal: High value waffle target 25+ PTS (+15 Bonus PTS)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvTotalScore = findViewById<TextView>(R.id.tvTotalScore)
        val tvWaffleCount = findViewById<TextView>(R.id.tvWaffleCount)
        val tvCustomer = findViewById<TextView>(R.id.tvCustomer)
        val tvOrderGoal = findViewById<TextView>(R.id.tvOrderGoal)
        val tvCurrentBuild = findViewById<TextView>(R.id.tvCurrentBuild)
        val tvCurrentPoints = findViewById<TextView>(R.id.tvCurrentPoints)

        // Base Buttons
        val btnClassicBase = findViewById<Button>(R.id.btnClassicBase)
        val btnChocoBase = findViewById<Button>(R.id.btnChocoBase)
        val btnBubbleBase = findViewById<Button>(R.id.btnBubbleBase)

        // Fruit Buttons
        val btnStrawberry = findViewById<Button>(R.id.btnStrawberry)
        val btnBanana = findViewById<Button>(R.id.btnBanana)
        val btnKiwi = findViewById<Button>(R.id.btnKiwi)

        // Sauce & Extras
        val btnNutella = findViewById<Button>(R.id.btnNutella)
        val btnMaple = findViewById<Button>(R.id.btnMaple)
        val btnIceCream = findViewById<Button>(R.id.btnIceCream)

        // Action Buttons
        val btnServe = findViewById<Button>(R.id.btnServe)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnNextCustomer = findViewById<Button>(R.id.btnNextCustomer)

        fun updateBuildDisplay() {
            if (currentIngredients.isEmpty()) {
                tvCurrentBuild.text = "Current Plate: Empty Waffle Plate"
            } else {
                tvCurrentBuild.text = "Plate: " + currentIngredients.joinToString(", ")
            }
            tvCurrentPoints.text = "Waffle Value: $currentWafflePoints PTS"
        }

        fun resetWafflePlate() {
            currentWafflePoints = 0
            currentIngredients.clear()
            hasBase = false
            updateBuildDisplay()
        }

        fun addIngredient(name: String, pts: Int, isBase: Boolean = false) {
            if (isBase && hasBase) {
                Toast.makeText(this, "Base already selected!", Toast.LENGTH_SHORT).show()
                return
            }
            if (isBase) hasBase = true

            currentIngredients.add(name)
            currentWafflePoints += pts
            updateBuildDisplay()
        }

        // Base Listeners
        btnClassicBase.setOnClickListener { addIngredient("Classic Base", 5, true) }
        btnChocoBase.setOnClickListener { addIngredient("Choco Base", 8, true) }
        btnBubbleBase.setOnClickListener { addIngredient("Bubble Base", 10, true) }

        // Fruit Listeners
        btnStrawberry.setOnClickListener { addIngredient("Strawberry", 6) }
        btnBanana.setOnClickListener { addIngredient("Banana", 4) }
        btnKiwi.setOnClickListener { addIngredient("Kiwi", 5) }

        // Extras Listeners
        btnNutella.setOnClickListener { addIngredient("Nutella", 7) }
        btnMaple.setOnClickListener { addIngredient("Maple Syrup", 5) }
        btnIceCream.setOnClickListener { addIngredient("Ice Cream", 8) }

        // Serve Waffle
        btnServe.setOnClickListener {
            if (!hasBase) {
                Toast.makeText(this, "Please select a Waffle Base first!", Toast.LENGTH_SHORT).show()
                return
            }

            var bonus = 0
            when (customerIndex) {
                0 -> if (currentIngredients.contains("Strawberry")) bonus = 10
                1 -> if (currentIngredients.contains("Nutella") || currentIngredients.contains("Choco Base")) bonus = 10
                2 -> if (currentWafflePoints >= 25) bonus = 15
            }

            val earned = currentWafflePoints + bonus
            totalChefScore += earned
            wafflesServedCount++

            tvTotalScore.text = "TOTAL CHEF SCORE: $totalChefScore PTS"
            tvWaffleCount.text = "Waffles Served: $wafflesServedCount"

            if (bonus > 0) {
                Toast.makeText(this, "PERFECT ORDER! +$bonus Bonus PTS Added!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Waffle Served! +$earned PTS", Toast.LENGTH_SHORT).show()
            }

            resetWafflePlate()
        }

        // Clear Plate
        btnClear.setOnClickListener {
            resetWafflePlate()
            Toast.makeText(this, "Plate Cleared!", Toast.LENGTH_SHORT).show()
        }

        // Next Customer
        btnNextCustomer.setOnClickListener {
            customerIndex = (customerIndex + 1) % customerNames.size
            tvCustomer.text = customerNames[customerIndex]
            tvOrderGoal.text = customerGoals[customerIndex]
            resetWafflePlate()
        }
    }
}