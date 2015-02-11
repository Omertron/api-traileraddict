/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TrailerAddict API.
 *
 *      TrailerAddict API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TrailerAddict API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TrailerAddict API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.traileraddictapi.tools;

import com.omertron.traileraddictapi.TrailerAddictException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.yamj.api.common.exception.ApiExceptionType;
import org.yamj.api.common.http.DigestedResponse;
import org.yamj.api.common.http.DigestedResponseReader;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

/**
 * Generic set of routines to process the DOM model data
 *
 * @author Stuart.Boston
 *
 */
public class DOMHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DOMHelper.class);
    private static final String YES = "yes";
    private static final CloseableHttpClient HTTP_CLIENT = new SimpleHttpClientBuilder().build();
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final Charset CHARSET = Charset.forName(DEFAULT_CHARSET);
    /*
     * Constants
     */
    private static final String UNABLE_TO_PARSE = "Unable to parse TheTVDb response, please try again later.";
    private static final String ERROR_WRITING_DOC = "Error writing the document to {}";

    // Hide the constructor
    protected DOMHelper() {
        // prevents calls from subclass
        throw new UnsupportedOperationException("Class can not be initialised!");
    }

    /**
     * Gets the string value of the tag element name passed
     *
     * @param element
     * @param tagName
     * @return
     */
    public static String getValueFromElement(Element element, String tagName) {
        NodeList elementNodeList = element.getElementsByTagName(tagName);

        String value = "";

        if (elementNodeList != null) {
            Element tagElement = (Element) elementNodeList.item(0);
            if (tagElement != null) {
                NodeList tagNodeList = tagElement.getChildNodes();
                if (tagNodeList != null && tagNodeList.getLength() > 0) {
                    value = ((Node) tagNodeList.item(0)).getNodeValue();
                }
            }
        }

        return value;
    }

    /**
     * Get a DOM document from the supplied URL
     *
     * @param url
     * @return
     * @throws TrailerAddictException
     */
    public static synchronized Document getEventDocFromUrl(String url) throws TrailerAddictException {
        InputStream in = null;
        Document doc = null;

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("accept", "application/xml");
            final DigestedResponse response = DigestedResponseReader.requestContent(HTTP_CLIENT, httpGet, CHARSET);

            if (response.getStatusCode() >= 500) {
                throw new TrailerAddictException(ApiExceptionType.HTTP_503_ERROR, url);
            } else if (response.getStatusCode() >= 300) {
                throw new TrailerAddictException(ApiExceptionType.HTTP_404_ERROR, url);
            }

            in = new ByteArrayInputStream(response.getContent().getBytes(DEFAULT_CHARSET));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(in);
            doc.getDocumentElement().normalize();
        } catch (UnsupportedEncodingException ex) {
            throw new TrailerAddictException(ApiExceptionType.INVALID_URL, "Unable to encode URL", url, ex);
        } catch (ParserConfigurationException error) {
            throw new TrailerAddictException(ApiExceptionType.MAPPING_FAILED, UNABLE_TO_PARSE, url, error);
        } catch (SAXException error) {
            throw new TrailerAddictException(ApiExceptionType.MAPPING_FAILED, UNABLE_TO_PARSE, url, error);
        } catch (IOException error) {
            throw new TrailerAddictException(ApiExceptionType.MAPPING_FAILED, UNABLE_TO_PARSE, url, error);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // Input Stream was already closed or null
                LOG.trace("Stream already closed for getEventDocFromUrl", ex);
            }
        }

        return doc;
    }

    /**
     * Convert a DOM document to a string
     *
     * @param doc
     * @return
     * @throws TransformerException
     */
    public static String convertDocToString(Document doc) throws TransformerException {
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
        trans.setOutputProperty(OutputKeys.INDENT, YES);

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        return sw.toString();
    }

    /**
     * Write the Document out to a file using nice formatting
     *
     * @param doc The document to save
     * @param localFile The file to write to
     * @return
     */
    public static boolean writeDocumentToFile(Document doc, String localFile) {
        try {
            TransformerFactory transfact = TransformerFactory.newInstance();
            Transformer trans = transfact.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
            trans.setOutputProperty(OutputKeys.INDENT, YES);
            trans.transform(new DOMSource(doc), new StreamResult(new File(localFile)));
            return true;
        } catch (TransformerConfigurationException ex) {
            LOG.warn(ERROR_WRITING_DOC, localFile, ex);
            return false;
        } catch (TransformerException ex) {
            LOG.warn(ERROR_WRITING_DOC, localFile, ex);
            return false;
        }
    }

    /**
     * Add a child element to a parent element
     *
     * @param doc
     * @param parentElement
     * @param elementName
     * @param elementValue
     */
    public static void appendChild(Document doc, Element parentElement, String elementName, String elementValue) {
        Element child = doc.createElement(elementName);
        Text text = doc.createTextNode(elementValue);
        child.appendChild(text);
        parentElement.appendChild(child);
    }

}
