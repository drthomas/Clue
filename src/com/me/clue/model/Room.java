package com.me.clue.model;

import java.util.ArrayList;

public class Room
{
    private String _roomName;
    private ArrayList<GridComponent> _room = new ArrayList<GridComponent>() { };

    public String getRoomName() { return _roomName; }
    public void setRoomName(String name) { _roomName = name; }

    public ArrayList<GridComponent> getRoom() { return _room; }
    public void setRoom(ArrayList<GridComponent> room) { _room.addAll(room); }
}
