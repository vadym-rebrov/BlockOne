package dev.profitsoft.internship.rebrov.model.constant;

public class Constant {
    /*
     * Допускає абсолютний шлях до директорії Windows або Unix типів.
     * Windows: C:\folder\sub\
     * Unix: /home/user/docs/
     * Заборонені символи Windows: *?"<>|
     * Дозволяє завершальний символ '\' або '/'
     */
    public static final String DIRECTORY_PATH_REGEX = "^(?:[A-Za-z]:[\\\\/]|[^\\\\/\\*?\"<>|:]|[./])?[^*?\"<>|]*[\\\\/]?$";

    /*
     * Дозволяються лише:
     * - латинські літери в нижньому регістрі
     * - вкладені атрибути розділяються через "."
     * Заборонено завершувати рядок на "."
     */
    public static final String ATTR_REGEX = "^[a-z]+(?:\\.[a-z]+)*$";

    public static final Integer MAX_THREAD_COUNT = 8;


}
