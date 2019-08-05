package com.tof.deployment.util.markDown;

import com.tof.deployment.annotation.ForMD;
import com.tof.deployment.annotation.FormDataForMD;
import com.tof.deployment.controller.TestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * WangSong
 * 2019-08-05
 */
public class MarkDownUtil {
    private static StringBuffer MARK_DOWN_STR = new StringBuffer("# custom\n" +
            "## 接口文档\n");

    public static void validAnnotation(List<Class<?>> clsList) {
        if (clsList != null && clsList.size() > 0) {
            for (Class<?> cls : clsList) {
                RequestMapping annotationClazz = cls.getAnnotation(RequestMapping.class);
                String urlPrefix = "";
                if (annotationClazz != null) {
                    urlPrefix = annotationClazz.value()[0];
                }
                //获取类中的所有的方法
                Method[] methods = cls.getDeclaredMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        ForMD scanMethodForApi = method.getAnnotation(ForMD.class);
                        if (scanMethodForApi != null) {
                            //可以做权限验证
                            MARK_DOWN_STR.append("---\n");
                            MARK_DOWN_STR.append("#### " + scanMethodForApi.value());
                            MARK_DOWN_STR.append("\n");
                            System.out.println(scanMethodForApi.value());
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            MARK_DOWN_STR.append("##### url:" + urlPrefix + (requestMapping.value()[0].startsWith("/") ? requestMapping.value()[0] : "/" + requestMapping.value()[0]) + "\n##### method:" + requestMapping.method()[0]);
                            MARK_DOWN_STR.append("\n");
                            System.out.println("url:" + urlPrefix + (requestMapping.value()[0].startsWith("/") ? requestMapping.value()[0] : "/" + requestMapping.value()[0]) + "\nmethod:" + requestMapping.method()[0]);
                            Parameter[] parameters = method.getParameters();
                            for (int i = 0; i < parameters.length; i++) {
                                RequestBody requestBody = parameters[i].getDeclaredAnnotation(RequestBody.class);
                                if (requestBody != null) {
                                    MARK_DOWN_STR.append("##### body");
                                    MARK_DOWN_STR.append("\n```json\n{");
                                    MARK_DOWN_STR.append("\n");
                                    System.out.println("body: \n{");
                                    fieldToString(parameters[i].getType(), 1, "arg");
                                    MARK_DOWN_STR.append("}");
                                    MARK_DOWN_STR.append("\n```\n");
                                    System.out.println("}");
                                }
                                FormDataForMD formDataForMD = parameters[i].getDeclaredAnnotation(FormDataForMD.class);
                                if (formDataForMD != null) {
                                    MARK_DOWN_STR.append("##### formData");
                                    MARK_DOWN_STR.append("\n```json\n{");
                                    MARK_DOWN_STR.append("\n");
                                    System.out.println("formData: \n{");
                                    fieldToString(parameters[i].getType(), 1, "formData");
                                    MARK_DOWN_STR.append("}");
                                    MARK_DOWN_STR.append("\n```\n");
                                    System.out.println("}");
                                }
                            }
                            MARK_DOWN_STR.append("\n");
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    private static void addString(int levelNumber, String name, Class clazz) {
        MARK_DOWN_STR.append(table(levelNumber) + "\"" + name + "\":" + "\"" + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1) + "\"");
        MARK_DOWN_STR.append("\n");
        System.out.println(table(levelNumber) + "\"" + name + "\":" + "\"" + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1) + "\"");

    }

    private static void fieldToString(Class clazz, int levelNumber, String name) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getType().getClassLoader() == null || fields[i].getType() == MultipartFile.class) {
                String endStr = table(levelNumber) + "\"" + fields[i].getName() + "\":" + "\"" + fields[i].getType().getName().substring(fields[i].getType().getName().lastIndexOf('.') + 1) + "\"";
                if (i != fields.length - 1) {
                    endStr += ",";
                }
                MARK_DOWN_STR.append(endStr);
                MARK_DOWN_STR.append("\n");
                System.out.println(endStr);
            } else {
                MARK_DOWN_STR.append(table(levelNumber) + "\"" + fields[i].getName() + "\":" + "{");
                MARK_DOWN_STR.append("\n");
                System.out.println(table(levelNumber) + "\"" + fields[i].getName() + "\":" + "{");
                fieldToString(fields[i].getType(), levelNumber + 1, fields[i].getName());
                String endStr = table(levelNumber) + "}";
                if (i != fields.length - 1) {
                    endStr += ",";
                }
                MARK_DOWN_STR.append(endStr);
                MARK_DOWN_STR.append("\n");
                System.out.println(endStr);
            }
        }
    }

    private static String table(int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += "\t";
        }
        return s;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
        // 获取特定包下所有的类(包括接口和类)
        List<Class<?>> clsList = ClassUtil.getAllClassByPackageName(TestController.class.getPackage());
        //输出所有使用了特定注解的类的注解值
        validAnnotation(clsList);
        File file = new File("README.md");
        Writer out = new FileWriter(file);
        out.write(MARK_DOWN_STR.toString());
        out.close();
    }
}

