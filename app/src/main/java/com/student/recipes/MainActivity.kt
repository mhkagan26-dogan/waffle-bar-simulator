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

    private var selectedBaseName = ""
    private var selectedBaseEmoji = ""

    private val selectedToppingsMap = mutableMapOf<String, String>()

    // Customers Data
    private val avatars = arrayOf("👧", "👨‍🍳", "🧔", "👩‍🦰")
    private val names = arrayOf("Maya", "Chef Alex", "Baron", "Elena")
    private val reqBases = arrayOf("Classic Waffle", "Choco Waffle", "Red Velvet Waffle", "Classic Waffle")
    private val reqBaseEmojis = arrayOf("🧇", "🍫🧇", "🔴🧇", "🧇")

    private val reqItem1 = arrayOf("Strawberry", "Bonbon", "Strawberry", "Banana")
    private val reqEmoji1 = arrayOf("🍓", "🍬", "🍓", "🍌")
    private val reqItem2 = arrayOf("White Chocolate", "Nutella", "White Chocolate", "Bonbon")
    private val reqEmoji2 = arrayOf("⚪", "🍫", "⚪", "🍬")

    private lateinit var tvScore: TextView
    private lateinit var tvLives: TextView
    private lateinit var tvCustomerAvatar: TextView
    private lateinit var tvCustomerName: TextView
    private lateinit var tvOrderBubble: TextView
    private lateinit var tvPlateBase: TextView
    private lateinit var tvPlateToppingsVisual: TextView
    private lateinit var tvPlateToppingsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mor çubuktaki başlığı "Waffle Bar Simulator" yapıyoruz:
        supportActionBar?.title = "Waffle Bar Simulator"

        tvScore = findViewById(R.id.tvScore)
        tvLives = findViewById(R.id.tvLives)
        tvCustomerAvatar = findViewById(R.id.tvCustomerAvatar)
        tvCustomerName = findViewById(R.id.tvCustomerName)
        tvOrderBubble = findViewById(R.id.tvOrderBubble)
        tvPlateBase = findViewById(R.id.tvPlateBase)
        tvPlateToppingsVisual = findViewById(R.id.tvPlateToppingsVisual)
        tvPlateToppingsText = findViewById(R.id.tvPlateToppingsText)

        // Base Buttons
        val btnClassicBase = findViewById<Button>(R.id.btnClassicBase)
        val btnChocoBase = findViewById<Button>(R.id.btnChocoBase)
        val btnRedBase = findViewById<Button>(R.id.btnRedBase)

        // Topping Buttons
        val btnStrawberry = findViewById<Button>(R.id.btnStrawberry)
        val btnBanana = findViewById<Button>(R.id.btnBanana)
        val btnBonbon = findViewById<Button>(R.id.btnBonbon)

        // Extras Buttons
        val btnNutella = findViewById<Button>(R.id.btnNutella)
        val btnWhiteChoco = findViewById<Button>(R.id.btnWhiteChoco)
        val btnIceCream = findViewById<Button>(R.id.btnIceCream)

        // Actions
        val btnServe = findViewById<Button>(R.id.btnServe)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnNewGame = findViewById<Button>(R.id.btnNewGame)

        // Base Listeners
        btnClassicBase.setOnClickListener { setBase("Classic Waffle", "🧇") }
        btnChocoBase.setOnClickListener { setBase("Choco Waffle", "🍫🧇") }
        btnRedBase.setOnClickListener { setBase("Red Velvet Waffle", "🔴🧇") }

        // Topping Listeners
        btnStrawberry.setOnClickListener { addTopping("Strawberry", "🍓") }
        btnBanana.setOnClickListener { addTopping("Banana", "🍌") }
        btnBonbon.setOnClickListener { addTopping("Bonbon", "🍬") }

        btnNutella.setOnClickListener { addTopping("Nutella", "🍫") }
        btnWhiteChoco.setOnClickListener { addTopping("White Chocolate", "⚪") }
        btnIceCream.setOnClickListener { addTopping("Ice Cream", "🍨") }

        btnServe.setOnClickListener { serveOrder() }
        btnClear.setOnClickListener { clearPlate() }
        btnNewGame.setOnClickListener { resetGame() }

        loadCustomer()
    }

    private fun setBase(name: String, emoji: String) {
        if (lives <= 0) return
        selectedBaseName = name
        selectedBaseEmoji = emoji
        tvPlateBase.text = emoji
        updatePlateUI()
    }

    private fun addTopping(name: String, emoji: String) {
        if (lives <= 0) return
        if (selectedBaseName.isEmpty()) {
            Toast.makeText(this, "Select a Waffle Base first!", Toast.LENGTH_SHORT).show()
            return
        }
        selectedToppingsMap[name] = emoji
        updatePlateUI()
    }

    private fun updatePlateUI() {
        if (selectedBaseName.isEmpty()) {
            tvPlateBase.text = "🍽️"
            tvPlateToppingsVisual.text = ""
            tvPlateToppingsText.text = "Select base & toppings below..."
        } else {
            tvPlateBase.text = selectedBaseEmoji
            val visualEmojis = selectedToppingsMap.values.joinToString(" ")
            tvPlateToppingsVisual.text = visualEmojis

            val textNames = if (selectedToppingsMap.isEmpty()) "Plain Base" else selectedToppingsMap.keys.joinToString(" + ")
            tvPlateToppingsText.text = "$selectedBaseName ($textNames)"
        }
    }

    private fun clearPlate() {
        selectedBaseName = ""
        selectedBaseEmoji = ""
        selectedToppingsMap.clear()
        updatePlateUI()
    }

    private fun loadCustomer() {
        tvCustomerAvatar.text = avatars[customerIndex]
        tvCustomerName.text = "Customer: ${names[customerIndex]}"
        tvOrderBubble.text = "💬 'Wants ${reqBases[customerIndex]} + ${reqEmoji1[customerIndex]} ${reqItem1[customerIndex]} + ${reqEmoji2[customerIndex]} ${reqItem2[customerIndex]}'"
        clearPlate()
    }

    private fun serveOrder() {
        if (lives <= 0) return

        if (selectedBaseName.isEmpty()) {
            Toast.makeText(this, "Plate is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val targetBase = reqBases[customerIndex]
        val targetItem1 = reqItem1[customerIndex]
        val targetItem2 = reqItem2[customerIndex]

        val isMatch = (selectedBaseName == targetBase) &&
                selectedToppingsMap.containsKey(targetItem1) &&
                selectedToppingsMap.containsKey(targetItem2)

        if (isMatch) {
            score += 30
            Toast.makeText(this, "PERFECT WAFFLE! +30 PTS 🌟", Toast.LENGTH_SHORT).show()
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