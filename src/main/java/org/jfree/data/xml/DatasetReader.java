/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ------------------
 * DatasetReader.java
 * ------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jfree.chart.internal.Args;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * A utility class for reading datasets from XML.
 */
public class DatasetReader {

    /** A factory for creating new parser instances. */
    static SAXParserFactory factory;

    /**
     * Returns the {@link SAXParserFactory} used to create {@link SAXParser} instances.
     * 
     * @return The {@link SAXParserFactory} (never {@code null}).
     */
    public static SAXParserFactory getSAXParserFactory() {
    	if (factory == null) {
            SAXParserFactory f = SAXParserFactory.newInstance();
            try {
                f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                f.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory = f;
            } catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
    	}
        return factory;
    }
    
    /**
     * Sets the SAXParserFactory that will be used to create SAXParser instances.  
     * You would only call this method if you wish to configure a new factory because
     * the default does not meet requirements.
     * 
     * @param f  the new factory ({@code null} not permitted).
     */
    public static void setSAXParserFactory(SAXParserFactory f) {
    	Args.nullNotPermitted(f, "f");
        factory = f;
    }

    /**
     * Reads a {@link PieDataset} from an XML file.
     *
     * @param file  the file ({@code null} not permitted).
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static PieDataset readPieDatasetFromXML(File file)
            throws IOException {
        InputStream in = new FileInputStream(file);
        return readPieDatasetFromXML(in);
    }

    /**
     * Reads a {@link PieDataset} from a stream.
     *
     * @param in  the input stream.
     *
     * @return A dataset.
     *
     * @throws IOException if there is an I/O error.
     */
    public static PieDataset readPieDatasetFromXML(InputStream in)
             throws IOException {
        PieDataset result = null;
        try {
            SAXParser parser = getSAXParserFactory().newSAXParser();
            PieDatasetHandler handler = new PieDatasetHandler();
            parser.parse(in, handler);
            result = handler.getDataset();
        }
        catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Reads a {@link CategoryDataset} from a file.
     *
     * @param file  the file.
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static CategoryDataset readCategoryDatasetFromXML(File file)
            throws IOException {
        InputStream in = new FileInputStream(file);
        return readCategoryDatasetFromXML(in);
    }

    /**
     * Reads a {@link CategoryDataset} from a stream.
     *
     * @param in  the stream.
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static CategoryDataset readCategoryDatasetFromXML(InputStream in)
            throws IOException {
        CategoryDataset result = null;
        try {
            SAXParser parser = getSAXParserFactory().newSAXParser();
            CategoryDatasetHandler handler = new CategoryDatasetHandler();
            parser.parse(in, handler);
            result = handler.getDataset();
        }
        catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}