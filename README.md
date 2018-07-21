# PinEditTextField for Android

 [ ![Download](https://api.bintray.com/packages/poovamraj/Android-Pin-Field/PinEditTextField/images/download.svg) ](https://bintray.com/poovamraj/Android-Pin-Field/PinEditTextField/_latestVersion)
 
This repository contains `PinEditTextField` that provides Pin Field widget for android with `Paste Functionality`
which no other library provides.


![PinEntryEditText](http://i.giphy.com/3o7qDVApSTFl8DCU4E.gif)

## Setup

**Gradle**

- **Project level `build.gradle`**
```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```
- **App level `build.gradle`**
```gradle
dependencies {
    implementation 'com.poovam:pin-edittext-field:1.0.1'
}
```

### Usage

```xml

```

### Listen 

```java
final LinePinField linePinField = findViewById(R.id.lineField);
linePinField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
    @Override
    public void onTextComplete(@NotNull String enteredText) {
        Toast.makeText(MainActivity.this,enteredText,Toast.LENGTH_SHORT).show();
    }
});
```

## Features

- Allow your users to paste the characters into your Pin Field which no other library provides
- Configure 3 different types of Pin Field Views to your app.
- Customize the number of fields you will be requiring.
- Highly configurable with many attributes for your View.
- Use any type of keyboard you would like for the View.

License
=======

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
