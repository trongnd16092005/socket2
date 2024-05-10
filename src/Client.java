import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        new Client();
    }

    public Client() throws Exception {
        Socket socket = new Socket("localhost", 8088);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    if (message.equals("END")) {
                        break;
                    }
                }
                System.out.println("Server disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            // Không cần gửi gì từ client, chỉ cần nhận dữ liệu từ server
        }
    }
}