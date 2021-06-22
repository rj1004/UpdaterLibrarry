# UpdaterLibrarry
[![](https://jitpack.io/v/rj1004/UpdaterLibrarry.svg)](https://jitpack.io/#rj1004/UpdaterLibrarry)

How to
To get a Git project into your build:

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
