/*
 *** INSTRUCTIONS ***
 * 
 * ENCRYPT
 *   Save the entered password as a string: String password;
 *   Instantiate the class: Encryption encryption = new Encryption();
 *   Generate a salt value: byte[] salt = encryption.generateSalt();
 *   Now encrypt: byte[] encryptedPassword = getEncryptedPassword(password, salt);
 *
 * AUTHENTICATE
 *   Read the encrypted password and salt value from the user object
 *   Assume attemptedPassword is a String, encryptedPassword is byte[], and salt
 *     is byte[]
 *   valid if this returns true: encryption.authenticate(attemptedPassword,
 *     encryptedPassword, salt)
 *
 * DECRYPT
 *   Don't ever decrypt
 */
package scheduler;

/**
 *
 * @author Cliff
 */
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Cliff
 */
public class Encryption {
    
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        
        return Arrays.equals(encryptedAttemptedPassword, encryptedPassword);
    }
    
    public byte[] getEncryptedPassword(String password, byte[] salt) 
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        String algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2 with SHA-1
        int keyLength = 160; // SHA-1 generates 160 bit hashes
        int iterations = 20000; // the NIST recommends > 1,000
        
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        
        return f.generateSecret(spec).getEncoded();
    }
    
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        
        return salt;
    }
}
