package org.example;

import org.example.handlers.CustomHandler1;
import org.example.services.appService1;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger myLogger0 = Logger.getLogger("org.example");
    private static final Logger myLogger1 = Logger.getLogger("org.example.services");

    static void main() throws IOException {
        // ======================== элементарное протоколирование

        // Logger.getGlobal().setLevel(Level.OFF); // OFF - запрет логирования в корне программы
                                                   //
        // Logger.getGlobal().info("test message"); // тестовая запись в консоль ИНФО

        //======================== тестирование протоколирования разных уровней, точек выполнения entering

        /* ConsoleHandler consoleHandler = new ConsoleHandler(); // определение кастомного обработчика
        consoleHandler.setLevel(Level.FINEST); // опредление фильстра на стороне обработчика

        myLogger1.setLevel(Level.FINEST); // определение фильтра на стороне регистратора
        myLogger1.addHandler(consoleHandler); // использовать кастомный обработчик
        myLogger1.setUseParentHandlers(false); // не использовать корневой обработчик

        // см точку входа и выхода в методе класса

        appService1.makeLog((myLogger, msgApp) -> {
            myLogger.severe(String.format("severe msg from %s", msgApp));
            myLogger.warning(String.format("warning msg from %s", msgApp));
            myLogger.info(String.format("info msg from %s", msgApp));
            myLogger.config(String.format("config msg from %s", msgApp));
            myLogger.fine(String.format("fine msg from %s", msgApp));
            myLogger.finer(String.format("finer msg from %s", msgApp));
            myLogger.finest(String.format("finest msg from %s", msgApp));
        }, myLogger1, "appService1"); */

        //======================= тестирование интернационализации

        // Logger logger = Logger.getLogger("org.example", "org.example.logmsg"); // указано имя комплекта ресурсов, берёт русскую из системы

        // загрузка на другой локали

        /*Locale enLocale = new Locale("en", "EN"); // описание локали для английского

        ResourceBundle bundle = ResourceBundle.getBundle( // описаниение комплекта ресурсов под локаль enLocale
                "org.example.logmsg", // имя комплекта
                enLocale // указание локали для комплекта
        );

        Logger logger = Logger.getLogger("org.example"); // создание регистратора для пакета org.example
        logger.setResourceBundle(bundle); // установка описанного выше комплекта на этот регистратор

        logger.log(Level.INFO, "msg.info", "appServ"); // протокольная запись ИНФО сообщения по коду msg.info с добавкой appServ в {0}*/

        //======================= тестирование логирования в файл

        FileHandler fileHandler = new FileHandler("log", 500,10,true); // определение обработчика в файл
                                               // 500 - предельный объём файла логов в байтах
                                               // 10 - количество файлов логов в списке ротации
                                               // true - разрешение добавления новых записей к старым при перезапуске
                                               // если false - при перезапуске старые удаляются, новые записываются
        fileHandler.setLevel(Level.FINEST);

        myLogger1.setLevel(Level.FINEST);
        myLogger1.addHandler(fileHandler);
        myLogger1.setUseParentHandlers(false);

        // см точку входа и выхода в методе класса

        appService1.makeLog((myLogger, msgApp) -> {
            myLogger.severe(String.format("severe msg from %s", msgApp));
            myLogger.warning(String.format("warning msg from %s", msgApp));
            myLogger.info(String.format("info msg from %s", msgApp));
            myLogger.config(String.format("config msg from %s", msgApp));
            myLogger.fine(String.format("fine msg from %s", msgApp));
            myLogger.finer(String.format("finer msg from %s", msgApp));
            myLogger.finest(String.format("finest msg from %s", msgApp));
        }, myLogger1, "appService1");

    }
}
