
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SocketHandler extends Thread {
    private BufferedReader in;
    private DataOutputStream out;
    private ArrayList<SocketHandler> clients;

    public SocketHandler(BufferedReader in, DataOutputStream out, ArrayList<SocketHandler> clients) {
        this.in = in;
        this.out = out;
        this.clients = clients;
        clients.add(this);
        start();
    }

    public void send(String message) {
        try {
            out.writeBytes(message + "\n");
            out.flush(); // Ensure data is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            for (int i = 1; i <= 1000; i++) {
                send(String.valueOf(i));
                Thread.sleep(1000);  // Gửi số mỗi giây
            }
            send("END");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clients.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}