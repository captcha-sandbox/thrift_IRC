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

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ThriftServer {
    
    public static RequestHandler handler;
    
    public static MultiplicationService.Processor processor;
    
    public static void main(String[] args) {
        try {
            handler = new RequestHandler();
            processor = new MultiplicationService.Processor(handler);
            
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
    
    public static void simple(MultiplicationService.Processor processor) {
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
