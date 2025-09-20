package academy.game;

import java.io.File;
import java.io.IOException;

public class InteractiveMode {

    private final File externalDictionaryPath;

    public InteractiveMode(File externalDictionaryPath){
        this.externalDictionaryPath = externalDictionaryPath;
    }

    public void run(){
        try {
            Dictionary.loadYaml(externalDictionaryPath); // checks for null inside loadYaml
        } catch (IOException e){
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
            return;
        }
    }
}
