package id.co.bca.pakar.be.oauth2.eai;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class DESedeEncryption {
	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede/ECB/NoPadding";
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	byte[] keyAsBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public DESedeEncryption() throws Exception {
		myEncryptionKey = "123456789013245678901234";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		myKeySpec = new DESedeKeySpec(keyAsBytes);
		mySecretKeyFactory = SecretKeyFactory.getInstance("DESede");
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = mySecretKeyFactory.generateSecret(myKeySpec);
	}
	
	public DESedeEncryption(String encryptionKey) throws Exception {
		myEncryptionKey = encryptionKey;
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		myKeySpec = new DESedeKeySpec(keyAsBytes);
		mySecretKeyFactory = SecretKeyFactory.getInstance("DESede");
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = mySecretKeyFactory.generateSecret(myKeySpec);
	}

	/**
	 * Method To Encrypt The String
	 */
	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String encryptToHex(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = Hex.encodeHexString(encryptedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	/**
	 * Method To Decrypt An Encrypted String
	 */
	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = bytes2String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
	
	/**
	 * Method To Decrypt An Hex Encrypted String
	 */
	public String decryptFromHex(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decoded = Hex.decodeHex(encryptedString);
			byte[] plainText = cipher.doFinal(decoded);
			decryptedText = bytes2String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	/**
	 * Testing The DESede Encryption And Decryption Technique
	 */
	public static void main(String args[]) throws Exception {
//		DESedeEncryption myEncryptor = new DESedeEncryption("123456789012345678901234");
		DESedeEncryption myEncryptor = new DESedeEncryption("123456789013245678901234");

		String stringToEncrypt = "Bca123!";
		
		String encrypted = myEncryptor.encrypt(stringToEncrypt);
		String encryptedHex = myEncryptor.encryptToHex(stringToEncrypt);
		String decrypted = myEncryptor.decrypt(encrypted);
		String decryptedHex = myEncryptor.decryptFromHex(encryptedHex);

		System.out.println("String To Encrypt: " + stringToEncrypt);
		System.out.println("Encrypted Value :" + encrypted.toLowerCase());
		System.out.println("Encrypted Value in hexa :" + encryptedHex.toLowerCase());
		System.out.println("Decrypted Value in hexa:" + decryptedHex);

	}
}
