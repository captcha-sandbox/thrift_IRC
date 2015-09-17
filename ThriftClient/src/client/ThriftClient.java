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
import thrift.MultiplicationService;

public class ThriftClient {
    
    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            
            TProtocol protocol = new TBinaryProtocol(transport);
            MultiplicationService.Client client = new MultiplicationService.Client(protocol);
            perform(client);
            transport.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void perform(MultiplicationService.Client client) throws TException{
        
        Scanner in = new Scanner(System.in);
        while(true) {
            int a = in.nextInt();
            int b = in.nextInt();
            int product = client.multiply(a,b);
            System.out.println(a +"*"+ b +"="+ product);
        }
    }
}
