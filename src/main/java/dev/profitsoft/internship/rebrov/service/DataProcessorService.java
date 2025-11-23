package dev.profitsoft.internship.rebrov.service;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

import static dev.profitsoft.internship.rebrov.validation.CustomValidator.isClassAttribute;


public class DataProcessorService<T> {

    private final Map<String, List<AccessibleObject>> chainCache = new ConcurrentHashMap<>();

    public Map<String, Integer> countAttributeValuesByItem(T item, String attributePath) {
        if (item == null || attributePath == null || !isClassAttribute(attributePath)) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        // Processing and caching chain of attributes. Example: director.fullName
        List<AccessibleObject> chain;
        if(chainCache.containsKey(attributePath)){
            chain = chainCache.get(attributePath);
        }else {
            chain = resolvePath(item.getClass(), attributePath);
            chainCache.put(attributePath, chain);
        }
        // Check if attribute is Collection or Array
        boolean isCollection = isCollectionType(getLastType(chain));

        Map<String, Integer> result = new HashMap<>();
        processItemAndCount(item, chain, isCollection, result);
        return result;
    }

    private void processItemAndCount(T item, List<AccessibleObject> chain, boolean isCollection, Map<String, Integer> targetMap) {
        try {
            Object value = item;

            for (AccessibleObject step : chain) {
                if (value == null) break;

                if (step instanceof Method m) {
                    value = m.invoke(value);
                } else if (step instanceof Field f) {
                    value = f.get(value);
                }
            }

            addToMap(targetMap, value, isCollection);

        } catch (Exception e) {
            System.err.println("Error processing item: " + e.getMessage());
        }
    }

    private List<AccessibleObject> resolvePath(Class<?> clazz, String path) {
        List<AccessibleObject> chain = new ArrayList<>();
        Class<?> current = clazz;

        try {
            for (String part : path.split("\\.")) {
                AccessibleObject step;
                try {
                    String getter = "get" + Character.toUpperCase(part.charAt(0)) + part.substring(1);
                    Method method = current.getMethod(getter);
                    current = method.getReturnType();
                    step = method;
                } catch (NoSuchMethodException e) {
                    // If no getter, try accessing the field directly
                    Field field = current.getDeclaredField(part);
                    field.setAccessible(true);
                    current = field.getType();
                    step = field;
                }
                chain.add(step);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid path: " + path, e);
        }
        return chain;
    }

    // Add collection attribute or String with comma separated values counting
    private void addToMap(Map<String, Integer> map, Object value, boolean isCollection) {
        if (isCollection) {
            if (value instanceof Collection<?> c){
                c.forEach(v -> increment(map, v));
            }
            else if(value instanceof Object[] a){
                Arrays.stream(a).forEach(v -> increment(map, v));
            }
            else{
                increment(map, value);
            }
        } else {
            if (value instanceof String s && s.contains(",")) {
                for (String v : s.split(",")) increment(map, v.trim());
            } else {
                increment(map, value);
            }
        }
    }

    private void increment(Map<String, Integer> map, Object value) {
        if (value != null && !value.toString().isEmpty()) {
            map.merge(value.toString(), 1, Integer::sum);
        }
    }


    private Class<?> getLastType(List<AccessibleObject> chain) {
        AccessibleObject last = chain.get(chain.size() - 1);
        return (last instanceof Method m) ? m.getReturnType() : ((Field) last).getType();
    }

    private boolean isCollectionType(Class<?> type) {
        return Collection.class.isAssignableFrom(type) || type.isArray();
    }

}