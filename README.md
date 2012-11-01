API-TrailerAddict
=================

Author: Stuart Boston (Omertron AT Gmail DOT com)

API for the Trailer Addict website
http://www.traileraddict.com/trailerapi

There are five methods supported
* Featured
* Film
* Actor
* IMDB
* Simple-API

__Featured__: Yes or No.
This will list all trailers TrailerAddict deems popular or important.
If featured is set to 'no', then the most recent trailer additions will be listed.

__Film__:
Want to list trailers by a particular film? Then you need the film keyword. To find this, just go to the film's page on TrailerAddict. Assuming you want all the trailers for Max Payne, you'd go to the film's page and, in the URL, you'll see: traileraddict.com/tags/max-payne. The keyword for Max Payne is max-payne.

__Actor__:
Want to list trailers only by a particular actor? Then you need the actor's keyword. To find this, go to the actor's page on TrailerAddict. Assuming you want all the trailers with Brad Pitt in them, you'd go to his page and, in the URL, you'll see: traileraddict.com/tags/brad-pitt. The keyword for Brad Pitt is brad-pitt

__IMDB__:
The same as Film, except the key is the IMDB ID instead of the TrailerAddict key.

__Simple-API__:
Provides more detailed information about a trailer, such as director, writer, cast, etc.
