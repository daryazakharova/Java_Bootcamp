package edu.school21.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.*;
import java.util.stream.Collectors;

public class Program {
    final static private String SEPARATOR = "---------------------";
    final static private String PACKAGE_NAME = "edu.school21.classes.";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Classes:");
        Set<Class<?>> classes = getClassesFromPackage(PACKAGE_NAME);
        for (Class<?> clazz : classes) {
            System.out.println("  - " + clazz.getSimpleName());
        }

        System.out.println(SEPARATOR);
        System.out.print("Enter class name:\n-> ");
        String className = scanner.nextLine();
        Class<?> clazz;
        try {
            clazz = Class.forName(PACKAGE_NAME + className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            return;
        }

        System.out.println(SEPARATOR);
        System.out.println("fields:");
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> System.out.println("\t" + field.getName()));

        System.out.println("methods:");
        Method[] methods = getMethods(clazz);

        System.out.println(SEPARATOR);
        System.out.println("Let’s create an object.");
        Object instance = createAnObject(clazz);
        if (instance == null) return;

        System.out.println(SEPARATOR);
        System.out.print("Enter name of the field for changing:\n-> ");
        modifyField(instance);

        System.out.println(SEPARATOR);
        System.out.print("Enter name of the method for call:\n-> ");
        String methodToCall = scanner.nextLine().trim();
        Method method = getCollMethod(methods, methodToCall);

        System.out.print("Enter " + method.getReturnType().getSimpleName() + " value:\n-> ");
        invokeMethod(instance, method);
        System.out.println(SEPARATOR);
    }

    private static void invokeMethod(Object instance, Method method) throws InvocationTargetException, IllegalAccessException {
        String methodValueStr = scanner.nextLine().trim();

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] methodArgs = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> paramType = parameterTypes[i];
            boolean validInput = false;

            while (!validInput) {
                try {
                    if (paramType == String.class) {
                        methodArgs[i] = methodValueStr;
                        validInput = true;
                    } else if (paramType == int.class || paramType == Integer.class) {
                        methodArgs[i] = Integer.parseInt(methodValueStr);
                        validInput = true;
                    } else if (paramType == long.class || paramType == Long.class) {
                        methodArgs[i] = Long.parseLong(methodValueStr);
                        validInput = true;
                    } else if (paramType == double.class || paramType == Double.class) {
                        methodArgs[i] = Double.parseDouble(methodValueStr);
                        validInput = true;
                    } else if (paramType == boolean.class || paramType == Boolean.class) {
                        methodArgs[i] = Boolean.parseBoolean(methodValueStr);
                        validInput = true;
                    } else {
                        throw new IllegalArgumentException("Unsupported parameter type: " + paramType.getSimpleName());
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid " + paramType.getSimpleName() + " value:\n-> ");
                    methodValueStr = scanner.nextLine().trim();
                }
            }
        }

        Object returnValue = method.invoke(instance, methodArgs);
        System.out.println("Method returned: " + returnValue);
    }

    public static Set<Class<?>> getClassesFromPackage(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class);
    }

    public static Method[] getMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String parameters = Arrays.stream(method.getParameters())
                    .map(parameter -> parameter.getType().getSimpleName())
                    .collect(Collectors.joining(", "));
            System.out.println("\t " + method.getReturnType().getSimpleName() + " " + method.getName() + "(" + parameters + ")");
        }
        return methods;
    }

    public static Method getCollMethod(Method[] methods, String methodName) {
        return Arrays.stream(methods).filter((method) -> {
            String parameters = Arrays.stream(method.getParameters())
                    .map(parameter -> parameter.getType().getSimpleName())
                    .collect(Collectors.joining(", "));
            String methodWithParams = method.getName() + "(" + parameters + ")";
            return methodWithParams.equals(methodName);
        }).findFirst().orElseThrow(() -> new NoSuchElementException("There is no such a method"));
    }

    private static Object createAnObject(Class<?> clazz) {
        Object obj;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            obj = constructor.newInstance();
        } catch (Exception e) {
            System.out.println("Error creating object: " + e.getMessage());
            return null;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.print(field.getName() + ":\n-> ");
            boolean validInput = false;

            while (!validInput) {
                try {
                    Object value = getFieldValue(field);
                    field.set(obj, value);
                    validInput = true;
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Invalid input. Try again.");
                } catch (IllegalAccessException e) {
                    System.out.println("Access error: " + e.getMessage());
                    return null;
                }
            }
        }
        System.out.println("Object created: " + obj);
        return obj;
    }

   private static void modifyField(Object instance) {
       String fieldName;

       while (true) {
           fieldName = scanner.nextLine().trim();
           try {
               Field field = instance.getClass().getDeclaredField(fieldName);
               System.out.print("Enter " + field.getType().getSimpleName() + " value:\n-> " );
               field.setAccessible(true);

               Object newValue = getFieldValue(field);
               if (newValue == null) {
                   System.out.println("Failed to get a valid value for the field.");
                   return;
               }

               field.set(instance, newValue);
               System.out.println("Object updated: " + instance);
               break;
           }catch (InputMismatchException e) {
               scanner.nextLine();
               System.out.println("Invalid input. Try again.");
           } catch (Exception e) {
               System.out.println("An error occurred: " + e.getMessage());
               return;
           }
       }
   }

    private static Object getFieldValue(Field field) {
        String fieldTypeName = field.getType().getSimpleName();
        Object value;

        switch (fieldTypeName) {
            case "String":
                value = getString();
                break;
            case "Integer":
            case "int":
                value = getInt();
                break;
            case "Long":
            case "long":
                value = getLong();
                break;
            case "Double":
            case "double":
                value = getDouble();
                break;
            case "Boolean":
            case "boolean":
                value = getBoolean();
                break;
            default:
                System.out.println("Unsupported field type: " + fieldTypeName);
                return null;
        }
        return value;
    }

    private static String getString(){
        return scanner.nextLine();
    }

    private static int getInt(){
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static double getDouble(){
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    private static long getLong(){
        long val = scanner.nextLong();
        scanner.nextLine();
        return val;
    }

    private static boolean getBoolean(){
        boolean val = scanner.nextBoolean();
        scanner.nextLine();
        return val;
    }

}
