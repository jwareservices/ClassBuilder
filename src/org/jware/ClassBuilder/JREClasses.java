package org.jware.ClassBuilder;

/*
 * Copyright (C) 2014 J. Paul Jackson <jwareservices@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.*;
import java.util.*;
import java.util.jar.*;
import static java.lang.System.out;

/**
 * File: JREClasses.java Created: 08/17/2012
 *
 * @author J. Paul Jackson <jwareservices@gmail.com>
 *
 */


public class JREClasses {

    static int count;
    static String RT_PATH = System.getProperty("java.home") + "/lib/rt.jar";

    public static List<String> getFullClassNameList(String _jarName) {
        ArrayList<String> arrayList = new ArrayList<>();
        count = 0;
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(_jarName));
            JarEntry jarEntry = jarFile.getNextJarEntry();
            while (jarEntry != null) {
                String name = jarEntry.getName();
                if (name.startsWith("java") && !name.contains("$")) {
                    arrayList.add(name.replaceAll("/", "\\."));
                }
                jarEntry = jarFile.getNextJarEntry();
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static List<String> getSortedClassNameOnlyList(String _path) {

        List<String> fullClassNameList = JREClasses.getFullClassNameList(_path);
        count = 0;
        ArrayList<String> list = new ArrayList<>();
        for (String str : fullClassNameList) {
            int z = str.lastIndexOf(".class");
            String x = str.substring(0, z);
            list.add(x.substring(x.lastIndexOf(".") + 1));
            count++;
        }
        Collections.sort(list, ASC_ALPHA_ORDER);
        return list;
    }
    static final Comparator<String> ASC_ALPHA_ORDER = new Comparator<String>() {
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    };

    public static List<String> getMethodsForClass(String className) {

        List<String> methodList = new ArrayList<>();
        
        int z = className.lastIndexOf(".class");
        String realClassName = className.substring(0, z);
        try {
            Class<?> c = Class.forName(realClassName);
            java.lang.reflect.Method[] methods = c.getDeclaredMethods();

            for (int ndx = 0; ndx < methods.length; ndx++) {
                methodList.add(methods[ndx].getName());
            }
        } catch (ClassNotFoundException | SecurityException e) {
            e.printStackTrace();
        }

        return methodList;

    }

    public static void main(String[] args) {
        List<String> fullClassNameList = JREClasses.getFullClassNameList(RT_PATH);
        List<String> sortList = getSortedClassNameOnlyList(RT_PATH);

        List<String> methodsList = getMethodsForClass("java.awt.Button");
        
        for (String str : fullClassNameList) {
            out.println("Found class: " + str);
        }
        out.println("*****************************************************************************************");
        out.println();
        out.println();

        for (String str : sortList) {
            out.println("Found class: " + str);
        }
        out.println("Class count: " + count);
        out.println("*****************************************************************************************");
        out.println();
        out.println();

        for (String str : methodsList) {
            out.println("Found class: " + str);
        }

    }
}