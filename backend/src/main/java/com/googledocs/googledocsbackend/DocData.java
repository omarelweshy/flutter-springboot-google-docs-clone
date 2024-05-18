package com.googledocs.googledocsbackend;

import java.util.*;


public class DocData {
    private String room;
    private List delta;

    public DocData() {}

    public DocData(String room, List delta) {
        this.room = room;
        this.delta = delta;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List getDelta() {
        return delta;
    }

    public void setDelta(List delta) {
        this.delta = delta;
    }
}
