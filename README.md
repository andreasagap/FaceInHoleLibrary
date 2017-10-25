# FaceInHoleLibrary


## Preview
<img src="/screenshot.png" width="240">


## Setup

### Gradle

Add this to your project level `build.gradle`:


```groovy
allprojects {
  repositories {
    jcenter()
    maven { url 'https://jitpack.io' }
  }
}
```


Add this to your app `build.gradle` :

```groovy
dependencies {
  compile 'com.github.andreasagap:FaceInHoleLibrary:v1.0'
}
```

## Usage

##### Via XML
Here's a basic implementation.
```xml
<andreas.faceinhole.Faceinhole
  android:id="@+id/view"
  android:layout_width="match_parent"
  android:layout_height="match_parent" />
```


In your code you can use the library like:
```groovy
Faceinhole faceinhole =(Faceinhole) findViewById(R.id.view);
faceinhole.setImages(face,body);
ImageView YourImageView=(ImageView) findViewById(R.id.yourimageview);
YourImageView.setImageBitmap(faceinhole.mergeImages());
```

For take your drawable from the folder you can use :
```groovy
getResources().getDrawable(R.drawable.face)
```
*The function setImages() takes for arguments Bitmap or Drawable.*

**You can handle the face image with these instruction**

<img src="https://image.slidesharecdn.com/android-150224085125-conversion-gate02/95/android-ui-guide-4-638.jpg?cb=1424768120" width="240">


## Author
Andreas Agapitos ([@andreasagap](https://github.com/andreasagap))


## Contributing
If you find any bugs, please feel free to fix it and send a pull request or [open an issue.](https://github.com/andreasagap/FaceInHoleLibrary/issues) 
