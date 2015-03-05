package myClient;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.parse.Parse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Mak on 03.03.2015.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class ServerListener extends AsyncTask<String, Void, String> {
    static private String serverIp = "192.168.0.13";
    static private Socket serverListener;

    static public void initServerListener(String server) {
        serverIp = server;

    }

    static public boolean connectServer() {
        try {
            serverListener = new Socket(InetAddress.getByName("192.168.0.13"), 11);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    static public void setTestCommand() {
        try {
            serverListener.getOutputStream().write("TEST\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public boolean authenticationCommand(String login, String password) {
        String answer = null;
        String command = "authentication login:{" + login + "} password:{" + password + "}\n";
        try {
            serverListener.getOutputStream().write(command.getBytes());
            Sender sender = new Sender(serverListener.getInputStream());
            sender.execute();
            while (!sender.flag){
            }
            answer = sender.answer;
            if (answer.equals("authentication success")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected String doInBackground(String... params) {
        connectServer();
        return null;
    }
}
