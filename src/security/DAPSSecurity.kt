package security

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class DAPSSecurity {
    companion object SecurityKeys {
        val hash_key = hex("8f74b48eeb9809d995d7b9916") // generated with pwgen 25 1 -A --remove-chars=ghijklmnopqrstuvwxyz
        val hmac_key = SecretKeySpec(hash_key, "HmacSHA1")

        /**
         * Method that hashes a [password] by using the globally defined secret key [hmac_key].
         */
        fun hash(password: String): String {
            val hmac = Mac.getInstance("HmacSHA1")
            hmac.init(hmac_key)
            return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
        }
    }
}
