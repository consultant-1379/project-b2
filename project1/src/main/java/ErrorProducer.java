import java.io.IOException;
import java.net.Socket;

public class ErrorProducer {
    public static void main(String[] args) throws IOException {
        final String NETWORK_SECURITY_NWIEB_SECURE_PASSWORD_ATTR = "nwiebSecureUserPassword";
        String UPPERCASE = "bad password";

        String ip = "192.168.12.42"; // Noncompliant
        Socket socket = new Socket(ip, 6667);

        final String OTHER_PWD = "also hard coded";
    }

    private String HelloController() throws IOException {
        try {
            return "Hello ";
        } catch (Exception t) {
            t.printStackTrace();
        }
        return "something else";
    }
}
