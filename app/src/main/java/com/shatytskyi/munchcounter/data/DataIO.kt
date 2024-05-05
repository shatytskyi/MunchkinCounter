package com.shatytskyi.munchcounter.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataIO implements Repo.OnDataChangedListener {

    {
        Repo.ins().subscribe(this);
    }

    private Context context;
    final String FILE_DATA = "data.txt";

    public DataIO(Context context) {
        this.context = context;
    }

    public List<Unit> read () {
        List<Unit> restoredList;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(FILE_DATA))) {
            restoredList = (List<Unit>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            restoredList = new ArrayList<>();
        }

        return restoredList;
    }

    @Override
    public void onDataChanged() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(FILE_DATA, Context.MODE_PRIVATE))) {
            objectOutputStream.writeObject(Repo.ins().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
