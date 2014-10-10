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

import java.io.PrintStream;
import java.lang.reflect.*;

/**
 * File: ReflectTest.java Created On: Sometime in 2012
 *
 * @author J. Paul Jackson <jwareservices@gmail.com>
 *
 */
public class ReflectTest {
    
    static PrintStream out = System.out;
    
    public  static void doPrint() {
        
      try {
          Class<?> c = Class.forName( "java.applet.Applet" );
          java.lang.reflect.Method[] methods = c.getDeclaredMethods();
          
          for (int ndx=0; ndx<methods.length; ndx++ ) {
              System.out.println(methods[ndx].getName());
          }
      }
      catch(ClassNotFoundException | SecurityException e) {e.printStackTrace();}
        
        out.println(System.getProperty("java.class.path"));
        
    }
       public static void main(String[] args) {
        
           ReflectTest.doPrint();    

    }
}
