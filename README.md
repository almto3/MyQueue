# MyQueue [![MIT license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://raw.githubusercontent.com/qirh/MyQueue/master/LICENSE)

Android app that displays media information (Movies/TV shows) for multiple [streaming services](#supported-services) in one place.

Originally made for CS 371M: Mobile Development (Android) at UT Austin, by [Kaivan](https://github.com/kaivan29), [Erin](https://github.com/erinjensby) & [Saleh](https://github.com/qirh).

Check out the [design doc](https://github.com/qirh/MyQueue/blob/master/MyQueue.pdf) for a light read and download the [apk](https://github.com/qirh/MyQueue/blob/master/app-debug.apk).

## Summary
### Requirements
  * Android phone with at least android 4.0 should run fine. App is compiled with a target apk for android 6.0.
  * Active internet connection.
  * Valid email address.

### The problems
  1. It is hard to search for a Movie/TV show in multiple streaming services at the time. You'd have to log in to each service to search.
  1. Some streaming services like Netflix restrict searching for only subscribed users.
  1. These services do not provide the ability to Queue/Favorites/Watchlist media items do not exist not in their platforms

### Solutions
  1. Using third party API's, search across all [supported services](#supported-services), and return the results in one place.
  1. Also allow the user to queue media items, if they exist in any of the [supported services](#supported-services).


**For more information**

**** is now included in the repo, please download it and let us know what you think!!

### Supported Services
Netflix, HBO GO, Hulu & Amzaon Prime

### Future Releases
* Sorting media items by category
* Browse function being able to show multiple sources at once
* Detailed search through media items
* More details about the Movie or TV show and Trailer playing in the app.


### Copyright information
* API Used: [themoviedb.org](themoviedb.org) & [guidebox.com](guidebox.com)
* Icons: [icons8.com](icons8.com)


## User use case
<table><tr><td>
<img src="/screenshots/wireframe.png" height="450" width="270">
</td></tr></table>

## Screenshots
### Home Page and Select Sources
<table><tr><td><img src="/screenshots/home_page.png" height = "450" width="270"></td> <td><img src="/screenshots/select_sources.png" height = "450" width="270"></td></tr></table>

### Browse movies and series
<table><tr><td><img src="/screenshots/browse_netflix.png" height = "450" width="270"></td> <td><img src="/screenshots/browse_hbo.png" height = "450" width="270"></td> <td><img src="/screenshots/browse_series.png" height = "450" width="270"></td></tr></table>

### Myqueue
<table><tr><td><img src="/screenshots/myqueue.png" height = "450" width="270"> <img src="/screenshots/remove.png" height = "450" width="270"></td></tr></table>
