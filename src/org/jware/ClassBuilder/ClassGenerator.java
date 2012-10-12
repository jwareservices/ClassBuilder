/*
  The JWare Public Access Project - http://jware.public.org

  Copyright (c) 2012-OCT J. Paul Jackson

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
*/

package org.jware.ClassBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 
 * A WORK IN PROGRESS.
 * 
 * 
 * ClassBuilder is a simple tool used to create a template for
 * class and interfaces to be used in the Processing programming 
 * environment.  It uses a Swing generated UI to gather the 
 * appropriate information and then in turn generates the class
 * template for saving to file.  The UI is very straightforward,
 * and fairly self-explanatory.  
 * 
 * <p>
 * I'd like to ultimately make this an actual tool using the
 * tool interface from Processing. 
 * 
 * @author      Paul Jackson<jjacks55@email.cpcc.edu>
 * @version     0.2 Beta, 08/17/2012
 * @since       0.1 build not released
 */

public class ClassGenerator {
    
    /**
     * 
     */
    String NL = System.lineSeparator();
    String TAB = "\t";
    String OCB = "{";
    String CCB = "}";
    String PARENS="()";
    String EOL = ";";
    String SPC = " ";
    
   /**
    * finalClassDef - buffer that contains the completed
    * class definition.  Built from components gathered
    * from the UI
    * 
    * @see ClassGenerator#className
    */
    StringBuilder finalClassDef = new StringBuilder();
    
    StringBuilder classDef = new StringBuilder();
    StringBuilder variables = new StringBuilder();
    StringBuilder methods = new StringBuilder();
    StringBuilder packageName = new StringBuilder();;
    String className, classAccess;
            
    Map<String, List<String>> variablesMap;
    Map<String, List<String>> methodsMap;

    /**
     *
     */
    public ClassGenerator() {
    }
          
    /**
     * Allocates the maps that contain the methods and variables
     * the class.
     */
    public void createLists() {
        variablesMap = new HashMap<>();
        methodsMap = new HashMap<>();
    }
    
    /**
     * Gathers all the parts and concatenates into a single
     * buffer to output to screen and file.
     */
    public void generateClass() {
        
        buildVariables();
        buildMethods();
        finalClassDef.append(packageName.toString());
        finalClassDef.append(classDef.toString());
        finalClassDef.append(variables.toString());
        finalClassDef.append(NL).append(TAB)
                     .append(classAccess).append(SPC)
                     .append(className).append(PARENS).append(SPC)
                     .append(OCB).append(NL).append(TAB).append(CCB)
                     .append(NL).append(NL);
        
        finalClassDef.append(methods.toString());
        finalClassDef.append(CCB);

        System.out.println(finalClassDef.toString());    
    }
 
    /**
     * Sets the package name parameter at the top of the class file.
     * @param _packageName
     */
    public void setPackage(String _packageName) {
        packageName.append("package").append(SPC)
                .append(_packageName).append(SPC)
                .append(EOL).append(NL).append(NL);
    }

    /**
     * Returns the entire class or interface template as string,
     * @return
     */
    public String getClassDef() {
        return finalClassDef.toString();
    }
    
    /**
     * Clears out and reset the buffers for holding content.
     */
    public void reset() {
        createLists();
        packageName.setLength(0);
        finalClassDef.setLength(0);
        classDef.setLength(0);
        variables.setLength(0);
        methods.setLength(0);
    }
    
    /**
     * Sets up the first part of the class definition.  The parameters
     * are sent form the UI and appended to the buffer.
     *
     * @param access
     * @param modifier
     * @param name
     * @param superName
     * @param interfaceName
     */
    public void setClassName (String access, String modifier, String name, String superName, String interfaceName) {

        classAccess=access;
        classDef.append(classAccess);
        classDef.append(SPC);
        if(!"".equals(modifier)) { 
            classDef.append(modifier);
        }
        classDef.append("class");
        classDef.append(SPC);
        className=name;
        classDef.append(className);
        if (!superName.equals("")) {
            classDef.append(SPC);
            classDef.append("extends");
            classDef.append(SPC);
            classDef.append(superName);
        }
        if (!interfaceName.equals("")) {
            classDef.append(SPC);
            classDef.append("implements");
            classDef.append(SPC);
            classDef.append(interfaceName);
        }
        classDef.append(SPC);
        classDef.append(OCB);
        classDef.append(NL);
    }
    
    /**
     * Creates a single variable and adds it to the variable map.
     * @param access
     * @param modifier
     * @param type
     * @param name
     */
    public void createVariable(String access, String modifier , String type, String name) {
        
        List<String> list = variablesMap.get(name);
        if (list == null)
            variablesMap.put(name, list=new ArrayList<>());
        list.add(access);
        list.add(modifier);
        list.add(type);
        list.add(name);
    }
    
    /**
     * Builds the entire list of variables for this class.
     */
    public void buildVariables() {
        
        Iterator<String> itr = variablesMap.keySet().iterator();
        while( itr.hasNext() ) {
            String name = itr.next();
            variables.append(TAB);
             for (Iterator<String> it = variablesMap.get(name).iterator(); it.hasNext();) {
                variables.append(it.next());
                variables.append(SPC);
             }
             variables.setLength(variables.length()-1);
             variables.append(EOL);
             variables.append(NL);
        } 
    }
    /**
     *
     * @param access
     * @param modifier
     * @param type
     * @param name
     */
    public void createMethod(String access, String modifier, String type, String name) {
        
        List<String> list = methodsMap.get(name);
        if (list == null)
            methodsMap.put(name, list=new ArrayList<>());
        list.add(access);
        list.add(modifier);
        list.add(type);
        list.add(name);
    }
    
    /**
     *
     */
    public void buildMethods() {
        
        Iterator<String> itr = methodsMap.keySet().iterator();
        while( itr.hasNext() ) {
            String name = itr.next();
            methods.append(TAB);
             for (Iterator<String> it = methodsMap.get(name).iterator(); it.hasNext();) {
                methods.append(it.next());
                methods.append(SPC);
             }
             methods.setLength(methods.length()-1);
             methods.append(PARENS).append(SPC).append(OCB);
             methods.append(NL).append(TAB).append(CCB).append(NL).append(NL);
        } 
    }
    
}