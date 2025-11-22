package dev.profitsoft.internship.rebrov.validation;

import static dev.profitsoft.internship.rebrov.model.constant.Constant.ATTR_REGEX;
import static dev.profitsoft.internship.rebrov.model.constant.Constant.DIRECTORY_PATH_REGEX;


public class CustomValidator {

    public static boolean isDirectoryPath(String directoryPath){
        return directoryPath.matches(DIRECTORY_PATH_REGEX);
    }

    public static boolean isClassAttribute(String attrName){
       return attrName.matches(ATTR_REGEX);

    }
}
