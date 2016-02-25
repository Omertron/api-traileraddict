/*
 *      Copyright (c) 2004-2016 Stuart Boston
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
import com.omertron.traileraddictapi.model.TrailerSize;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Trailer Addict API
 *
 * @author stuart.boston
 */
public class TrailerAddictApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(TrailerAddictApiTest.class);
    private static final List<String> FILM_IDS = new ArrayList<>();
    private static final List<String> IMDB_IDS = new ArrayList<>();
    private static final List<String> ACTOR_IDS = new ArrayList<>();
    private static final List<String> FEATURED_IDS = new ArrayList<>();
    private static final int NUMBER_OF_TRAILERS = 4;
    private static final int REQUIRED_WIDTH = 720;
    private static final String TEST_WIDTH_COMPARE = "width=\"" + REQUIRED_WIDTH + "\"";
    private static final String TRAILERS_EMPTY = "List of trailers is empty for ";
    private static final String TRAILERS_NULL = "List of trailers is null for ";
    private static final String INCORRECT_NUMBER = "Incorrect number of trailers for ";
    private static final String INCORRECT_WIDTH = "Incorrect width found in results for ";
    private static final String NO_CUSTOM_TRAILER_SIZE = "No custom trailer size found for ";

    @BeforeClass
    public static void setUpClass() {
        TestLogger.configure();

        // Add the film IDs
        FILM_IDS.add("the-hobbit");

        IMDB_IDS.add("tt0903624");  // The Hobbit
        IMDB_IDS.add("0903624");    // The Hobbit

        ACTOR_IDS.add("brad-pitt");

        FEATURED_IDS.add("yes");
        FEATURED_IDS.add("no");
    }

    /**
     * Test of getFilm method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFilm() throws TrailerAddictException {
        LOG.info("getFilm");

        for (String id : FILM_IDS) {
            List<Trailer> trailers = TrailerAddictApi.getFilm(id, NUMBER_OF_TRAILERS);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
            assertTrue(INCORRECT_NUMBER + id, trailers.size() == NUMBER_OF_TRAILERS);
        }
    }

    /**
     * Test of getFilm method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFilmWidth() throws TrailerAddictException {
        LOG.info("getFilmWidth");

        String id = FILM_IDS.get(0);
        List<Trailer> trailers = TrailerAddictApi.getFilm(id, NUMBER_OF_TRAILERS, REQUIRED_WIDTH);
        assertNotNull(TRAILERS_NULL + id, trailers);
        assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
        Trailer t = trailers.get(0);
        String embed = t.getEmbed(TrailerSize.CUSTOM);
        assertNotNull(NO_CUSTOM_TRAILER_SIZE + id, embed);
        assertTrue(INCORRECT_WIDTH + id, embed.contains(TEST_WIDTH_COMPARE));
    }

    /**
     * Test of getActor method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetActor() throws TrailerAddictException {
        LOG.info("getActor");

        for (String id : ACTOR_IDS) {
            LOG.debug("Actor: {}", id);
            List<Trailer> trailers = TrailerAddictApi.getActor(id, NUMBER_OF_TRAILERS);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
            assertTrue(INCORRECT_NUMBER + id, trailers.size() == NUMBER_OF_TRAILERS);
        }
    }

    /**
     * Test of getActor method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetActorWidth() throws TrailerAddictException {
        LOG.info("getActorWidth");

        String id = ACTOR_IDS.get(0);
        List<Trailer> trailers = TrailerAddictApi.getActor(id, NUMBER_OF_TRAILERS, REQUIRED_WIDTH);
        assertNotNull(TRAILERS_NULL + id, trailers);
        assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
        Trailer t = trailers.get(0);
        String embed = t.getEmbed(TrailerSize.CUSTOM);
        assertNotNull(NO_CUSTOM_TRAILER_SIZE + id, embed);
        assertTrue(INCORRECT_WIDTH + id, embed.contains(TEST_WIDTH_COMPARE));
    }

    /**
     * Test of getFilmImdb method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFilmImdb() throws TrailerAddictException {
        LOG.info("getFilmImdb");

        for (String id : IMDB_IDS) {
            List<Trailer> trailers = TrailerAddictApi.getFilmImdb(id, NUMBER_OF_TRAILERS);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
            assertTrue(INCORRECT_NUMBER + id, trailers.size() == NUMBER_OF_TRAILERS);
        }
    }

    /**
     * Test of getFilmImdb method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFilmImdbWidth() throws TrailerAddictException {
        LOG.info("getFilmImdbWidth");

        String id = IMDB_IDS.get(0);
        List<Trailer> trailers = TrailerAddictApi.getFilmImdb(id, NUMBER_OF_TRAILERS, REQUIRED_WIDTH);
        assertNotNull(TRAILERS_NULL + id, trailers);
        assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
        Trailer t = trailers.get(0);
        String embed = t.getEmbed(TrailerSize.CUSTOM);
        assertNotNull(NO_CUSTOM_TRAILER_SIZE + id, embed);
        assertTrue(INCORRECT_WIDTH + id, embed.contains(TEST_WIDTH_COMPARE));
    }

    /**
     * Test of getFeatured method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFeatured() throws TrailerAddictException {
        LOG.info("getFeatured");

        for (String id : FEATURED_IDS) {
            List<Trailer> trailers = TrailerAddictApi.getFeatured(id, NUMBER_OF_TRAILERS);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
            assertTrue(INCORRECT_NUMBER + id, trailers.size() == NUMBER_OF_TRAILERS);
        }
    }

    /**
     * Test of getFeatured method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetFeaturedWidth() throws TrailerAddictException {
        LOG.info("getFeatured");

        for (String id : FEATURED_IDS) {
            List<Trailer> trailers = TrailerAddictApi.getFeatured(id, NUMBER_OF_TRAILERS, REQUIRED_WIDTH);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());
            Trailer t = trailers.get(0);
            String embed = t.getEmbed(TrailerSize.CUSTOM);
            assertNotNull(NO_CUSTOM_TRAILER_SIZE + id, embed);
            assertTrue(INCORRECT_WIDTH + id, embed.contains(TEST_WIDTH_COMPARE));
        }
    }

    /**
     * Test of getSimpleApi method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Ignore("Tested elsewhere")
    public void testGetSimpleApiString() throws TrailerAddictException {
    }

    /**
     * Test of getSimpleApi method, of class TrailerAddictApi.
     *
     * @throws com.omertron.traileraddictapi.TrailerAddictException
     */
    @Test
    public void testGetSimpleApiTrailer() throws TrailerAddictException {
        LOG.info("getSimpleApi (via Trailer)");

        for (String id : FILM_IDS) {
            List<Trailer> trailers = TrailerAddictApi.getFilm(id, NUMBER_OF_TRAILERS);
            assertNotNull(TRAILERS_NULL + id, trailers);
            assertFalse(TRAILERS_EMPTY + id, trailers.isEmpty());

            // Just test the first trailer
            Trailer trailer = TrailerAddictApi.getSimpleApi(trailers.get(0));
            assertNotNull("Simple trailer is null for " + id, trailer);
            assertFalse("Simple trailer is empty", trailer.getEmbed().isEmpty());

            // Just test the first movie
            break;
        }

    }
}
