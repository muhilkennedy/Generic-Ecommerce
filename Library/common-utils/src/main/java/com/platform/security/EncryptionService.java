package com.platform.security;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.exceptions.CryptoException;
import com.platform.exceptions.EncryptionException;
import com.platform.security.config.FileSecretConfiguration;
import com.platform.util.EncryptionUtil;
import com.platform.util.FileCryptoUtil;

/**
 * @author Muhil
 *
 */
@Service
public class EncryptionService {

	@Autowired
	private FileSecretConfiguration fileSecret;

	public File encryptFile(File inputFile) throws CryptoException, IOException {
		return FileCryptoUtil.encrypt(fileSecret.getSecret(), inputFile);
	}

	public File decrypFile(File inputFile) throws CryptoException, IOException {
		return FileCryptoUtil.decrypt(fileSecret.getSecret(), inputFile);
	}

	public String encryptField(String value) throws EncryptionException {
		return EncryptionUtil.encrypt_AES(value);
	}

	public String decryptField(String encryptedValue) throws EncryptionException {
		return EncryptionUtil.decrypt_AES(encryptedValue);
	}

}
