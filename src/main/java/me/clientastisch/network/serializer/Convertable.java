package me.clientastisch.network.serializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public interface Convertable<T extends Convertable> extends Serializable, Cloneable {

    public default Optional<String> toBase64() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            return Optional.ofNullable(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        } catch (Throwable ex) {
            return Optional.empty();
        }
    }

    public default Optional<T> fromBase64(String content) {
        try {
            byte[] bytes = Base64.getDecoder().decode(content);
            ObjectInputStream outputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            T object = (T) outputStream.readObject();
            outputStream.close();
            return Optional.ofNullable(object);
        } catch (Throwable ex) {
            return Optional.empty();
        }
    }

    public default List<String> readTextFileByLines(java.io.File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return lines;
    }

    public default void writeFile(String filename, String text) throws Exception {
        Files.write(Paths.get(filename), text.getBytes());
    }
}
