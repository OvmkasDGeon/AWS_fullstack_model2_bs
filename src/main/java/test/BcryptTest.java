package test;

import org.mindrot.bcrypt.BCrypt;

public class BcryptTest {
	public static void main(String[] args) {
		String pw = "0000";
		String result = BCrypt.hashpw(pw, BCrypt.gensalt(10));
		String result2 = BCrypt.hashpw(pw, BCrypt.gensalt(8));
		String result3 = BCrypt.hashpw(pw, BCrypt.gensalt());//gensalt : 
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
		
		System.out.println(BCrypt.checkpw(pw, result));
		System.out.println(BCrypt.checkpw(pw, result2));
		System.out.println(BCrypt.checkpw("0000", result3));
		
		String resultCopy = "$2a$10$D6.zeG/V61O2lhEDLvqOeus0B9jba5wX0/mPbwi.W9lw/XWw1G/7y";
		System.out.println(BCrypt.checkpw("0000", resultCopy));
	}
}
