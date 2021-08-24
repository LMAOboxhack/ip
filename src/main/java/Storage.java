import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private Path storageFilePath;

    public Storage(String pathString) {
        this.storageFilePath = Paths.get(pathString);
        try {
            Files.createDirectories(this.storageFilePath.getParent());
            if (!Files.exists(storageFilePath)) {
                Files.createFile(storageFilePath);
            }
        } catch (IOException e) {
            System.out.printf("Failed to create storage file: %s\n", e.getMessage());
        }
    }

    public TaskList loadTaskList() {
        try (InputStream is = Files.newInputStream(storageFilePath);
                ObjectInputStream ois = new ObjectInputStream(is)) {
            // Only Task objects are written to / removed from the list
            return (TaskList) ois.readObject();
        } catch (IOException e) {
            System.out.println("Save file not found; Creating new task list\n");
            return new TaskList();
        } catch (ClassNotFoundException e) {
            System.out.printf("Failed to load from storage: %s\nCreating new task list\n", e.getMessage());
            return new TaskList();
        }
    }

    public void saveToDisk(TaskList taskList) {
        try (OutputStream os = Files.newOutputStream(storageFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(os);) {
            oos.writeObject(taskList);
        } catch (IOException e) {
            System.out.printf("Failed to save to disk: %s\n", e.getMessage());
        }
    }
}
