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
    private val currentIngredients = mutableListOf<String>()
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

    private lateinit var tvTotalScore: TextView
    private lateinit var tvWaffleCount: TextView
    private lateinit var tvCustomer: TextView
    private lateinit var tvOrderGoal: TextView
    private lateinit var tvCurrentBuild: TextView
    private lateinit var tvCurrentPoints: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTotalScore = findViewById(R.id.tvTotalScore)
        tvWaffleCount = findViewById(R.id.tvWaffleCount)
        tvCustomer = findViewById(R.id.tvCustomer)
        tvOrderGoal = findViewById(R.id.tvOrderGoal)
        tvCurrentBuild = findViewById(R.id.tvCurrentBuild)
        tvCurrentPoints = findViewById(R.id.tvCurrentPoints)

        val btnClassicBase = findViewById<Button>(R.id.btnClassicBase)
        val btnChocoBase = findViewById<Button>(R.id.btnChocoBase)
        val btnBubbleBase = findViewById<Button>(R.id.btnBubbleBase)

        val btnStrawberry = findViewById<Button>(R.id.btnStrawberry)
        val btnBanana = findViewById<Button>(R.id.btnBanana)
        val btnKiwi = findViewById<Button>(R.id.btnKiwi)

        val btnNutella = findViewById<Button>(R.id.btnNutella)
        val btnMaple = findViewById<Button>(R.id.btnMaple)
        val btnIceCream = findViewById<Button>(R.id.btnIceCream)

        val btnServe = findViewById<Button>(R.id.btnServe)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnNextCustomer = findViewById<Button>(R.id.btnNextCustomer)

        btnClassicBase.setOnClickListener { addIngredient("Classic Base", 5, true) }
        btnChocoBase.setOnClickListener { addIngredient("Choco Base", 8, true) }
        btnBubbleBase.setOnClickListener { addIngredient("Bubble Base", 10, true) }

        btnStrawberry.setOnClickListener { addIngredient("Strawberry", 6, false) }
        btnBanana.setOnClickListener { addIngredient("Banana", 4, false) }
        btnKiwi.setOnClickListener { addIngredient("Kiwi", 5, false) }

        btnNutella.setOnClickListener { addIngredient("Nutella", 7, false) }
        btnMaple.setOnClickListener { addIngredient("Maple Syrup", 5, false) }
        btnIceCream.setOnClickListener { addIngredient("Ice Cream", 8, false) }

        btnServe.setOnClickListener {
            if (!hasBase) {
                Toast.makeText(this, "Please select a Waffle Base first!", Toast.LENGTH_SHORT).show()
            } else {
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
        }

        btnClear.setOnClickListener {
            resetWafflePlate()
            Toast.makeText(this, "Plate Cleared!", Toast.LENGTH_SHORT).show()
        }

        btnNextCustomer.setOnClickListener {
            customerIndex = (customerIndex + 1) % customerNames.size
            tvCustomer.text = customerNames[customerIndex]
            tvOrderGoal.text = customerGoals[customerIndex]
            resetWafflePlate()
        }
    }

    private fun updateBuildDisplay() {
        if (currentIngredients.isEmpty()) {
            tvCurrentBuild.text = "Current Plate: Empty Waffle Plate"
        } else {
            tvCurrentBuild.text = "Plate: " + currentIngredients.joinToString(", ")
        }
        tvCurrentPoints.text = "Waffle Value: $currentWafflePoints PTS"
    }

    private fun resetWafflePlate() {
        currentWafflePoints = 0
        currentIngredients.clear()
        hasBase = false
        updateBuildDisplay()
    }

    private fun addIngredient(name: String, pts: Int, isBase: Boolean) {
        if (isBase && hasBase) {
            Toast.makeText(this, "Base already selected!", Toast.LENGTH_SHORT).show()
            return
        }
        if (isBase) hasBase = true

        currentIngredients.add(name)
        currentWafflePoints += pts
        updateBuildDisplay()
    }
}