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
package org.xwiki.rendering.macro.parameter.instances;

import org.xwiki.rendering.macro.parameter.MacroParameterException;
import org.xwiki.rendering.macro.parameter.classes.MacroParameterClass;

/**
 * Convert parameter String value to provided Enum class entry.
 * 
 * @param <T> the type of the Enum.
 * @version $Id: $
 */
public class EnumMacroParameter<T extends Enum<T>> extends AbstractMacroParameter<T>
{
    /**
     * @param parameterClass the macro parameter descriptor.
     * @param stringValue the value as String from parser.
     */
    public EnumMacroParameter(MacroParameterClass<T> parameterClass, String stringValue)
    {
        super(parameterClass, stringValue);
    }

    /**
     * @return the value as provided Enum class entry.
     */
    protected T parseValue()
    {
        T def = getParameterClass().getDefaultValue();
        
        T[] values = ((Class<T>) def.getClass()).getEnumConstants();

        String valueAsString = getValueAsString();
        for (T value : values) {
            if (value.toString().equalsIgnoreCase(valueAsString)) {
                return value;
            }
        }

        setErrorInvalid();

        return def;
    }

    /**
     * Generate and register error exception.
     */
    protected void setErrorInvalid()
    {
        StringBuffer errorMessage = new StringBuffer(generateInvalidErrorMessage());

        errorMessage.append(" Valid values are ");

        StringBuffer valueList = new StringBuffer();
        for (T value : ((Class<T>) getParameterClass().getDefaultValue().getClass()).getEnumConstants()) {
            if (valueList.length() > 0) {
                valueList.append(" or ");
            }

            valueList.append('"');
            valueList.append(value);
            valueList.append('"');
        }

        errorMessage.append(valueList);
        errorMessage.append('.');

        this.error = new MacroParameterException(errorMessage.toString());
    }
}
