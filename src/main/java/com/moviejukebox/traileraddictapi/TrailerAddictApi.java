/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.traileraddictapi;

import com.moviejukebox.traileraddictapi.model.Trailer;
import com.moviejukebox.traileraddictapi.tools.ApiBuilder;
import com.moviejukebox.traileraddictapi.tools.TrailerAddictParser;
import java.net.URL;
import java.util.List;
import org.apache.log4j.Logger;

public final class TrailerAddictApi {

    private static final Logger LOGGER = Logger.getLogger(TrailerAddictApi.class);
    private static final int DEFAULT_WIDTH = -1;
    private static final int DEFAULT_COUNT = 1;

    private TrailerAddictApi() {
        throw new UnsupportedOperationException("Class cannot be initialised");
    }

    public static List<Trailer> getFilm(String filmId, int count, int width) throws TrailerAddictException {
        URL url = ApiBuilder.getFilmUrl(filmId, count, width);
        return TrailerAddictParser.getFilmTrailers(url.toString());
    }

    public static List<Trailer> getFilm(String filmId, int count) throws TrailerAddictException {
        return getFilm(filmId, count, DEFAULT_WIDTH);
    }

    public static List<Trailer> getFilm(String filmId) throws TrailerAddictException {
        return getFilm(filmId, DEFAULT_COUNT, DEFAULT_WIDTH);
    }
}
