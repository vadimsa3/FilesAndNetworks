import java.io.*;

public class ParsFolders {

    // Задание 3. Поиск файлов формата .json и .csv в папке
    /* метод поиска файлов, который использует MyFileNameFilter для определения файлов в формате .json и .csv.
     на вход передаем путь к папке поиска и расширение искомого файла
     */

    public void findFiles(String pathToFolder, String extension) throws IOException {
        // создаем объект папки
        File folder = new File(pathToFolder);
        if (!folder.exists()) System.out.println(pathToFolder + " указанная папка не существует");
        // проверяем файлы из папки на соответствие условию
        // и получаем список файлов с определенным расширением
        File[] listFiles = folder.listFiles(new MyFileNameFilter(extension));
        // выводим совпадения
        for (File file : listFiles) {
            System.out.println("Файл найден: " + file.getAbsoluteFile());
        }
        // получаем все вложенные объекты в каталоге и проверяем на папку
        for (File items : folder.listFiles()) {
            if (items.isDirectory()) {
                findFiles(items.getAbsolutePath(), extension); // обрабатываем папку рекурсивно
            }
        }
    }
}

//    Класс поиска файлов в папках.

//    В методах этого класса необходимо реализовать обход папок, лежащих в архиве.
//    Разархивируйте его
//    и напишите код, который будет обходить все вложенные папки
//    и искать в них файлы форматов JSON и CSV (с расширениями *.json и *.csv).
//    Метод для обхода папок должен принимать путь до папки, в которой надо производить поиск.
//    Проверьте работу данного класса: передайте ему на вход путь к папке и убедитесь,
//    что он вывел информацию о трёх найденных JSON-файлах и о трёх CSV-файлах.