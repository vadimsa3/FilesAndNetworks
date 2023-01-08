import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ParserCSV {

    // с применением библиотеки парсеров CSV - OpenCSV
    public void parseCSV(String fileCSV) throws IOException {
        // Cоздаем экземпляр считывателя, разделитель по умолчанию — запятая,
        // символ кавычки по умолчанию - двойная кавычка
        // читаем со строки номер 2 (пропускаем названия столбцов)
        CSVReader reader = new CSVReader(new FileReader(fileCSV), ',', '"', 1);
        // читаем все строки в массив
        List<String[]> allRows = reader.readAll();
        // читаем массив строк построчно
        for (String[] row : allRows) {
            System.out.println(Arrays.toString(row));
        }
    }
}

/*
    Класс парсинга файлов формата CSV.

    Изучите структуру CSV-файлов, лежащих в папках, и создайте класс(ы) для хранения данных из этих файлов.

    Напишите код, который будет принимать CSV-данные и выдавать список соответствующих им объектов.

    Проверьте работу данного класса: передайте ему на вход данные любого из CSV-файлов,
    найденных двумя шагами ранее, и убедитесь, что он выводит данные корректно.
*/