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
import org.apache.thrift.TException;
import static thrift.ThriftServer.channelList;
import static thrift.ThriftServer.userList;
import static thrift.ThriftServer.inbox;

public class RequestHandler implements IRCService.Iface{
    
    @Override
    public String nickname(String nickname) {
        ChatUser new_member = new ChatUser();
        new_member.setUsername(nickname);
        userList.add(new_member);
        System.out.println(nickname + " is signing in to server");
        return nickname;
    }
    
    @Override
    public String join(String channelname, String nickname) {
        
        if(!checkChannel(channelname)) {
            createChannel(channelname);
        }
        
        int index = searchMember(nickname);
        userList.get(index).addMembership(channelname);
        
//        for(int i=0; i<userList.get(index).getMembership().size(); i++) {
//            System.out.println(userList.get(index).getMembership().get(i));
//        }
        return channelname;
    }
    
    @Override
    public String leave(String channelname, String nickname) {
        int index = searchMember(nickname);
        userList.get(index).leaveGroup(channelname);
        System.out.println(nickname + " left channel "+channelname);
        
//        for(int i=0; i<userList.get(index).getMembership().size(); i++) {
//            System.out.println(userList.get(index).getMembership().get(i));
//        }
        return channelname;
    }
    
    @Override
    public void exit(String nickname) {
        int index = searchMember(nickname);
        
        for(int i=0; i<userList.get(index).getMembership().size(); i++) {
            System.out.println(userList.get(index).getMembership().get(i));
        }
        userList.remove(index);
        System.out.println(nickname+" exit from application");
    }
    
    @Override
    public String receiveMessage(String text, String nickname, String channel) {
        return nickname;
    }
    
    @Override
    public void sendMessage(String text, String nickname, String channel) {
        ChatMessage msg = new ChatMessage();
        msg.setMessage(text);
        msg.addMembership(channel);
        inbox.add(msg);
        System.out.println(nickname+" is sending message");
        System.out.println(text);
    }
    
    @Override
    public boolean checkChannel(String channelname) {
        boolean found = false;
        int i = 0;
        
        while(i<channelList.size() && !found) {
            String temp = channelList.get(i);
            if(temp.equals(channelname)) {
                found = true;
                System.out.println("Channel "+channelname+" is exist");
            }
            i++;
        }
        return found;
    }
    
    @Override
    public void createChannel(String channelname) {
        
        channelList.add(channelname);
        System.out.println("Channel " + channelname + " is created");
    }
    
    public int searchMember(String username) {
        
        int i = 0;
        boolean found = false;
        int index = 0;
     
        while(i<userList.size() && !found) {
            String temp = userList.get(i).getUsername();
            if(temp!=null) {
                if(temp.equals(username)) {
                    index = i;
                    found = true;
                }
            }
        i++;    
        }
        return index;
    }
}
