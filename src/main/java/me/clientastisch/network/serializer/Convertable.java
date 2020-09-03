package me.clientastisch.network.serializer;

import lombok.SneakyThrows;

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

    @SneakyThrows
    public default void toFile(String filename) {
        Files.write(Paths.get(filename), toBase64().get().getBytes());
    }

    @SneakyThrows
    public default Optional<T> fromFile(String filename) {
        return fromBase64(Files.readAllLines(new File(filename).toPath(), StandardCharsets.UTF_8).stream().map(String::join).findFirst().orElse(null));
    }
}
