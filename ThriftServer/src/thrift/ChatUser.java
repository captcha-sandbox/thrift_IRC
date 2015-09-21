/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thrift;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ChatUser {
    
    private String username;
    private List<String> membership = new ArrayList<>();
    private List<ChatMessage> inbox = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getMembership() {
        return membership;
    }

    public void setMembership(List<String> membership) {
        this.membership = membership;
    }

    public List<ChatMessage> getInbox() {
        return inbox;
    }

    public void setInbox(List<ChatMessage> inbox) {
        this.inbox = inbox;
    }
    
    public void addMembership(String channel) {
        membership.add(channel);
        System.out.println("You're now member of " + channel);
    }
    
    public void leaveGroup(String channel) {
        int index = membership.indexOf(channel);
        membership.remove(index);
    }
    
    public void addMessage(ChatMessage message) {
        inbox.add(message);
    }
    
    public void deleteMessage() {
        ChatMessage chat = inbox.get(0);
        if(chat.isStatus()) {
            inbox.remove(0);
        }
    }
}
