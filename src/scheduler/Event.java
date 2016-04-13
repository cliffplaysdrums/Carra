/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ayomitunde
 */
public class Event implements Serializable{
    
    public static enum Priority
    {
        LOW ("low"),
        MEDIUM ("medium"),
        HIGH ("high");
        
        private final String name;
        private Priority(String s){
            name = s;
        }
        
        public boolean equalsName(String prio){
            return(prio == null) ? false : name.equals(prio);
        }
        
        @Override
        public String toString(){
            return this.name;
        }
    };
    
    private String _eventName;
    private String _eventDescription;
    private String _eventDate;
    private String _eventTime;
    private String _eventPriority;
    private User _eventCreator;
    private boolean _rescheduled;
    private ArrayList<String> _eventAttendees;
    
    public Event(String eventName, String eventDescr, String date, String time, User user){
        this._eventName = eventName;
        this._eventDescription = eventDescr;
        this._eventDate = date;
        this._eventTime = time;
        this._eventCreator = user;
        this._rescheduled = false;
        _eventAttendees = new ArrayList<>();
    }
    
    public void setDescription(String descr) {
        this._eventDescription = descr;
    }
    
    public void setDate(String date){
        this._eventDate = date;
    }
    
    public void setTime(String time){
        this._eventTime = time;
    }
    
    public void setCreator(User user){
        this._eventCreator = user;
    }
    
    public void setEventName(String name){
        this._eventName = name;
    }
    
    public void setPriorty(String p){
        this._eventPriority = p;
    }
    public void setRescheduled(){
        this._rescheduled = true;
    }
    
    public String getEventDescription() {
        return this._eventDescription;
    }
    
    public String getEventDate(){
        return this._eventDate;
    }
    
    public String getPriority(){
        return this._eventPriority;
    }
    public String getEventTime(){
        return this._eventTime;
    }
    
    public String getEventName(){
        return this._eventName;
    }
    
    public User getEventCreator(){
        return this._eventCreator;
    }
    
    public boolean isRescheduled(){
        return this._rescheduled;
    }
    
    public void addAttendee(String username){
        _eventAttendees.add(username);
    }
    
    public ArrayList getAttendees(){
        return this._eventAttendees;
    }
    
}
