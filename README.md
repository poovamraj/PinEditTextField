# PinEditTextField for Android

 [ ![Download](https://api.bintray.com/packages/poovamraj/Android-Pin-Field/PinEditTextField/images/download.svg) ](https://bintray.com/poovamraj/Android-Pin-Field/PinEditTextField/_latestVersion)[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-PinEditTextField-green.svg?style=flat )]( https://android-arsenal.com/details/1/7051 )
 
This repository contains `PinEditTextField` that provides Pin Field widget for android with `Paste Functionality`
which no other library provides.


![PinEntryEditText](https://media.giphy.com/media/1rL2WFYucy6AF1Y4L4/giphy.gif)

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
    implementation 'com.poovam:pin-edittext-field:1.1.2'
}
```

### Usage

```xml
    <com.poovam.pinedittextfield.LinePinField
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="text"
        android:textSize="16sp"                                              
        android:textSelectHandle="@drawable/text_handle" // recommended
        app:noOfFields="4"              
        app:distanceInBetween="10dp"  // custom distance can be provided in between fields (applicable to all types of Pin Fields)                                               
        app:fieldColor="@color/colorPrimary"  // custom color can be provided (applicable to all types of Pin Fields)
        app:highlightColor="@color/colorAccent" // custom color can be provided (applicable to all types of Pin Fields)
        app:highlightEnabled="true" // highlighting can be enabled or disabled (applicable to all types of Pin Fields)
        app:lineThickness="5dp" // line thickness can be provided (applicable to all types of Pin Fields)                                              
        app:isCustomBackground="true" // to be set to true when background is set (applicable to all types of Pin Fields)
        app:highlightSingleFieldMode="true" // only the active field is highlighted (applicable to all types of Pin Fields)
        app:isCursorEnabled="true" // to be set to true if cursor is needed (applicable to only Square and Line Pin Fields)
        app:bottomTextPaddingDp="15dp" // distance between line and the text (applicable only for Line Pin Fields)
        android:background="@color/colorPrimary"
        android:id="@+id/lineField"/>

    <com.poovam.pinedittextfield.SquarePinField
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="text"
        android:textSize="16sp"
        app:cornerRadius="10dp" // Will make the square curved on the edges (applicable only for Square Pin Fields)
        app:noOfFields="4"                                                
        android:textSelectHandle="@drawable/text_handle" // recommended
        android:id="@+id/squareField"
        android:layout_marginTop="15dp"/>

    <com.poovam.pinedittextfield.CirclePinField
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="text"
        app:noOfFields="4"                                                
        android:textSize="16sp"
        android:textSelectHandle="@drawable/text_handle" // recommended
        app:circleRadius="15dp" // radius of the circle  (applicable only to Circle Pin Field)                         
        app:fillerRadius="2dp" // radius of the inside circle shown when text is entered (applicable only to Circle Pin Field)  
        app:fillerColor="@color/colorPrimary" // color that can be provided inside circle  (applicable only to Circle Pin Field)
        android:id="@+id/circleField"/>
```

### Listen to your Pin Field

```java
final LinePinField linePinField = findViewById(R.id.lineField);
linePinField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
    @Override
    public boolean onTextComplete (@NotNull String enteredText) {
        Toast.makeText(MainActivity.this,enteredText,Toast.LENGTH_SHORT).show();
        return true; // Return true to keep the keyboard open else return false to close the keyboard
    }
});
```

## Features

- Allow your users to paste the characters into your Pin Field which no other library provides
- Configure 3 different types of Pin Field Views to your app.
- Customize the number of fields you will be requiring.
- Highly configurable with many attributes for your View.
- Use any type of keyboard you would like for the View.
- Customize the distance between your Pin Fields.
- Enable or disable cursor to your Pin Fields.
- Highlight only the currently active Field or the whole Pin Field.
- Keep the keyboard open or closed after the Text is entered.
- Curve the edges in your Square Pin Field view.
- Set custom distance between your line and text Line Pin Field.
- Mask your passwords properly.
- Support for showing hint in your Pin Fields

## Example

![PinEntryEditText](https://ibin.co/4KY3vBnVXxtc.jpg)

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
