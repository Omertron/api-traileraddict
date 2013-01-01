/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
