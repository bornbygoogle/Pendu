package commun;

import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Utils 
{
	private static final String SECRET_KEY = "Key1PenduKey1Pendu1819G3";
	private static String salt = "ssshhhhhhhhhhh!!!!";

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String getCurrentTimeUsingCalendar() {
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}

	public static String getSecretKey() {
		return SECRET_KEY;
	}
	
	private static Pattern pswNamePtrn = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
	
	public static boolean validatePassword(String password){
        
        Matcher mtch = pswNamePtrn.matcher(password);
        if(mtch.matches()){
            return true;
        }
        return false;
    }
}
