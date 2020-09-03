package me.clientastisch.network.neural.train;

import lombok.Getter;
import lombok.SneakyThrows;
import me.clientastisch.network.serializer.Convertable;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("ALL")
public class DataSet implements Convertable<DataSet> {

    @Getter private CopyOnWriteArrayList<DataRow> rows = new CopyOnWriteArrayList<>();

    public DataSet(DataRow... rows) {
        addRow(rows);
    }

    public void addRow(DataRow... rows) {
        Arrays.stream(rows).forEach(this.rows::add);
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
