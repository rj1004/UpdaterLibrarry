# UpdaterLibrarry
[![](https://jitpack.io/v/rj1004/UpdaterLibrarry.svg)](https://jitpack.io/#rj1004/UpdaterLibrarry)


# Install Updater App From Appstore to give Remote Features to Your App.

https://rahulcompany.herokuapp.com/



How to

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.rj1004:UpdaterLibrarry:Tag'
	}

Step 3. Add Following Permissions to Manifest file

	<uses-permission android:name="android.permission.INTERNET"/>
    	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
	
Step 4. Add Code To Activity Which You Want to Check And Update Your App

	new Updater(MainActivity.this, "hi", MainActivity.this).fetch();
    
