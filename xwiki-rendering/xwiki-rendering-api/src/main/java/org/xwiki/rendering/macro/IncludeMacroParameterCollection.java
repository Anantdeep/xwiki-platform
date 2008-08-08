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
package org.xwiki.rendering.macro;

import java.util.HashMap;
import java.util.Map;

import org.xwiki.rendering.macro.parameter.AbstractMacroParameterCollection;
import org.xwiki.rendering.macro.parameter.MacroParameterException;
import org.xwiki.rendering.macro.parameter.classes.EnumMacroParameterClass;
import org.xwiki.rendering.macro.parameter.classes.MacroParameterClass;
import org.xwiki.rendering.macro.parameter.classes.StringMacroParameterClass;

/**
 * Parse and convert INCLUDE macro parameters values into more readable java values (like boolean, int etc.).
 * 
 * @version $Id: $
 * @since 1.6M1
 */
// TODO: Use an I8N service to translate the descriptions in several languages
public class IncludeMacroParameterCollection extends AbstractMacroParameterCollection
{
    /**
     * @version $Id: $
     */
    public enum Context
    {
        /**
         * Macro executed in its own context.
         */
        NEW,

        /**
         * Macro executed in the context of the current page.
         */
        CURRENT;
    };

    /**
     * The name of the macro parameter "document".
     */
    private static final String PARAM_DOCUMENT = "document";

    /**
     * The description of the macro parameter "document".
     */
    private static final String PARAM_DOCUMENT_DESC =
        "The name of the document to include. For example: \"Space.Page\".";

    /**
     * The default value of the macro parameter "document".
     */
    private static final String PARAM_DOCUMENT_DEF = null;

    /**
     * The name of the macro parameter "context".
     */
    private static final String PARAM_CONTEXT = "context";

    /**
     * The description of the macro parameter "context".
     */
    private static final String PARAM_CONTEXT_DESC =
        "Defines whether the included page is executed in its separated "
            + "execution context or whether it's executed in the contex of the current page. If the value is \""
            + Context.NEW + "\" then it's executed in its own context. If the value is \"" + Context.CURRENT
            + "\" it's executed in the context of the current page. This affects for example whether the Velocity "
            + "variables of the current page will be visible in the included page or not.";

    /**
     * The default value of the macro parameter "context".
     */
    private static final Context PARAM_CONTEXT_DEF = Context.NEW;

    /**
     * The unique list of macro parameters descriptors of the TOC macro.
     */
    private static Map<String, MacroParameterClass< ? >> parametersClasses =
        new HashMap<String, MacroParameterClass< ? >>();

    /**
     * The unique macro parameter descriptor the parameter "document".
     */
    private static final StringMacroParameterClass documentParamClass =
        new StringMacroParameterClass(PARAM_DOCUMENT, PARAM_DOCUMENT_DESC, PARAM_DOCUMENT_DEF);

    /**
     * The unique macro parameter descriptor the parameter "context".
     */
    private static final EnumMacroParameterClass<Context> contextParamClass =
        new EnumMacroParameterClass<Context>(PARAM_CONTEXT, PARAM_CONTEXT_DESC, PARAM_CONTEXT_DEF);

    static {

        documentParamClass.setRequired(true);
        parametersClasses.put(PARAM_DOCUMENT, documentParamClass);

        parametersClasses.put(PARAM_CONTEXT, contextParamClass);
    }

    /**
     * Set the macro parameters class list.
     */
    public IncludeMacroParameterCollection()
    {
        super(parametersClasses);
    }

    /**
     * @return the name of the document to include.
     * @exception MacroParameterException error when converting value.
     */
    public String getDocument() throws MacroParameterException
    {
        return getParameterValue(PARAM_DOCUMENT);
    }

    /**
     * @return defines whether the included page is executed in its separated execution context or whether it's executed
     *         in the contex of the current page.
     * @exception MacroParameterException error when converting value.
     */
    public Context getContext() throws MacroParameterException
    {
        return getParameterValue(PARAM_CONTEXT);
    }
}
