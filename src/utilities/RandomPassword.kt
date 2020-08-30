package utilities

import java.time.LocalDateTime
import kotlin.random.Random

object RandomPassword {
    fun password(): String {
        val temp_password_builder = StringBuilder("i1l45f9s012s47cJ7x0z")
        val random = Random(LocalDateTime.now().second)
        // Generate a temporary password
        val alphabet: CharArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(0)
        val special_chars: CharArray = "#\$%".toCharArray(0)
        for (i in 0..19) {
            // if even pick a letter
            if (i % 2 == 0) {
                when {
                    // Lowercase Letter
                    i % 4 == 0 -> {
                        temp_password_builder[i] = alphabet[random.nextInt(0, alphabet.size-26)]
                    }
                    // Capital Letter
                    i % 6 == 0 -> {
                        temp_password_builder[i] = alphabet[random.nextInt(26, alphabet.size-1)]
                    }
                    // Pick wherever
                    else -> {
                        temp_password_builder[i] = alphabet[random.nextInt(0, alphabet.size-1)]
                    }
                }
            }
            // if odd pick an integer
            if (i % 3 == 0) {
                temp_password_builder[i] = random.nextInt(0,9).toString().first()
            }
            // pick a symbol at this index
            if (i == 11) {
                temp_password_builder[i] = special_chars[random.nextInt(0,special_chars.size-1)]
            }
        }
        return temp_password_builder.toString().substring(0..18)
    }
}
