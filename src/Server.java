
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("Server started");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                ClientHandler clientHandler = new ClientHandler(socket, in, out, clients);
                clientHandler.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket socket, BufferedReader in, DataOutputStream out, ArrayList<ClientHandler> clients) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.clients = clients;
        clients.add(this);
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 1000; i++) {
                out.writeBytes(i + "\n");
                out.flush();
                Thread.sleep(1000);  // Gửi số mỗi giây
            }
            out.writeBytes("END\n");
            out.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                clients.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}