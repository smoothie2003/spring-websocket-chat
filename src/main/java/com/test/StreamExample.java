package com.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;

public class StreamExample {
  
      public static void main(String []args) throws IOException {
          final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
          final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
          Properties properties = new Properties();
          final String transform = "AES/CBC/PKCS5Padding";
  
          String input = "hello world!";
          //Encryption with CryptoOutputStream.
  
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  
          try (CryptoOutputStream cos = new CryptoOutputStream(transform, properties, outputStream, key, iv)) {
              cos.write(getUTF8Bytes(input));
              cos.flush();
          }
  
          System.out.println(outputStream.toString());
          
          // The encrypted data:
          System.out.println("Encrypted: "+Arrays.toString(outputStream.toByteArray()));
  
          // Decryption with CryptoInputStream.
          InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
  
          try (CryptoInputStream cis = new CryptoInputStream(transform, properties, inputStream, key, iv)) {
              byte[] decryptedData = new byte[1024];
              int decryptedLen = 0;
              int i;
              while ((i = cis.read(decryptedData, decryptedLen, decryptedData.length - decryptedLen)) > -1) {
                  decryptedLen += i;
              }
              System.out.println("Decrypted: "+new String(decryptedData, 0, decryptedLen, StandardCharsets.UTF_8));
          }
      }
  

      private static byte[] getUTF8Bytes(String input) {
         return input.getBytes(StandardCharsets.UTF_8);
      }
  
  }
