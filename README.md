Lists and DBs
====

A small `multi-module` project that displays all Jake Wharton's github repos, and stores them
in a local db for offline reuse.

The project uses Google's [Room][room] for the interaction with the database, and is designed to follow
 the MVI architecture pattern.
 
### Project Structure

* `:app` - Application module.
* `:service:github` - Contains network service logic.
* `:service:local` - Contains db service logic.
* `service:common` - Contains common models used by `:app` to display the list.

[room]: https://developer.android.com/topic/libraries/architecture/room.html
