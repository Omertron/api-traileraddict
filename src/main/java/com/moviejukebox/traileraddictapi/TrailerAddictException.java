/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.traileraddictapi;

public class TrailerAddictException extends Exception {

    private static final long serialVersionUID = -8952129102483143278L;

    public enum TrailerAddictExceptionType {
        UNKNOWN_CAUSE, INVALID_URL, HTTP_404_ERROR, ID_NOT_FOUND, PARSE_ERROR, CONNECTION_ERROR, INVALID_IMAGE;
    }

    private final TrailerAddictExceptionType exceptionType;
    private final String response;

    public TrailerAddictException(final TrailerAddictExceptionType exceptionType, final String response) {
        super();
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public TrailerAddictException(final TrailerAddictExceptionType exceptionType, final String response, final Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public TrailerAddictExceptionType getExceptionType() {
        return exceptionType;
    }

    public String getResponse() {
        return response;
    }
}
