package commun;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	
	public static String encrypt(String password) {
		try	{
			Key clef = new SecretKeySpec("KeyPendu".getBytes("ISO-8859-2"), "Pendu");
			Cipher cipher = Cipher.getInstance("Pendu");
			cipher.init(Cipher.ENCRYPT_MODE, clef);
			return new String(cipher.doFinal(password.getBytes()));
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static String decrypt(String password) {
		try {
			Key clef = new SecretKeySpec("KeyPendu".getBytes("ISO-8859-2"), "Pendu");
			Cipher cipher = Cipher.getInstance("Pendu");
			cipher.init(Cipher.DECRYPT_MODE, clef);
			return new String(cipher.doFinal(password.getBytes()));
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

	public static String getCurrentTimeUsingCalendar() {
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
}
