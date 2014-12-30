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
package com.omertron.traileraddictapi;

import com.omertron.traileraddictapi.model.Trailer;
import com.omertron.traileraddictapi.tools.ApiBuilder;
import com.omertron.traileraddictapi.tools.TrailerAddictParser;
import java.net.URL;
import java.util.List;

public final class TrailerAddictApi {

    private static final int DEFAULT_WIDTH = -1;
    private static final int DEFAULT_COUNT = 1;

    private TrailerAddictApi() {
        throw new UnsupportedOperationException("Class cannot be initialised");
    }

    /**
     * List trailers by a particular film.
     *
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is max-payne
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
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is max-payne
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
     * This method uses the string representation of the film name from the TrailerAddict website. E.G. "Max Payne" is max-payne
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
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is brad-pitt
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
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is brad-pitt
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
     * This method uses the actor representation of the actor's name from the TrailerAddict website. E.G. Brad Pitt is brad-pitt
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

    /**
     * Get more detailed information about a trailer.
     *
     * @param trailerUrl Pass the full URL of the trailer
     * @return
     * @throws TrailerAddictException
     */
    public static Trailer getSimpleApi(String trailerUrl) throws TrailerAddictException {
        URL url = ApiBuilder.getSimpleUrl(trailerUrl);
        List<Trailer> trailers = TrailerAddictParser.getTrailers(url);
        if (trailers == null || trailers.isEmpty()) {
            // Nothing found
            return new Trailer();
        }
        // return the first (and only) trailer
        return trailers.get(0);
    }

    /**
     * Get more detailed information about a trailer.
     *
     * @param trailer Pass the trailer object as returned by another method
     * @return
     * @throws TrailerAddictException
     */
    public static Trailer getSimpleApi(Trailer trailer) throws TrailerAddictException {
        return getSimpleApi(trailer.getLink());
    }
}
