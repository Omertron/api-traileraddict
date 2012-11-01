/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.traileraddictapi.tools;

import com.moviejukebox.traileraddictapi.TrailerAddictException;
import com.moviejukebox.traileraddictapi.model.Trailer;
import com.moviejukebox.traileraddictapi.model.TrailerSize;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class TrailerAddictParser {

    private static final Logger LOGGER = Logger.getLogger(TrailerAddictParser.class);

    private TrailerAddictParser() {
        // prevents calls from subclass
        throw new UnsupportedOperationException("Class can not be initialised!");
    }

    public static List<Trailer> getTrailers(URL url) {
        Document doc;
        List<Trailer> trailers = new ArrayList<Trailer>();

        try {
            LOGGER.trace("Attempting to get trailer XML from " + url.toString());
            doc = DOMHelper.getEventDocFromUrl(url.toString());
        } catch (TrailerAddictException ex) {
            LOGGER.trace("Exception processing document; " + url.toString());
            LOGGER.trace("Exception: " + ex.getResponse());
            return trailers;
        }

        NodeList nlTrailers = doc.getElementsByTagName("trailer");
        Node nTrailer;
        Element eTrailer;
        Trailer trailer;

        for (int loop = 0; loop < nlTrailers.getLength(); loop++) {
            nTrailer = nlTrailers.item(loop);
            if (nTrailer.getNodeType() == Node.ELEMENT_NODE) {
                eTrailer = (Element) nTrailer;
                trailer = new Trailer();
                trailer.setCombinedTitle(DOMHelper.getValueFromElement(eTrailer, "title"));
                trailer.setLink(DOMHelper.getValueFromElement(eTrailer, "link"));

                String trailerId = DOMHelper.getValueFromElement(eTrailer, "trailer_id");
                if (StringUtils.isNumeric(trailerId)) {
                    trailer.setTrailerId(Integer.parseInt(trailerId));
                }

                trailer.setPublishDate(DOMHelper.getValueFromElement(eTrailer, "pubDate"));
                trailer.addEmbed(DOMHelper.getValueFromElement(eTrailer, "embed"));

                // Simple API stuff here
                trailer.setTrailerTitle(DOMHelper.getValueFromElement(eTrailer, "video_title"));
                trailer.setDescription(DOMHelper.getValueFromElement(eTrailer, "description"));
                trailer.setFilmTitle(DOMHelper.getValueFromElement(eTrailer, "film"));
                trailer.addEmbed(TrailerSize.standard, DOMHelper.getValueFromElement(eTrailer, "embed_standard"));
                trailer.addEmbed(TrailerSize.small, DOMHelper.getValueFromElement(eTrailer, "embed_small"));
                trailer.addEmbed(TrailerSize.medium, DOMHelper.getValueFromElement(eTrailer, "embed_medium"));
                trailer.addEmbed(TrailerSize.large, DOMHelper.getValueFromElement(eTrailer, "embed_large"));
                trailer.setDirectors(DOMHelper.getValueFromElement(eTrailer, "director"));
                trailer.setWriters(DOMHelper.getValueFromElement(eTrailer, "writer"));
                trailer.setCast(DOMHelper.getValueFromElement(eTrailer, "cast"));
                trailer.setStudio(DOMHelper.getValueFromElement(eTrailer, "studio"));
                trailer.setReleaseDate(DOMHelper.getValueFromElement(eTrailer, "release_date"));

                // Add the trailer to the list
                trailers.add(trailer);
            }
        }

        LOGGER.trace("Found " + trailers.size() + " trailers for " + url.toString());
        return trailers;

    }
}
