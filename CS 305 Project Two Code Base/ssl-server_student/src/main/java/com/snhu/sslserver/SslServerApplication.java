package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";
@RestController
class ChecksumController {

    private SecretKey secretKey;

    public ChecksumController() throws Exception {
        // Generate a secret key for AES encryption
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // 128-bit AES
        this.secretKey = keyGen.generateKey();
    }

    @GetMapping("/hash")
    public String getChecksum() throws Exception {
        String data = "Cody Adams";
        
        // Encrypt the data using AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        // Encode the encrypted data in Base64 for display
        String encryptedString = Base64.getEncoder().encodeToString(encryptedData);

        return "Data: " + data + " Encrypted: " + encryptedString;
    }
}