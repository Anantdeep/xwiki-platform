/**
 * Copyright 2006, XpertNet SARL, and individual contributors as indicated
 * by the contributors.txt.
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
 *
 * @author sdumitriu
 */

package com.xpn.xwiki.render.macro;

import java.io.IOException;
import java.io.Writer;

import org.radeox.api.engine.RenderEngine;
import org.radeox.api.engine.context.RenderContext;
import org.radeox.macro.BaseLocaleMacro;
import org.radeox.macro.parameter.MacroParameter;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.render.XWikiRadeoxRenderEngine;

/**
 * Macro that displays images.
 * 
 * Syntax: {image:text|height|width|align|document|alt|title|link|fromIncludingDoc}
 * <ul>
 * <li>text: The target filename.</li>
 * <li>height: The height attribute of the img.</li>
 * <li>width: The width attribute of the img.</li>
 * <li>align: The align attribute of the img.</li>
 * <li>document: The document to which the file is attached. If missing, 
 *   the current document is used.</li>
 * <li>alt: The alt attribute of the link. If missing, title is used.
 *   If both are missing, use the filename.</li>
 * <li>title: The title attribute of the link. If missing, alt is used.
 *   If both are missing, use the filename.</li>
 * <li>link: Also link to the image.</li>
 * <li>fromIncludingDoc: If present, when the current document is included using 
 *   #include*, use the top level document instead of the included one.
 *   This is useful for templates, for example.</li>
 * </ul>
 * @author sdumitriu
 */
public class ImageMacro extends BaseLocaleMacro {
    public String getLocaleKey() {
        return "macro.image";
    }

    public void execute(Writer writer, MacroParameter params) throws IllegalArgumentException, IOException {

        RenderContext context = params.getContext();
        RenderEngine engine = context.getRenderEngine();

        // Read the parameters
        String img = params.get("text", 0);
        if (null == img || img.indexOf("=") != -1) {
            return;
        }
        String height = params.get("height", 1);
        if (null == height || height.indexOf("=") != -1) {
            height = null;
        }
        String width = params.get("width", 2);
        if (null == width || width.indexOf("=") != -1) {
            width = null;
        }
        String align = params.get("align", 3);
        if (null == align || align.indexOf("=") != -1) {
            align = null;
        }
        String document = params.get("document", 4);
        if (null == document || document.indexOf("=") != -1) {
            document = null;
        }
        String alt = params.get("alt", 5);
        if (null == alt || alt.indexOf("=") != -1) {
            alt = null;
        }
        String title = params.get("title", 6);
        if (null == title || title.indexOf("=") != -1) {
            title = null;
        }
        String link = params.get("link", 7);
        if (null == link || link.indexOf("=") != -1 || link.toLowerCase().startsWith("f")) {
            link = null;
        }
        String useIncluder = params.get("fromIncludingDoc", 8);
        if (null == useIncluder || useIncluder.indexOf("=") != -1 || useIncluder.toLowerCase().startsWith("f")) {
            useIncluder = null;
        }

        // Fix missing alt or title parameters
        if (alt == null && title != null) {
            alt = title;
        } else if (alt != null && title == null) {
            title = alt;
        } else {
            alt = title = img;
        }

        XWikiContext xcontext = ((XWikiRadeoxRenderEngine) engine).getContext();

        // Get the target document
        XWikiDocument doc = null;
        try {
            if (document == null || "".equals(document)) {
                if (useIncluder == null) {
                    doc = (XWikiDocument) xcontext.get("sdoc");
                }
            } else {
                doc = xcontext.getWiki().getDocument(xcontext.getDoc().getSpace(), document, xcontext);
            }
        } catch (Exception ex) {
            // NullPointer or ClassCast
        }
        if (doc == null) {
            doc = xcontext.getDoc();
        }

        // Create the img code
        StringBuffer str = new StringBuffer();
        if (link != null) {
            str.append("<a href=\"" + doc.getAttachmentURL(img, "download", xcontext) + "\">");
        }
        str.append("<img src=\"");

        if (img.indexOf("tp://") != -1) {
            str.append(img);
        } else {
            str.append(doc.getAttachmentURL(img, "download", xcontext));
        }
        str.append("\" ");
        if ((!"none".equals(height)) && (height != null) && (!"".equals(height.trim()))) {
            str.append("height=\"" + height.trim() + "\" ");
        }
        if ((!"none".equals(width)) && (width != null) && (!"".equals(width.trim()))) {
            str.append("width=\"" + width.trim() + "\" ");
        }
        if ((!"none".equals(align)) && (align != null) && (!"".equals(align.trim()))) {
            str.append("align=\"" + align.trim() + "\" ");
        }
        str.append("alt=\"");
        str.append(alt);
        str.append("\" title=\"");
        str.append(title);
        str.append("\"/>");
        if (link != null) {
            str.append("</a>");
        }

        // All done, flush the StringBufer
        writer.write(str.toString());
    }
}