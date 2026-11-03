package com.student.recipes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var score = 0
    private var lives = 3
    private var customerIndex = 0

    private var selectedBase = ""
    private val selectedToppings = mutableListOf<String>()

    // Customer Data
    private val avatars = arrayOf("👧", "👨‍🍳", "🧔", "👩‍🦰")
    private val names = arrayOf("Maya", "Chef Alex", "Baron", "Elena")
    private val targetBases = arrayOf("Classic Waffle", "Choco Waffle", "Red Velvet Waffle", "Classic Waffle")
    private val targetFruits = arrayOf("Strawberry", "Banana", "Strawberry", "Banana")
    private val targetSauces = arrayOf("Nutella", "Nutella", "Nutella", "Strawberry")

    private lateinit var tvScore: TextView
    private lateinit var tvLives: TextView
    private lateinit var tvCustomerAvatar: TextView
    private lateinit var tvCustomerName: TextView
    private lateinit var tvOrderBubble: TextView
    private lateinit var tvPlateBase: TextView
    private lateinit var tvPlateToppings: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvScore = findViewById(R.id.tvScore)
        tvLives = findViewById(R.id.tvLives)
        tvCustomerAvatar = findViewById(R.id.tvCustomerAvatar)
        tvCustomerName = findViewById(R.id.tvCustomerName)
        tvOrderBubble = findViewById(R.id.tvOrderBubble)
        tvPlateBase = findViewById(R.id.tvPlateBase)
        tvPlateToppings = findViewById(R.id.tvPlateToppings)

        val btnClassicBase = findViewById<Button>(R.id.btnClassicBase)
        val btnChocoBase = findViewById<Button>(R.id.btnChocoBase)
        val btnRedBase = findViewById<Button>(R.id.btnRedBase)

        val btnStrawberry = findViewById<Button>(R.id.btnStrawberry)
        val btnBanana = findViewById<Button>(R.id.btnBanana)
        val btnNutella = findViewById<Button>(R.id.btnNutella)

        val btnServe = findViewById<Button>(R.id.btnServe)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnNewGame = findViewById<Button>(R.id.btnNewGame)

        // Set Bases
        btnClassicBase.setOnClickListener { setBase("Classic Waffle", "🧇") }
        btnChocoBase.setOnClickListener { setBase("Choco Waffle", "🍫🧇") }
        btnRedBase.setOnClickListener { setBase("Red Velvet Waffle", "🔴🧇") }

        // Add Toppings
        btnStrawberry.setOnClickListener { addTopping("Strawberry", "🍓") }
        btnBanana.setOnClickListener { addTopping("Banana", "🍌") }
        btnNutella.setOnClickListener { addTopping("Nutella", "🍫") }

        btnServe.setOnClickListener { serveOrder() }
        btnClear.setOnClickListener { clearPlate() }
        btnNewGame.setOnClickListener { resetGame() }

        loadCustomer()
    }

    private fun setBase(baseName: String, emoji: String) {
        if (lives <= 0) return
        selectedBase = baseName
        tvPlateBase.text = emoji
        updatePlateUI()
    }

    private fun addTopping(toppingName: String, emoji: String) {
        if (lives <= 0) return
        if (selectedBase.isEmpty()) {
            Toast.makeText(this, "Select a Waffle Base first!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!selectedToppings.contains(toppingName)) {
            selectedToppings.add(toppingName)
            updatePlateUI()
        }
    }

    private fun updatePlateUI() {
        if (selectedBase.isEmpty()) {
            tvPlateToppings.text = "Select ingredients below..."
        } else {
            val toppingsStr = if (selectedToppings.isEmpty()) "Plain Base" else selectedToppings.joinToString(" + ")
            tvPlateToppings.text = "$selectedBase\n($toppingsStr)"
        }
    }

    private fun clearPlate() {
        selectedBase = ""
        selectedToppings.clear()
        tvPlateBase.text = "🍽️ EMPTY"
        updatePlateUI()
    }

    private fun loadCustomer() {
        tvCustomerAvatar.text = avatars[customerIndex]
        tvCustomerName.text = "Customer: ${names[customerIndex]}"
        tvOrderBubble.text = "💬 'I want a ${targetBases[customerIndex]} with ${targetFruits[customerIndex]} & ${targetSauces[customerIndex]}!'"
        clearPlate()
    }

    private fun serveOrder() {
        if (lives <= 0) return

        if (selectedBase.isEmpty()) {
            Toast.makeText(this, "The plate is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val correctBase = targetBases[customerIndex]
        val correctFruit = targetFruits[customerIndex]
        val correctSauce = targetSauces[customerIndex]

        val isMatch = (selectedBase == correctBase) &&
                selectedToppings.contains(correctFruit) &&
                selectedToppings.contains(correctSauce)

        if (isMatch) {
            score += 25
            Toast.makeText(this, "PERFECT WAFFLE! +25 PTS 🌟", Toast.LENGTH_SHORT).show()
        } else {
            lives--
            Toast.makeText(this, "WRONG ORDER! Lost 1 Heart 💔", Toast.LENGTH_SHORT).show()
        }

        tvScore.text = "SCORE: $score"
        tvLives.text = when (lives) {
            3 -> "❤️ ❤️ ❤️"
            2 -> "❤️ ❤️ 🖤"
            1 -> "❤️ 🖤 🖤"
            else -> "🖤 🖤 🖤"
        }

        if (lives > 0) {
            customerIndex = (customerIndex + 1) % names.size
            loadCustomer()
        } else {
            tvOrderBubble.text = "💀 GAME OVER! Press RESTART to try again."
            Toast.makeText(this, "Game Over! Final Score: $score", Toast.LENGTH_LONG).show()
        }
    }

    private fun resetGame() {
        score = 0
        lives = 3
        customerIndex = 0
        tvScore.text = "SCORE: 0"
        tvLives.text = "❤️ ❤️ ❤️"
        loadCustomer()
        Toast.makeText(this, "New Game Started!", Toast.LENGTH_SHORT).show()
    }
}