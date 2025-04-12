package com.platform.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.exceptions.EncryptionException;
import com.platform.logging.Log;
import com.platform.security.config.DbSecretConfigurartion;

/**
 * @author Muhil
 */
@Component
public class EncryptionUtil {

	private static String ALGORITHM = "AES/CBC/PKCS5PADDING"; //cipher block chaining
	private static final int IV_SIZE = 16;

	public static DbSecretConfigurartion dbSecrets;

	@Autowired
	public void setDbSecrets(DbSecretConfigurartion dbSecrets) {
		EncryptionUtil.dbSecrets = dbSecrets;
	}

	public static String hash_SHA256(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return Base64.encodeBase64String(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}

	public static String hash_SHA512(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return Base64.encodeBase64String(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}

	public static String encrypt_AES(String value) throws EncryptionException {
		try {
			IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(dbSecrets.getInitVector()));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(dbSecrets.getSecret()), "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception ex) {
			Log.platform.error("Exception encrypting String {} : {}", value, ex);
			throw new EncryptionException();
		}
	}

	public static String decrypt_AES(String encrypted) throws EncryptionException {
		try {
			IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(dbSecrets.getInitVector()));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(dbSecrets.getSecret()), "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			return new String(original);
		} catch (Exception ex) {
			Log.platform.error("Exception decrypting String {} : {}", encrypted, ex);
			throw new EncryptionException();
		}
	}

	// to provide additional security init vector is manipulated dynamically so
	// patterns cannot be found on encrypted text 
	 // Generate a random IV
    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

	// Encrypts a given plaintext
	public static String encrypt(String plaintext) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(dbSecrets.getSecret()), "AES");
		IvParameterSpec iv = generateIv();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(plaintext.getBytes());
		// Combine IV and encrypted data and encode in Base64 (this dynamic IV will be used for decryption)
		byte[] combined = new byte[IV_SIZE + encrypted.length];
		System.arraycopy(iv.getIV(), 0, combined, 0, IV_SIZE);
		System.arraycopy(encrypted, 0, combined, IV_SIZE, encrypted.length);
		return Base64.encodeBase64String(combined);
	}
	
	// Decrypts a given ciphertext
	public static String decrypt(String encryptedText) throws Exception {
		byte[] combined = Base64.decodeBase64(encryptedText);
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(dbSecrets.getSecret()), "AES");
		// Extract IV
		byte[] iv = new byte[IV_SIZE];
		System.arraycopy(combined, 0, iv, 0, IV_SIZE);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		// Extract encrypted data
		byte[] encryptedBytes = new byte[combined.length - IV_SIZE];
		System.arraycopy(combined, IV_SIZE, encryptedBytes, 0, encryptedBytes.length);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
		byte[] decrypted = cipher.doFinal(encryptedBytes);
		return new String(decrypted);
	}
}
