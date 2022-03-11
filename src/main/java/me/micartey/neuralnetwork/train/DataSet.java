package me.micartey.neuralnetwork.train;

import lombok.Getter;
import lombok.SneakyThrows;
import me.micartey.network.serializer.Convertable;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataSet implements Convertable<DataSet> {

    @Getter private final CopyOnWriteArrayList<DataRow> rows;

    public DataSet(DataRow... rows) {
        this.rows = new CopyOnWriteArrayList<>();
        this.addRow(rows);
    }

    public void addRow(DataRow... rows) {
        this.rows.addAll(Arrays.asList(rows));
    }

    @SneakyThrows
    public void save(String name) {
        File file = new File(name);
        file.createNewFile();

        toFile(name);
    }

    @SneakyThrows
    public DataSet load(String name) {
        return fromFile(name).orElse(null);
    }
}
