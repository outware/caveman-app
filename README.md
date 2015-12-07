Caveman
=======

The purpose of this application is to allow you to create and modify configuration environments for android applications at run time. 

This serves a similar purpose to setting build variables for different variants, however the benefit of this approach is that you can use one application for all environments instead of one application per build variant. 
Another benefit is that values can be updated without requiring a new build, which is particularly useful as this means values can be updated by testers, product managers etc.

Usage
-----
1. Fork this repository and include it as a submodule of your existing Android project using `git submodule add <repository url>`. 
2. (Optional) If you intend to use multiple Cavemen on one device, update the application id in `build.gradle`, as well as the permission name and provider authority in `AndroidManifest.xml`
3. Configure your environment variables. The variable definitions can be found in `res/raw/configuration.json` and the default environments and values can be found in `res/raw/environment.json`. 
Caveman supports the Number, String and Boolean JSON types for properties
4. Add `classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'` to your project's buildscript dependencies
4. Add `compile 'au.com.outware:caveman:<version>'` to your application's dependencies and add the `<provider authority>.ACCESS_DATA` permision to your manifest, where <provider authority> matches the authority specified in step 2, or `au.com.outware.caveman` otherwise.
5. Instantiate the `EnvironmentContentProviderRepository`, supplying a context and the provider authority (specified in Caevman's manifest) and use the `getProperty` method to retrieve your properties as necessary
	
License
-------
		The MIT License (MIT)
		
		Copyright (c) 2015 Outware Mobile
		
		Permission is hereby granted, free of charge, to any person obtaining a copy
		of this software and associated documentation files (the "Software"), to deal
		in the Software without restriction, including without limitation the rights
		to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
		copies of the Software, and to permit persons to whom the Software is
		furnished to do so, subject to the following conditions:
		
		The above copyright notice and this permission notice shall be included in all
		copies or substantial portions of the Software.
		
		THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
		IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
		FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
		AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
		LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
		OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
		SOFTWARE.