# FaceInHoleLibrary



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


## Author
Andreas Agapitos ([@andreasagap](https://github.com/andreasagap))


## Contributing
If you find any bugs, please feel free to fix it and send a pull request or [open an issue.](https://github.com/andreasagap/FaceInHoleLibrary/issues) 
