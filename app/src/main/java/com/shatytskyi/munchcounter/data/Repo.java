package com.shatytskyi.munchcounter.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repo {

    private static Repo mInstance;
    private List<Unit> mData;

    // Used for SnackBar's undo action
    private List<Unit> mTempData;
    private Unit mTempUnit;


    private Repo() {
    }

    public static Repo ins() {
        if (mInstance == null) mInstance = new Repo();
        return mInstance;
    }


    // Used for SnackBar's undo action
    public void createTempData() {
        mTempData = new ArrayList<>();
        mTempData.addAll(mData);
    }

    public void restoreData() {
        mData = new ArrayList<>();
        mData.addAll(mTempData);
        notifySubscribers();
        mTempData = null;
    }

    public void editUnit(long id, String name, int lvl, int power) {
        Unit unit = findUnitById(id);
        unit.name = name;
        unit.lvl = lvl;
        unit.power = power;
        notifySubscribers();
    }

    //Used to set restored mData from file
    public void setData(List<Unit> data) {
        mData = data;
    }

    public List<Unit> getData() {
        return mData;
    }

    public void shuffleData() {
        Collections.shuffle(mData);
        notifySubscribers();
    }

    public void addUnit(String name) throws ZeroLengthException {
        if (name.trim().length() > 0) {
            mData.add(new Unit(name.trim(), 1, 0));
        } else throw new ZeroLengthException();
        notifySubscribers();
    }

    public void addUnit(Unit unit) {
        mData.add(unit);
        notifySubscribers();
    }

    public void removeUnit(long id) {
        mData.remove(findUnitById(id));
        notifySubscribers();
    }

    public void removeUnit(Unit unit) {
        mData.remove(unit);
        notifySubscribers();
    }

    public void resetUnit(long id) {
        Unit unit = findUnitById(id);
        unit.lvl = 1;
        unit.power = 0;
        notifySubscribers();
    }

    public void removeAll() {
        mData = new ArrayList<>();
        notifySubscribers();
    }

    public void resetAll() {
        for (Unit unit : mData) {
            unit.lvl = 1;
            unit.power = 0;
        }
        notifySubscribers();
    }

    public void changePower(long id, int value) {
        if ((findUnitById(id).getScore() + value) < 100) {
            mData.get(mData.indexOf(findUnitById(id))).power += value;
            notifySubscribers();
        }
    }
    public void changeLvl(long id, int value) {
        if (findUnitById(id).lvl + value <= 10 && findUnitById(id).lvl + value >= 1) {
            if ((findUnitById(id).getScore() + value) < 100)
                mData.get(mData.indexOf(findUnitById(id))).lvl += value;
            else {
                mData.get(mData.indexOf(findUnitById(id))).lvl += value;
                mData.get(mData.indexOf(findUnitById(id))).power = 99 - mData.get(mData.indexOf(findUnitById(id))).lvl;
            }
        }
        notifySubscribers();
    }

    public Unit findUnitById(long id) {
        for (Unit unit : mData) {
            if (unit.id == id) return unit;
        }
        return null;
    }

    //Set of subscribed OnDataChangedListeners
    private final Set<OnDataChangedListener> mSubscribers = new HashSet<>();


    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private void notifySubscribers() {
        for (OnDataChangedListener subscriber : mSubscribers) {
            subscriber.onDataChanged();
        }
    }

    public void subscribe(OnDataChangedListener subscriber) {
        mSubscribers.add(subscriber);
    }

    public void unsubscribe(OnDataChangedListener subscriber) {
        mSubscribers.remove(subscriber);
    }

    //Thrown when new player's name field is empty
    public static class ZeroLengthException extends Exception {

    }

}
