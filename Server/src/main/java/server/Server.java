package server;

import commands.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final int PORT = 8190;
    private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
        authService = new SimpleAuthServise();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                System.out.println("Client connected");
                System.out.println("client: " + socket.getRemoteSocketAddress());
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg){
        String message = String.format("[ %s ]: %s", sender.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg){
        String message = String.format("[ %s ] to [ %s ]: %s", sender.getNickname(), receiver, msg);
        for (ClientHandler c : clients) {
            if(c.getNickname().equals(receiver)){
                c.sendMsg(message);
                if (!c.equals(sender)){
                    sender.sendMsg(message);
                }
                return;
            }
        }
        sender.sendMsg("user not found" + receiver);
    }

    public void broadcastClientlist(){
        StringBuilder sb = new StringBuilder(Command.CLIENT_LIST);
        for (ClientHandler c : clients) {
           sb.append(" ").append(c.getNickname());
        }
        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }

    }



//    public synchronized void privatMsg(ClientHandler sender, String userNick, String msg) {
//        String message = String.format("[%s] to [ %s ]: %s", sender.getNickname(), userNick, msg);
//        for (ClientHandler c : clients) {
//            if(c.getNickname().equals(userNick)){
//                c.sendMsg(msg);
//                if (!c.equals(sender)){
//                    sender.sendMsg(message);
//                }
//                return;
//            }
//        }
//        sender.sendMsg("user");
//    }



    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
        broadcastClientlist();
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadcastClientlist();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public  boolean isLoginAuthenticated (String login){
        for (ClientHandler c: clients) {
            if (c.getLogin().equals(login)){
                return true;
            }

        }
        return false;
    }
}
