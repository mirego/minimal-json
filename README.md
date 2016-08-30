minimal-json (fork)
===================

This is a fork of [minimal-json](https://github.com/ralfstx/minimal-json) with somehow extreme optimizations added specically for usage in mobile applications. 

Mobile application (android) are typically given relatively small heap size. The temporary memory footprint of parsing json as significant impacts, as using too much memory force heavy garbage collector activity. On a typical backend you'd allocate >500mb of heap, allowing the parser to temporarly allocate over 20mb for parsing a single large json file without impacts. Mobile apps can be limited to around 70mb, with views and images already in heap, 20mb is simply too much, forcing a lot a gc activity. Mobile being all about smooth UI activity, such pressure on the heap gives an unpleasant feeling.

The main optimization is the reuse of strings. The typical approach of parsing json is to use a char array as input buffer, browser through to find token and accumulate values in outpout buffer and then convert the output to String and other types. We you have a large json files that is an array of the same objects, you end with a new String instance for all equivalent property names (and repetitive values). This version keeps produced String and reuse them instead of making a new one.

Also, it will avoid going through a String to parse numbers and boolean.

**Could leak** if using in a longer lived context or if there is too much json of different nature. It keeps a static map of property name strings.

Usage
-----

Add the following dependency to your build.gradle file:

```groovy
repositories {
    maven {
    	url 'https://mirego.bintray.com/public'
    }
}

dependencies {
	compile 'com.eclipsesource.minimal-json.mirego:minimal-json:0.1'
	
}
```

