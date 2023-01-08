import java.io.File;
import java.io.FilenameFilter;

// реализация интерфейса FileNameFilter при рекурсивном поиске файлов в ParsFolders

public class MyFileNameFilter implements FilenameFilter {
    private String extension;

    // возвращает значение строки, преобразованное в нижний регистр
    public MyFileNameFilter(String extension) {
        this.extension = extension.toLowerCase();
    }

    @Override
    // метод проверяет имя файла на наличие расширения
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(extension);
    }
}
