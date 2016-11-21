package com.asapp.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;

public class EncryptionService {
	
	public String encrypt(String value) throws UnsupportedEncodingException {
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
        Properties properties = new Properties();
        final String transform = "AES/CBC/PKCS5Padding";

        String input = value;
        //Encryption with CryptoOutputStream.

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (CryptoOutputStream cos = new CryptoOutputStream(transform, properties, outputStream, key, iv)) {
            cos.write(getUTF8Bytes(input));
            cos.flush();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String asB64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        
        return asB64;    
	}
	
	public String decrypt(String value) throws UnsupportedEncodingException {
		
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
        Properties properties = new Properties();
        final String transform = "AES/CBC/PKCS5Padding";
        String output = null;
        
		InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(value));

        try (CryptoInputStream cis = new CryptoInputStream(transform, properties, inputStream, key, iv)) {
            byte[] decryptedData = new byte[1024];
            int decryptedLen = 0;
            int i;
            while ((i = cis.read(decryptedData, decryptedLen, decryptedData.length - decryptedLen)) > -1) {
                decryptedLen += i;
            }
            output = new String(decryptedData, 0, decryptedLen, StandardCharsets.UTF_8);
            System.out.println("Decrypted: "+ output);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return output;
        
	}
	
	private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
     }

}
