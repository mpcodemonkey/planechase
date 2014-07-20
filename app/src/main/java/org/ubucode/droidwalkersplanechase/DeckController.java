package org.ubucode.droidwalkersplanechase;

/**
 * Created by Jon on 7/20/2014.
 */

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DeckController {
    private LinkedList<Card> planeQueue;
    private Card currentPlane;
    private Context context;

    public DeckController(Context c) {
        planeQueue = new LinkedList<Card>();
        context = c;
        onStart();
    }

    //grabs planeCards from db, initializes entries in planequeue
    private void onStart() {
        DataBaseHelper helper = null;
        try {
            helper = new DataBaseHelper(context);
            helper.opendatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<Card> res = helper.getAllPlanes();
        Iterator<Card> why = res.iterator();
        while (why.hasNext()) {
            Card p = why.next();
            planeQueue.add(p);
        }
        currentPlane = planeQueue.peek();
        helper.close();
    }

    public boolean nextPlane() {
        Card tmp = currentPlane;
        planeQueue.removeFirst();
        currentPlane = planeQueue.getFirst();
        planeQueue.addLast(tmp);

        return checkForEvent();
    }

    private boolean checkForEvent() {
        return false;
    }


    public void prevPlane() {
        Card tmp = planeQueue.removeLast();
        currentPlane = tmp;
        planeQueue.addFirst(tmp);
    }


    public Card getCurrentPlane() {
        // TODO Auto-generated method stub
        return this.currentPlane;
    }

    public void setCurrentPlane(Card p) {
        // TODO Auto-generated method stub
        this.currentPlane = p;
    }

    public LinkedList<Card> getPlaneQueue() {
        // TODO Auto-generated method stub
        return this.planeQueue;
    }
}

