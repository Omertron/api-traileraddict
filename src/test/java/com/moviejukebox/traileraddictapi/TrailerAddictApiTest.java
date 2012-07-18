/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviejukebox.traileraddictapi;

import com.moviejukebox.traileraddictapi.model.Trailer;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author stuart.boston
 */
public class TrailerAddictApiTest {

    private static final Logger LOGGER = Logger.getLogger(TrailerAddictApiTest.class);

    public TrailerAddictApiTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Set the logger level to TRACE
        Logger.getRootLogger().setLevel(Level.TRACE);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFilm method, of class TrailerAddictApi.
     */
    @Test
    public void testGetFilm() throws TrailerAddictException {
        LOGGER.info("getFilm");
//        String filmId = "blade-runner";
        String filmId = "the-hobbit";
        List<Trailer> trailers = TrailerAddictApi.getFilm(filmId, 4);

        for (Trailer t : trailers) {
            LOGGER.info(t.getTitle() + " - " + t.getTrailerId());
        }

        assertNotNull("List of trailers is null", trailers);
        assertFalse("List of trailers is empty", trailers.isEmpty());
    }
}
