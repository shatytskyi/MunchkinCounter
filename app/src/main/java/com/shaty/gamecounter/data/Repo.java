package com.shaty.gamecounter.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repo {

    private static Repo mInstance;
    private List<Unit> mData;


    private Repo () {
    }

    public static Repo instance() {
        if (mInstance == null) mInstance = new Repo();
        return mInstance;
    }
    public List<Unit> getData() {
        return mData;
    }
    public void setData(List<Unit> data) {
        mData = data;
    }

    public void addUnit(String name) throws ZeroLengthException {
        if (name.trim().length() > 0) {
            mData.add(new Unit(name.trim(), 1, 0));
        }
        else throw new ZeroLengthException();
        notifySubscribers();
    }

    public void removeUnit(long id) {
        mData.remove(findUnitById(id));
        notifySubscribers();
    }

    public void removeAll() {
        mData = new ArrayList<>();
        notifySubscribers();
    }

    public void resetUnit(long id) {
        Unit unit = findUnitById(id);
        unit.lvl = 1;
        unit.power = 0;
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
        mData.get(mData.indexOf(findUnitById(id))).power += value;
        notifySubscribers();
    }

    public void changeLvl(long id, int value) {
        mData.get(mData.indexOf(findUnitById(id))).lvl += value;
        notifySubscribers();
    }

    public Unit findUnitById (long id) {
        for (Unit unit : mData) {
            if (unit.id == id) return unit;
        }
        return null;
    }

    private final Set<OnDataChangedListener> mSubscribers = new HashSet<>();

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private void notifySubscribers() {
        for (OnDataChangedListener subscriber : mSubscribers) {
            subscriber.onDataChanged();
        }
    }

    public void subscribe (OnDataChangedListener subscriber) {
        mSubscribers.add(subscriber);
    }

    public void unsubscribe (OnDataChangedListener subscriber) {
        mSubscribers.remove(subscriber);
    }

    public static class ZeroLengthException extends Exception {
    }

}
