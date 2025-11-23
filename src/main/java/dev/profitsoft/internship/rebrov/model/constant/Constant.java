package dev.profitsoft.internship.rebrov.model.constant;

public class Constant {
    /*
     *   Allow absolute and local path, Unix or Windows Type
     */
    public static final String DIRECTORY_PATH_REGEX = "^(?:[A-Za-z]:[\\\\/]|[^\\\\/\\*?\"<>|:]|[./])?[^*?\"<>|]*[\\\\/]?$";

    /*
     *
     * Allow only latin letters with dot separated values
     */
    public static final String ATTR_REGEX = "^[A-Za-z]+(?:\\.[A-Za-z]+)*$";

    public static final Integer MAX_THREAD_COUNT = 8;


}
