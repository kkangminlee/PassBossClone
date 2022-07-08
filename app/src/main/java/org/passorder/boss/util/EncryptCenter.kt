package org.passorder.boss.util

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.math.floor

object EncryptCenter {
    private const val AES_ENCRYPTION_KEY = "VKDOWHGMDKZOCLDJEKRTQOSNCMVZXXDL"
    private const val INIT_VECTOR = "ZZXOCMDKFFOEQWSA"
    fun encrypt(raw: String): String? {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(sha256Encrypt(AES_ENCRYPTION_KEY), "AES")
            val ivSpec = IvParameterSpec(
                sha256Encrypt(INIT_VECTOR).copyOfRange(0, 16)
            )
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val result = makePlain(raw)
            val results = cipher.doFinal(result.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(results, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun sha256Encrypt(sha: String): ByteArray {
        try {
            // Create MD5 Hash
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update(sha.toByteArray(StandardCharsets.UTF_8))
            return digest.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ByteArray(32)
    }

    private fun makePlain(text: String): String {
        val times = floor((text.length / 16).toDouble()).toInt() + 1
        val zeroCount = 16 - text.length % 16
        val builder = StringBuilder()

        // Put Zero String
        builder.append(zeroCount)

        // Put #s
        while (builder.length != 16) builder.append("#")

        // Put raw text
        builder.append(text)

        // Put 0s
        while (builder.length != 16 * (times + 1)) {
            builder.append("0")
        }

        // Example 7###############passorder0000000
        return builder.toString()
    }
}