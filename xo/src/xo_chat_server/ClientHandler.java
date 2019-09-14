package xo_chat_server;

import user.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


public class ClientHandler implements Runnable {
    private String name;
    final DataInputStream dis;

    Socket s;
    boolean isLoggedin;

    public ClientHandler(Socket s, String name, DataInputStream dis) {
        this.dis = dis;

        this.name = name;
        this.s = s;
        this.isLoggedin = true;
    }

    @Override
    public void run() {

        String received;
        while (true) {
            try {
                received = dis.readUTF();
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgtoSend = st.nextToken();
                String recipient = st.nextToken();
                String sender = st.nextToken();

                if (MsgtoSend.equals("logout")) {
                    this.isLoggedin = false;
                    this.s.close();
                    break;
                }
                Message.createMessage(Integer.parseInt(sender), Integer.parseInt(recipient), MsgtoSend);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            this.dis.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}

