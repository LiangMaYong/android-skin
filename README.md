# android-skin
this is android skin

[![LatestVersion](https://img.shields.io/badge/LatestVersion-1.1.0-brightgreen.svg?style=plastic) ](https://github.com/LiangMaYong/android-skin/releases/tag/V1.1.0)

## gradle
```
compile 'com.liangmayong.android:skin:$LatestVersion'
```
## skin view
SkinView

SkinButton

SkinTextView

SkinRelativeLayout

SkinLinearLayout
##　setting skin
```
Skin.editor().setThemeColor(0xff3399ff, 0xffffffff).commit();
```

## styleable
```
<declare-styleable name="SkinStyleable">
<attr name="pressed_color" format="color" />
<attr name="pressed_alpha" format="integer" />
<attr name="stroke_width" format="dimension" />
<attr name="radius" format="dimension" />
<attr name="shape_type" format="enum">
    <enum name="round" value="0" />
    <enum name="rectangle" value="1" />
    <enum name="stroke" value="2" />
    <enum name="oval" value="3" />
</attr>
<attr name="skin_type" format="enum">
    <enum name="defualt" value="0" />
    <enum name="primary" value="1" />
    <enum name="success" value="2" />
    <enum name="info" value="3" />
    <enum name="warning" value="4" />
    <enum name="danger" value="5" />
    <enum name="white" value="6" />
</attr>
</declare-styleable>
```
##License
```
Copyright 2016 LiangMaYong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
