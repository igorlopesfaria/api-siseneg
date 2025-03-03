package br.com.finlegacy.api.core.extensions

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Extension function to validate if a CNPJ is valid.
 */
fun String.isValidCNPJ(): Boolean {
    val cnpj = this.filter { it.isDigit() }

    // CNPJ must be 14 digits
    if (cnpj.length != 14) return false

    // Invalid CNPJs
    if (cnpj.all { it == cnpj[0] }) return false

    // CNPJ validation weights
    val weightFirstPart = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    val weightSecondPart = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    // Calculate first verification digit
    val firstCheckDigit = calculateCheckDigit(cnpj, weightFirstPart)
    if (firstCheckDigit != cnpj[12].digitToInt()) return false

    // Calculate second verification digit
    val secondCheckDigit = calculateCheckDigit(cnpj, weightSecondPart)
    if (secondCheckDigit != cnpj[13].digitToInt()) return false

    return true
}

/**
 * Helper function to calculate the CNPJ check digit based on weights.
 */
private fun calculateCheckDigit(cnpj: String, weights: IntArray): Int {
    val sum = cnpj
        .substring(0, weights.size)
        .mapIndexed { index, char -> char.digitToInt() * weights[index] }
        .sum()

    val remainder = sum % 11
    return if (remainder < 2) 0 else 11 - remainder
}

fun String.isValidCPF(): Boolean {
    // CPF regex to validate the structure (11 digits)
    if (!this.matches(Regex("\\d{11}"))) return false

    // Check for known invalid CPF patterns (e.g., all digits being the same)
    val invalidCpfs = setOf(
        "00000000000", "11111111111", "22222222222", "33333333333",
        "44444444444", "55555555555", "66666666666", "77777777777",
        "88888888888", "99999999999"
    )
    if (this in invalidCpfs) return false

    // Validate the first check digit
    val firstCheckDigit = calculateCheckDigit(this, 10)
    if (firstCheckDigit != this[9].toString().toInt()) return false

    // Validate the second check digit
    val secondCheckDigit = calculateCheckDigit(this, 11)
    return secondCheckDigit == this[10].toString().toInt()
}

// Helper function to calculate the CPF check digits
private fun calculateCheckDigit(cpf: String, weight: Int): Int {
    var sum = 0
    for (i in 0 until weight - 1) {
        sum += (cpf[i].toString().toInt()) * (weight - i)
    }
    val remainder = sum % 11
    return if (remainder < 2) 0 else 11 - remainder
}

fun String.isValidEmail(): Boolean {
    // Simple regex to validate the email format
    val emailRegex = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
    return this.matches(Regex(emailRegex))
}

fun String.isValidPassword(): Boolean {
    // Regular expression to check password validity
    val passwordRegex = Regex(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
    )
    return this.matches(passwordRegex)
}

fun String.isValidBoolean(): Boolean {
    return this.equals("true", ignoreCase = true) || this.equals("false", ignoreCase = true)
}

// Extension function to hash a string using bcrypt
fun String.toEncrypt(): String {
    val md = MessageDigest.getInstance("MD5")
    val hashedBytes = md.digest(this.toByteArray())
    return hashedBytes.joinToString("") { "%02x".format(it) }
}

fun String.isValidRG(): Boolean {
    // Remove any non-digit characters (pontos, hifens, etc.)
    val cleanRG = this.replace("[^\\d]".toRegex(), "")

    // Check if the cleaned RG has exactly 9 digits
    return cleanRG.length == 9 && cleanRG.all { it.isDigit() }
}

fun String.isValidPhoneNumber(): Boolean {
    // Remove qualquer caractere que não seja dígito (espaços, hífens, parênteses, etc.)
    val cleanPhoneNumber = this.replace("[^\\d]".toRegex(), "")

    // Verifica se o número tem entre 10 e 11 dígitos (formato válido no Brasil)
    return cleanPhoneNumber.length in 10..11 && cleanPhoneNumber.all { it.isDigit() }
}

fun String.isValidBrazilianBirthDate(): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    dateFormat.isLenient = false // Para garantir que a data seja estritamente válida

    return try {
        val date = dateFormat.parse(this)
        val currentDate = java.util.Date()

        // Verifica se a data é no passado e não futura
        date != null && !date.after(currentDate)
    } catch (e: Exception) {
        false // Retorna falso se a data não puder ser analisada
    }
}
fun String.isValidCEP(): Boolean {
    // Regex to match the CEP format (either 12345-678 or 12345678)
    val cepRegex = """^\d{5}-\d{3}$|^\d{8}$""".toRegex()
    return this.matches(cepRegex)
}

fun String.isValidPrice(): Boolean {
    // Regex to match numbers with exactly two decimal places (e.g., "12.34", "0.99")
    val priceRegex = Regex("^\\d+\\.\\d{2}\$")

    // Check if it matches the regex and is greater than zero
    return this.matches(priceRegex) && this.toDouble() > 0.0
}
