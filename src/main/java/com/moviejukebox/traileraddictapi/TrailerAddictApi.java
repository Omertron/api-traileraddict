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

    /**
     * List trailers by a particular film.
     *
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is
     * max-payne
     *
     * @param filmId
     * @param count The number of trailers to return
     * @param width The width to be used in the embedded URL
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilm(String filmId, int count, int width) throws TrailerAddictException {
        URL url = ApiBuilder.getFilmUrl(filmId, count, width);
        return TrailerAddictParser.getTrailers(url);
    }

    /**
     * List trailers by a particular film.
     *
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is
     * max-payne
     *
     * @param filmId
     * @param count The number of trailers to return
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilm(String filmId, int count) throws TrailerAddictException {
        return getFilm(filmId, count, DEFAULT_WIDTH);
    }

    /**
     * List trailers by a particular film.
     *
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is
     * max-payne
     *
     * @param filmId
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilm(String filmId) throws TrailerAddictException {
        return getFilm(filmId, DEFAULT_COUNT, DEFAULT_WIDTH);
    }

    /**
     * List trailers by a particular actor.
     *
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is
     * brad-pitt
     *
     * @param actorId
     * @param count The number of trailers to return
     * @param width The width to be used in the embedded URL
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getActor(String actorId, int count, int width) throws TrailerAddictException {
        URL url = ApiBuilder.getActorUrl(actorId, count, width);
        return TrailerAddictParser.getTrailers(url);
    }

    /**
     * List trailers by a particular actor.
     *
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is
     * brad-pitt
     *
     * @param actorId
     * @param count The number of trailers to return
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getActor(String actorId, int count) throws TrailerAddictException {
        return getActor(actorId, count, DEFAULT_WIDTH);
    }

    /**
     * List trailers by a particular actor.
     *
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is
     * brad-pitt
     *
     * @param actorId
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getActor(String actorId) throws TrailerAddictException {
        return getActor(actorId, DEFAULT_COUNT, DEFAULT_WIDTH);
    }

    /**
     * List trailers by their IMDB ID.
     *
     * This method uses the IMDB ID to get a list of trailers.
     *
     * @param imdbId
     * @param count The number of trailers to return
     * @param width The width to be used in the embedded URL
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilmImdb(String imdbId, int count, int width) throws TrailerAddictException {
        URL url = ApiBuilder.getImdbUrl(imdbId, count, width);
        return TrailerAddictParser.getTrailers(url);
    }

    /**
     * List trailers by their IMDB ID.
     *
     * This method uses the IMDB ID to get a list of trailers.
     *
     * @param imdbId
     * @param count The number of trailers to return
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilmImdb(String imdbId, int count) throws TrailerAddictException {
        return getFilmImdb(imdbId, count, DEFAULT_WIDTH);
    }

    /**
     * List trailers by their IMDB ID.
     *
     * This method uses the IMDB ID to get a list of trailers.
     *
     * @param imdbId
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFilmImdb(String imdbId) throws TrailerAddictException {
        return getFilmImdb(imdbId, DEFAULT_COUNT, DEFAULT_WIDTH);
    }

    /**
     * Get a list of the featured trailers or just the newest trailers.
     *
     * Setting featured="yes" will list all trailers TrailerAddict deems popular or important.
     *
     * Setting featured="no" then the most recent trailer additions will be listed
     *
     * @param featured Either 'yes' or 'no'
     * @param count The number of trailers to return
     * @param width The width to be used in the embedded URL
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFeatured(String featured, int count, int width) throws TrailerAddictException {
        URL url = ApiBuilder.getFeaturedUrl(featured, count, width);
        return TrailerAddictParser.getTrailers(url);
    }

    /**
     * Get a list of the featured trailers or just the newest trailers.
     *
     * Setting featured="yes" will list all trailers TrailerAddict deems popular or important.
     *
     * Setting featured="no" then the most recent trailer additions will be listed
     *
     * @param featured Either 'yes' or 'no'
     * @param count The number of trailers to return
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFeatured(String featured, int count) throws TrailerAddictException {
        return getFeatured(featured, count, DEFAULT_WIDTH);
    }

    /**
     * Get a list of the featured trailers or just the newest trailers.
     *
     * Setting featured="yes" will list all trailers TrailerAddict deems popular or important.
     *
     * Setting featured="no" then the most recent trailer additions will be listed
     *
     * @param featured Either 'yes' or 'no'
     * @return
     * @throws TrailerAddictException
     */
    public static List<Trailer> getFeatured(String featured) throws TrailerAddictException {
        return getFeatured(featured, DEFAULT_COUNT, DEFAULT_WIDTH);
    }
}
