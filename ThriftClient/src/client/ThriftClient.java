/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

/**
 *
 * @author Lenovo
 */
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import IRCService.IRCService;

public class ThriftClient {
    
    public static String username;
    public static boolean isOnline;
    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            
            TProtocol protocol = new TBinaryProtocol(transport);
            IRCService.Client client = new IRCService.Client(protocol);
            isOnline = true;
            perform(client);
            transport.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void perform(IRCService.Client client) throws TException{
        
        Scanner in = new Scanner(System.in);
        while(isOnline) {
            
            String test = in.nextLine();
            processRequest(client, test);
        }
    }
    
    //process user commands
    public static void processRequest(IRCService.Client client, String req) throws TException {
        String[] input = req.split(" ");
        
        if(input[0].equals("/nick")) {
            getNickname(client, input[1]);
        }
        else if(input[0].equals("/join")) {
            joinChannel(client, input[1]);
        }
        else if(input[0].equals("/leave")) {
            leaveChannel(client, input[1]);
        }
        else if(input[0].equals("/exit")) {
            exitApp(client);
            isOnline = false;
        }
        else if(input[0].contains("@")) {
            String channel = input[0].substring(1);
            String chat = "";
            for(int i=1; i<input.length; i++) {
                chat+=input[i]+" ";
            }
            sendChat(client, chat, channel);
        }
    }
    
    //request for nickname
    public static void getNickname(IRCService.Client client, String name) throws TException {
        
        username = client.nickname(name);
        System.out.println("Your nickname is : "+username);
    }
    
    public static void joinChannel(IRCService.Client client, String channel) throws TException {
        
        String group = client.join(channel, username);
        System.out.println("You are member of channel " + group);
    }
    
    public static void leaveChannel(IRCService.Client client, String channel) throws TException {
        
        String group = client.leave(channel, username);
        System.out.println("You left from "+ group + " channel");
    }
    
    public static void exitApp (IRCService.Client client) throws TException {
        
        client.exit(username);
        System.out.println("Aplication closed successfully");
    }
    
    public static void sendChat(IRCService.Client client, String text, String channel) throws TException {
        
        client.sendMessage(text, username, channel);
        System.out.println("Mesage sent");
    }
}
