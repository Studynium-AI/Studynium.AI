package com.StudyniumAI.androidApp.Model.PBKDF2

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.Base64

/**
* Encrypts and Decrypts the API key using the user's password. follows the PBKDF2 algorithm
* with encryption based on AES-GCM
 */
object ApiKeyEncryptor {
    private const val SALT_LENGTH = 16
    private const val IV_LENGTH = 12
    private const val KEY_LENGTH = 256
    private const val ITERATIONS = 125000
    private const val GCM_TAG_LENGTH = 128
    private const val PBKDF2_ALGO = "PBKDF2WithHmacSHA256"
    private const val CIPHER_ALGO = "AES/GCM/NoPadding"

    /**
     * Encrypts the API key using a key derived from the password via PBKDF2.
     * Returns a Base64-encoded string: salt:iv:encryptedData
     */
    @Throws(Exception::class)
    private fun encrypt(apiKey: String, password: String): String {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGO)
        val derivedKeyBytes = factory.generateSecret(spec).encoded
        val derivedKey: SecretKey = SecretKeySpec(derivedKeyBytes, "AES")
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        val cipher = Cipher.getInstance(CIPHER_ALGO)
        val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, derivedKey, gcmSpec)
        val encrypted = cipher.doFinal(apiKey.toByteArray(StandardCharsets.UTF_8))
        return "${Base64.getEncoder().encodeToString(salt)}:${Base64.getEncoder().encodeToString(iv)}:${Base64.getEncoder().encodeToString(encrypted)}"
    }

    /**
     * Decrypts the encrypted API key using the password.
     * Expects input as Base64-encoded: salt:iv:encryptedData
     */
    @Throws(Exception::class)
    private fun decrypt(encryptedData: String, password: String): String {
        val parts = encryptedData.split(":")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid encrypted data format")
        }
        val salt = Base64.getDecoder().decode(parts[0])
        val iv = Base64.getDecoder().decode(parts[1])
        val encrypted = Base64.getDecoder().decode(parts[2])
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGO)
        val derivedKeyBytes = factory.generateSecret(spec).encoded
        val derivedKey: SecretKey = SecretKeySpec(derivedKeyBytes, "AES")
        val cipher = Cipher.getInstance(CIPHER_ALGO)
        val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, derivedKey, gcmSpec)
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted, StandardCharsets.UTF_8)
    }
    fun encryptKey(apiKey: String, password: String): String {
        return encrypt(apiKey, password)
    }
    fun decryptKey(encryptedData: String, password: String): String {
        return decrypt(encryptedData, password)
    }
}