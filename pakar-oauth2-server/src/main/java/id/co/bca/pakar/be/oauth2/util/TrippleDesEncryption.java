package id.co.bca.pakar.be.oauth2.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class TrippleDesEncryption {
    private SecretKey desKey;

    public TrippleDesEncryption(byte[] key) throws Exception {
        createCipher(key);
    }

    public void createCipher(byte[] desKeyData) throws Exception {
        if (!(desKeyData.length == 16 || desKeyData.length == 24)) {
          throw new Exception("Length not valid :" + desKeyData.length);
        }
        byte[] key = new byte[24];
        if (desKeyData.length == 16) {
            for (int za = 0; za < 16; za ++) {
                key[za] = desKeyData[za];
            }
            for (int za = 0; za < 8; za++) {
                key[za + 16] = desKeyData[za];
            }
        }
        if (desKeyData.length == 24) {
            for (int za = 0; za < 24; za ++) {
                key[za] = desKeyData[za];
            }
        }
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = null;
        keyFactory = SecretKeyFactory.getInstance("DESede");
        desKey = keyFactory.generateSecret(desKeySpec);
    }

    public byte[] encryptECB ( byte[] cleartext ) {
        byte[] ciphertext = null;
        try {
            Cipher desCipher;
            desCipher = Cipher.getInstance( "DESede/ECB/NoPadding" );
            desCipher.init( Cipher.ENCRYPT_MODE , desKey );
            ciphertext = desCipher.doFinal( cleartext );
        }
        catch ( NoSuchAlgorithmException ex1 ) {}
        catch ( InvalidKeyException ex2 ) {}
        catch ( NoSuchPaddingException ex3 ) {}
        catch ( BadPaddingException ex4 ) {}
        catch ( IllegalBlockSizeException ex5 ) {}
        catch ( IllegalStateException ex6 ) {}
        return ciphertext;
    }

    public byte[] decryptECB ( byte[] ciphertext ) {
        byte[] cleartext = null;
        try {
            Cipher desCipher;
            desCipher = Cipher.getInstance("DESede/ECB/NoPadding");
            desCipher.init(Cipher.DECRYPT_MODE, desKey);
            cleartext = desCipher.doFinal(ciphertext);
        }
        catch (NoSuchAlgorithmException ex1) {}
        catch (InvalidKeyException ex2) {}
        catch (NoSuchPaddingException ex3) {}
        catch (BadPaddingException ex4) {}
        catch (IllegalBlockSizeException ex5) {}
        catch (IllegalStateException ex6) {}
        return cleartext;
    }
  
    public byte[] encryptCBC(byte[] cleartext) throws Exception {
        byte[] ciphertext = null;
        byte[] iv = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
  
        Cipher desCipher;
        desCipher = Cipher.getInstance("DESede/CBC/NoPadding");
        if (desKey == null) {
            throw new Exception("desKey = NULL");
        }
        desCipher.init(Cipher.ENCRYPT_MODE, desKey, paramSpec);
        ciphertext = desCipher.doFinal(cleartext);
        iv = desCipher.getIV();
        return ciphertext;
    }
  
    public byte[] decryptCBC(byte[] ciphertext) throws Exception {
        byte[] cleartext = null;
        byte[] iv = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        
        IvParameterSpec paramSpec = new IvParameterSpec(iv);
  
        Cipher desCipher;
        desCipher = Cipher.getInstance("DESede/CBC/NoPadding");
        if (desKey == null) {
            throw new Exception("desKey = NULL");
        }
        desCipher.init(Cipher.DECRYPT_MODE, desKey, paramSpec);
        cleartext = desCipher.doFinal(ciphertext);
        iv = paramSpec.getIV();
        return cleartext;
    }
  
  
    public String cleanData(String orig) {
        StringBuffer buff = new StringBuffer();
        char[] chars = orig.toCharArray();
        for (int za = 0; za < chars.length; za++) {
            char tmp = chars[za];
            if (!(tmp == 0x00)) {
                buff.append(tmp);
            }
        }
        return buff.toString();
    }
  
    public String paddData(String orig) {
        StringBuffer buff = new StringBuffer();
        buff.append(orig);
        int paddSize = 0;
        if (orig.length() % 8 != 0)
            paddSize = 8 - (orig.length() % 8);
        for (int za = 0; za < paddSize; za++) {
            buff.append((char) 0x00);
        }
        return buff.toString();
    }
  
    public String encrypt(String data) {
        String retval = "";
        byte[] encrypted = encryptECB(paddData(data).getBytes());
        retval = Hex.encodeHexString(encrypted);
        return retval;
    }
  
    public String decrypt(String data) {
        String retval = "";
        byte[] decrypted = null;
		try {
			decrypted = decryptECB(Hex.decodeHex(data));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        retval = cleanData(new String(decrypted));
        return retval;
    }
    
    public static void main(String[] args) {
    	try {
    		TrippleDesEncryption trippleDes = new TrippleDesEncryption("123456789013245678901234".getBytes());
    		String encryptext = trippleDes.encrypt("Bca123!");
    		System.out.println(trippleDes.decrypt(encryptext));
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 }