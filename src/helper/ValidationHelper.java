package helper;

public class ValidationHelper {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isValidUsername(String username) {
        return username.length() >= 3;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    public static boolean isValidPhoneNumber(String phone_number) {
        return phone_number.startsWith("+62") && phone_number.length() == 14;
    }

    public static boolean isValidRole(String role) {
        return role.equals("seller") || role.equals("buyer");
    }
}
