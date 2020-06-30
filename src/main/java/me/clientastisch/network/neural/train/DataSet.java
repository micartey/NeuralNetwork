package me.clientastisch.network.neural.train;

import lombok.Getter;
import lombok.SneakyThrows;
import me.clientastisch.network.compiller.Convertable;

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

        writeFile(name, toBase64().get());
    }

    @SneakyThrows
    public DataSet load(String name) {
        File file = new File(name);
        StringBuilder content = new StringBuilder();
        readTextFileByLines(file).forEach(content::append);
        return fromBase64(content.toString()).orElse(null);
    }
}
