<?xml version='1.0' encoding='ISO-8859-1' ?>
<helpset version="1.0">
  <title>Basic Layout Example - Help</title>
  <maps>
    <homeID>top</homeID>
    <mapref location="Layout.jhm"/>
  </maps>
  <view>
    <name>TOC</name>
    <label>Table Of Contents</label>
    <type>javax.help.TOCView</type>
    <data>LayoutTOC.xml</data>
  </view>
  <view>
    <name>Index</name>
    <label>Help Index</label>
    <type>javax.help.IndexView</type>
    <data>LayoutIndex.xml</data>
  </view>
  <view>
    <name>Search</name>
    <label>Search</label>
    <type>javax.help.SearchView</type>
    <data>JavaHelpSearch</data>
  </view>
</helpset>

