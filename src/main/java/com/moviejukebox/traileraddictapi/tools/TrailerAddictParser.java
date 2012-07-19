package com.moviejukebox.traileraddictapi.tools;

import com.moviejukebox.traileraddictapi.model.Trailer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.WebServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class TrailerAddictParser {

    private static final Logger LOGGER = Logger.getLogger(TrailerAddictParser.class);

    public static List<Trailer> getTrailers(URL url) {
        Document doc;
        List<Trailer> trailers = new ArrayList<Trailer>();

        try {
            LOGGER.trace("Attempting to get trailer XML from " + url.toString());
            doc = DOMHelper.getEventDocFromUrl(url.toString());
        } catch (WebServiceException ex) {
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
                trailer.setTitle(DOMHelper.getValueFromElement(eTrailer, "title"));
                trailer.setLink(DOMHelper.getValueFromElement(eTrailer, "link"));

                String trailerId = DOMHelper.getValueFromElement(eTrailer, "trailer_id");
                if(StringUtils.isNumeric(trailerId)) {
                    trailer.setTrailerId(Integer.parseInt(trailerId));
                }

                trailer.setPublishDate(DOMHelper.getValueFromElement(eTrailer, "pubDate"));
                trailer.setEmbed(DOMHelper.getValueFromElement(eTrailer, "embed"));

                trailers.add(trailer);
            }
        }

        LOGGER.trace("Found " + trailers.size() + " trailers for " + url.toString());
        return trailers;

    }

}
