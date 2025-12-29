import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StringUtils {
    public static void universalToString(Object obj) throws IllegalAccessException {
        StringBuilder resultString = new StringBuilder("====Fields====\n");
        Class cl = obj.getClass();
        for (Field f : cl.getDeclaredFields()){
            f.setAccessible(true);
            resultString.append(String.format("name %s, value = %s;\n", f.getName(), f.get(obj)));
        }
        resultString.append("\n====Methods====\n");

        for (Method m : cl.getDeclaredMethods()){
            m.setAccessible(true);
            resultString.append(String.format("%s %s\n", Modifier.toString(m.getModifiers()), m.getName()));
        }

        System.out.println(resultString);
    }
}
