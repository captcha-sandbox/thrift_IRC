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
public class ChatMessage {
    
    private String message;
    private List<String> membership = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMembership() {
        return membership;
    }

    public void setMembership(List<String> membership) {
        this.membership = membership;
    }
    
    public void addMembership(String channel) {
        membership.add(channel);
    }
    
    public void leaveGroup(String channel) {
        int index = membership.indexOf(channel);
        membership.remove(index);
    }
}
