# MyQueue [![MIT license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://raw.githubusercontent.com/qirh/MyQueue/master/LICENSE)

Group project for CS 371M: Mobile Development (Android) at UT Austin. 

Android app that displays media information for multiple streaming services in one place

Team: [Kaivan](https://github.com/kaivan29), [Erin](https://github.com/erinjensby) & [me](https://github.com/qirh)

## App
### Requirments
  * Android phone with at least android 4.0 should run fine. App is compiled with a target apk for android 6.0
  * Active internet connection
  
### The problems we try to address
  1. It is hard to search for a Movie/TV show in multiple services. As the user would have to log in to each service to search
  2. Some streaming services like Netflix do not allow the user to search their databses unless subscribed
  3. These services do not provide a Queue/Favorites feature for the media items that are not tn their platforms
  
### How we solve them
  1. Using third party API's, we search across all [supported servcies](#Supported-Services)
  2. Also, we offer the ability to search all [supported servcies](#Supported-Services) without subscribing
  3. Our app also allows the user to queue movies and shows, voila that’s why the name myQueue! 
  
  
**For more information** check out our [Design doc](https://github.com/qirh/MyQueue/blob/master/MyQueue.pdf)

**The [apk](https://github.com/qirh/MyQueue/blob/master/app-debug.apk)** is now included in the repo, please download it and let us know what you think!!

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


## Screenshots
### User use cases
<img src="/screenshots/wireframe.png" height="450" width="270" style="border:0.2px solid grey">

### Home Page and Select Sources
<img src="/screenshots/home_page.png" height = "450" width="270"> <img src="/screenshots/select_sources.png" height = "450" width="270">

### Browse movies and series
<img src="/screenshots/browse_netflix.png" height = "450" width="270"> <img src="/screenshots/browse_hbo.png" height = "450" width="270"> <img src="/screenshots/browse_series.png" height = "450" width="270">

### Myqueue
<img src="/screenshots/myqueue.png" height = "450" width="270"> <img src="/screenshots/remove.png" height = "450" width="270">
