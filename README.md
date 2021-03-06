# MyQueue [![MIT license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://raw.githubusercontent.com/qirh/MyQueue/master/LICENSE)

Android app that displays media information (Movies/TV shows) for multiple [streaming services](#supported-services) in one place.

Originally made for CS 371M: Mobile Development (Android) at UT Austin, by [Kaivan](https://github.com/kaivan29), [Erin](https://github.com/erinjensby) & [Saleh](https://github.com/qirh).

Check out the [design doc](https://github.com/qirh/MyQueue/blob/master/MyQueue.pdf) for a light read and download the [apk](https://github.com/qirh/MyQueue/blob/master/app-debug.apk).

## Summary
**Requirement:** Android phone with at least android 4.0. Active internet connection. Valid email address.

### El problema
  * It is hard to search for a Movie/TV show in multiple streaming services at the time. You'd have to log in to each service to search.
  * Some streaming services like Netflix restrict searching for only subscribed users.
  * These services do not provide the ability to Queue/Favorites/Watchlist media items do not exist not in their platforms

### Solution
  * Using third party API's, search across all [supported services](#supported-services), and return the results in one place.
  * Also allow the user to queue media items, if they exist in any of the [supported services](#supported-services).

### Supported Services
Netflix, HBO GO, Hulu & Amazon Prime

### Copyright information
* API Used: [themoviedb.org](themoviedb.org) & [guidebox.com](guidebox.com)
* Icons: [icons8.com](icons8.com)


## Use Case
<table><tr><td>
<img src="/screenshots/wireframe.png" height="450" width="270">
</td></tr></table>

## Screenshots
### Home Page and Select Sources
<table><tr><td><img src="/screenshots/home_page.png" height = "450" width="270"></td> <td><img src="/screenshots/select_sources.png" height = "450" width="270"></td></tr></table>

### Browse movies and series
<table><tr><td><img src="/screenshots/browse_netflix.png" height = "450" width="270"></td> <td><img src="/screenshots/browse_hbo.png" height = "450" width="270"></td> <td><img src="/screenshots/browse_series.png" height = "450" width="270"></td></tr></table>

### MyQueue
<table><tr><td><img src="/screenshots/myqueue.png" height = "450" width="270"> <img src="/screenshots/remove.png" height = "450" width="270"></td></tr></table>
