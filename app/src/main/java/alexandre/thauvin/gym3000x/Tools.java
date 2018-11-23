package alexandre.thauvin.gym3000x;

import java.util.regex.Pattern;

public class Tools {

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String target){
        return Pattern.compile("[0-9]").matcher(target).find() && target.length() > 6;
    }
}