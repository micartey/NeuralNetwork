package me.micartey.neuralnetwork.train;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataSet {

    @Getter private final CopyOnWriteArrayList<DataRow> rows;

    public DataSet(DataRow... rows) {
        this.rows = new CopyOnWriteArrayList<>();
        this.addRow(rows);
    }

    public void addRow(DataRow... rows) {
        this.rows.addAll(Arrays.asList(rows));
    }

}
