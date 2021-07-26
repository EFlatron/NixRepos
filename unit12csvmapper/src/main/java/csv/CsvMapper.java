package csv;

import annotation.CsvMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    public <N> List<N> mapper(Class<N> cl, CsvTable table){
        List<N> copies = new ArrayList<>();
        for (int i = 0; i < table.bodySize(); i++) {
            N instance = mapperRow(cl, table, i);
            copies.add(instance);
        }
        return copies;
    }

    private <N> N mapperRow(Class<N> cl, CsvTable table, int numOfRow){
        try{
            Constructor<N> constructor = cl.getConstructor();
            N instance = constructor.newInstance();

            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                if(!field.isAnnotationPresent(CsvMapping.class)) continue;
                CsvMapping csvMapping = field.getAnnotation(CsvMapping.class);

                String colName = csvMapping.value();
                if(!table.getHeader().contains(colName)) continue;

                String property = table.get(numOfRow, colName);

                Class<?> typeOfField = field.getType();
                if(typeOfField == String.class) {
                    field.set(instance, property);
                } else if(typeOfField == int.class || typeOfField == Integer.class){
                    field.set(instance, Integer.parseInt(property));
                } else if(typeOfField == boolean.class || typeOfField == Boolean.class){
                    field.set(instance, Boolean.parseBoolean(property));
                } else if(typeOfField == char.class || typeOfField == Character.class){
                    field.set(instance, property.charAt(0));
                } else if(typeOfField == long.class || typeOfField == Long.class){
                    field.set(instance, Long.parseLong(property));
                } else if(typeOfField == float.class || typeOfField == Float.class){
                    field.set(instance, Float.parseFloat(property));
                } else if(typeOfField == double.class || typeOfField == Double.class){
                    field.set(instance, Double.parseDouble(property));
                } else if(typeOfField.isEnum()){
                    field.set(instance, Enum.valueOf((Class<? extends Enum>) typeOfField, property));
                } else {
                    throw new RuntimeException("An annotated field is not supported");
                }
            }
            return instance;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
