package com.test;

import java.io.UnsupportedEncodingException;

import com.asapp.service.EncryptionService;

public class EncryptionServiceTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		EncryptionService service = new EncryptionService();
		
		String value = "PassCode1";
		
		String encryptValue = service.encrypt(value);
		
		System.out.println("Encrypted Value : " + encryptValue);
		
		String decryptValue = service.decrypt(encryptValue);
		
		System.out.println("Decrypted Value : " + decryptValue);

	}

}
