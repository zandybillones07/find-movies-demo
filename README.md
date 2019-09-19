# find-movies-demo
This app is for code exam purposes only. What it does is to list all items specified in parameters. 

Architecture, Persistence and libraries:
- The design pattern that i use for this demo is MVVM (arch version 2.1.0). Instead of using dagger and rxjava, I decided to make it basic and simple. I use retrofit for REST handling, picasso for rendering images. 
- There are many ways to persist the data like storing sqlite or using Room, etc.. But I'd simply made a minor tweak on retrofit and cache the data through Context.cacheDir.

Thank you!
