/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.macro.parameter.classes;

import org.xwiki.rendering.macro.parameter.instances.BooleanMacroParameter;
import org.xwiki.rendering.macro.parameter.instances.MacroParameter;

/**
 * Describe a macro parameter that can be true or false.
 * 
 * @version $Id: $
 */
public class BooleanMacroParameterClass extends AbstractMacroParameterClass<Boolean>
{
    /**
     * @param name the name of the parameter.
     * @param descritpion the description of the parameter.
     * @param def the default value.
     */
    public BooleanMacroParameterClass(String name, String descritpion, boolean def)
    {
        super(name, descritpion, def);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.xwiki.rendering.macro.parameter.classes.MacroParameterClass#newInstance(java.lang.String)
     */
    public MacroParameter<Boolean> newInstance(String value)
    {
        return new BooleanMacroParameter(this, value);
    }
}
