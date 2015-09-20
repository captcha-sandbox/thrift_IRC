/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thrift;

/**
 *
 * @author Lenovo
 */

import IRCService.IRCService;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ThriftServer {
    
    public static RequestHandler handler;
    public static IRCService.Processor processor;
    public static List<String> channelList = new ArrayList<>();
    public static List<ChatUser> userList = new ArrayList<>();
    public static List<ChatMessage> inbox = new ArrayList<>();
    
    public static void main(String[] args) {
        try {
            ChatUser dummy = new ChatUser();
            ChatMessage test = new ChatMessage();
            
            handler = new RequestHandler();
            processor = new IRCService.Processor(handler);
            channelList.add("dummy");
            userList.add(dummy);
            inbox.add(test);
            
            Runnable simple;
            simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };
            
            new Thread(simple).start();
            System.out.println("Thread created");
        }catch(Exception x) {
            x.printStackTrace();
        }
    }
    
    public static void simple(IRCService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            
            System.out.println("Starting the simple server ...");
            server.serve();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
