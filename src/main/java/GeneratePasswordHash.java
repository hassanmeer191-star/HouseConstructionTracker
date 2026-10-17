public class GeneratePasswordHash {
public static void main(String[] args) {
System.out.println("Homeowner password hash:");
System.out.println(PasswordUtil.hashPassword("Home12345"));

System.out.println();

System.out.println("Admin password hash:");
System.out.println(PasswordUtil.hashPassword("Admin12345"));
}
}