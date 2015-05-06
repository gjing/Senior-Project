package com.example.george.livegps;

/**
 * Created by George on 5/6/2015.
 */
public class LinkedList{

    private GPSNode head;
    private int size;

    public LinkedList()
    {
        head = null;
        size = 0;
    }

    public void add(double altitude, double longitude, double latitude, long t)
    {
        GPSNode node = new GPSNode(altitude, longitude, latitude, t);
        if (size == 0)
        {
            head = node;
        }
        else
        {
            node.setNext(head);
            head = node;
        }
        size++;
    }

    public double getRel(GPSNode a, GPSNode b)
    {
        long t = a.getTime() - b.getTime();
        double lat = a.getLatitude() - b.getLatitude();
        double lon = a.getLongitude() - b.getLongitude();
        double alt = a.getAltitude() - b.getAltitude();
        return lat + lon + alt;
    }

    public double calculate()
    {
        double saved = 0;
        while (size >0)
        {
            saved += getRel(head, head.getNext());
            head = head.getNext();
            size--;
        }
        return saved;
    }

    private class GPSNode {

        private double alt;
        private double lon;
        private double lat;
        private long time;
        private GPSNode next;

        public GPSNode(double altitude, double longitude, double latitude, long t) {
            alt = altitude;
            lon = longitude;
            lat = latitude;
            time = t;
            next = null;
        }

        public double getAltitude() {
            return alt;
        }

        public void setAll(double altitude, double longitude, double latitude, long t) {
            alt = altitude;
            lon = longitude;
            lat = latitude;
            time = t;
        }

        public double getLongitude() {
            return lon;
        }

        public double getLatitude() {
            return lat;
        }

        public long getTime() {
            return time;
        }

        public GPSNode getNext() {
            return next;
        }

        public void setNext(GPSNode n) {
            next = n;
        }
    }
}
