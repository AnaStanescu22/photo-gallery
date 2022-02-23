# Photo-gallery

This sample Kotlin app retrieves images from the internal storage of the phone.  
The app displays the content with `RecyclerView` and uses a traditional adapter.

The `GalleryViewModel` is in charge of preparing and altering the UI state for the `MainActivity`. The activity listens for state changes through Flow observing.

For image loading, the app uses Coil.
