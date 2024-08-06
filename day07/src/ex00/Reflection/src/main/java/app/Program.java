package app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printClasses();
        String className = scanner.nextLine();
        try {
            Class<?> aClass = Class.forName("models." + className);
            printBorder();
            printFields(aClass);
            printMethods(aClass);
            Object object = createObject(aClass, scanner);
            if (object == null) {
                System.out.println("Can not create an object");
                System.exit(-1);
            }
            changeField(object, aClass, scanner);
            callMethod(object, aClass, scanner);
        } catch (ClassNotFoundException | RuntimeException | NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    private static void printClasses() {
        System.out.println("Classes:\n  - User\n  - Car");
        printBorder();
        System.out.print("Enter class name:\n-> ");
    }

    private static void printFields(Class<?> aClass) {
        System.out.println("fields:");
        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("\t\t"
                    + field.getType().getSimpleName()
                    + " "
                    + field.getName());
        }
        printBorder();
    }

    private static void printMethods(Class<?> aClass) {
        System.out.println("methods:");
        final Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.print("\t\t"
                    + method.getReturnType().getSimpleName()
                    + " "
                    + method.getName()
                    + "(");
            Class<?>[] parameters = method.getParameterTypes();
            int count = 0;
            for (Class<?> parameter : parameters) {
                System.out.print(parameter.getSimpleName());
                if (parameters.length > 1 && count < parameters.length - 1) {
                    System.out.print(", ");
                    count++;
                }
            }
            System.out.println(")");
        }
        printBorder();
    }

    private static Object createObject(Class<?> aClass, Scanner scanner) {
        System.out.println("Let’s create an object.");
        Object object;
        try {
            object = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }

        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            System.out.print(field.getName() + ":\n-> ");
            String value = scanner.nextLine();
            String fieldTypeName = field.getType().getSimpleName();
            System.out.println("value = " + value
                    + ", type = " + fieldTypeName);
            try {
                switch (fieldTypeName) {
                    case "String":
                        field.set(object, value);
                        break;
                    case "int":
                        field.set(object, Integer.parseInt(value));
                        break;
                    case "Double":
                        field.set(object, Double.parseDouble(value));
                        break;
                    case "Boolean":
                        field.set(object, Boolean.parseBoolean(value));
                        break;
                    case "Long":
                        field.set(object, Long.parseLong(value));
                        break;
                    default:
                        throw new IllegalStateException(
                                "Unexpected value: " + fieldTypeName);
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
                return null;
            }

        }
        System.out.println("Object created: " + object);
        return object;
    }

    private static void changeField(Object object,
                                    Class<?> aClass, Scanner scanner) {
        System.out.print("Enter name of the field for changing:\n-> ");
        String fieldName = scanner.nextLine();
        try {
            Field field = aClass.getDeclaredField(fieldName);
            String fieldTypeName = field.getType().getSimpleName();
            field.setAccessible(true);
            System.out.print("Enter " + fieldTypeName + " value:\n-> ");
            String value = scanner.nextLine();
            switch (fieldTypeName) {
                case "String":
                    field.set(object, value);
                    break;
                case "int":
                    field.set(object, Integer.parseInt(value));
                    break;
                case "Double":
                    field.set(object, Double.parseDouble(value));
                    break;
                case "Boolean":
                    field.set(object, Boolean.parseBoolean(value));
                    break;
                case "Long":
                    field.set(object, Long.parseLong(value));
                    break;
                default:
                    throw new IllegalStateException(
                            "Unexpected value: " + fieldTypeName);
            }
            System.out.println("Object updated: " + object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        printBorder();
    }

    private static void callMethod(Object object,
                                   Class<?> aClass, Scanner scanner) {
        System.out.print("Enter name of the method for call:\n-> ");
        String input = scanner.nextLine();
        int openBracketIndex = input.indexOf('(');
        int closeBracketIndex = input.indexOf(')');
        if (openBracketIndex == -1 || closeBracketIndex == -1
                || closeBracketIndex < openBracketIndex) {
            System.out.println("Invalid method call syntax!");
            return;
        }

        String methodName = input.substring(0, openBracketIndex);
        String paramsStr = input.substring(
                openBracketIndex + 1, closeBracketIndex);
        String[] params = paramsStr.split(", ");
        Method method = findMethod(aClass, methodName);
        if (method == null) {
            System.out.println("Method not found");
            return;
        }

        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            String typeName = paramTypes[i].getSimpleName();
            System.out.print("Enter " + typeName + " value:\n-> ");
            String value = scanner.nextLine();
            if (paramTypes[i].equals(String.class)) {
                args[i] = value;
            } else if (paramTypes[i].equals(int.class)) {
                args[i] = Integer.parseInt(value);
            } else if (paramTypes[i].equals(double.class)) {
                args[i] = Double.parseDouble(value);
            } else if (paramTypes[i].equals(Boolean.class)) {
                args[i] = Boolean.parseBoolean(value);
            } else if (paramTypes[i].equals(Long.class)) {
                args[i] = Long.parseLong(value);
            } else {
                throw new IllegalStateException(
                        "Unexpected value: " + typeName);
            }
        }
        try {
            Object resultObject = method.invoke(object, args);
            if (method.getReturnType() != void.class) {
                System.out.println("Method returned: " + resultObject);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        printBorder();
    }

    private static Method findMethod(Class<?> aClass, String methodName) {
        for (Method method : aClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }


    private static void printBorder() {
        System.out.println("---------------------");
    }
}
