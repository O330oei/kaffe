<?xml version="1.0" encoding="utf-8"?>

<!-- outputfiles.xsl
     Copyright (C) 2003 Free Software Foundation, Inc.
     
     This file is part of GNU Classpath.
     
     GNU Classpath is free software; you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation; either version 2, or (at your option)
     any later version.
      
     GNU Classpath is distributed in the hope that it will be useful, but
     WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
     General Public License for more details.
     
     You should have received a copy of the GNU General Public License
     along with GNU Classpath; see the file COPYING.  If not, write to the
     Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
     02111-1307 USA.
     -->

<!-- Returns a list of files generated by this stylesheet complex.
     -->

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:gjdoc="http://www.gnu.org/software/cp-tools/gjdocxml"
  xmlns:html="http://www.w3.org/TR/REC-html40"
  xmlns="http://www.gnu.org/software/cp-tools/gjdocxslfiles">

  <xsl:output method="xml"
    encoding="utf8"
    indent="no"/>

  <xsl:strip-space elements="*"/>

  <xsl:template match="/">
    <doctranslet>

      <name>GNU Gjdoc HTML DocTranslet</name>

      <version>0.6.1</version>

      <outputtype>
        <name>HTML</name>
      </outputtype>

      <outputfile>
        <name><xsl:text>index.html</xsl:text></name>
        <type><xsl:text>index</xsl:text></type>
        <sheet><xsl:text>index.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML Index file</xsl:text></comment>
      </outputfile>
      
      <outputfile>
        <name><xsl:text>index_noframes.html</xsl:text></name>
        <type><xsl:text>index_noframes</xsl:text></type>
        <sheet><xsl:text>index_noframes.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML Index file (No Frames)</xsl:text></comment>
      </outputfile>
      
      <outputfile>
        <name><xsl:text>descriptor.xml</xsl:text></name>
        <type><xsl:text>descriptor</xsl:text></type>
        <sheet><xsl:text>descriptor.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML Descriptor file</xsl:text></comment>
      </outputfile>
      
      <outputfile>
        <name><xsl:text>allclasses.html</xsl:text></name>
        <type><xsl:text>allclasses</xsl:text></type>
        <sheet><xsl:text>allclasses.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML All Classes file</xsl:text></comment>
      </outputfile>
      
      <outputfile>
        <name><xsl:text>allpackages.html</xsl:text></name>
        <type><xsl:text>allpackages</xsl:text></type>
        <sheet><xsl:text>allpackages.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML All Packages file</xsl:text></comment>
      </outputfile>

      <outputfile>
        <name><xsl:text>fulltree.html</xsl:text></name>
        <type><xsl:text>fulltree</xsl:text></type>
        <sheet><xsl:text>fulltree.xsl</xsl:text></sheet>
        <comment><xsl:text>HTML Tree file</xsl:text></comment>
      </outputfile>

      <xsl:for-each select="gjdoc:rootdoc/gjdoc:packagedoc">
        <outputfile>
          <name><xsl:value-of select="concat(translate(@name,'.','/'),'/package-summary.html')"/></name>
          <type><xsl:text>package</xsl:text></type>
          <info><xsl:value-of select="@name"/></info>
          <sheet><xsl:text>packagedoc.xsl</xsl:text></sheet>
          <comment><xsl:text>HTML Documentation for package </xsl:text><xsl:value-of select="@name"/></comment>
        </outputfile>
      </xsl:for-each>

      <xsl:for-each select="gjdoc:rootdoc/gjdoc:classdoc">
        <outputfile>
          <name><xsl:value-of select="concat(translate(gjdoc:containingPackage/@name,'.','/'),'/',@name,'.html')"/></name>
          <type><xsl:text>class</xsl:text></type>
          <info><xsl:value-of select="@qualifiedtypename"/></info>
          <sheet><xsl:text>classdoc.xsl</xsl:text></sheet>
          <comment><xsl:text>HTML Documentation for </xsl:text><xsl:value-of select="@qualifiedtypename"/></comment>
        </outputfile>
      </xsl:for-each>

    </doctranslet>
  </xsl:template>

</xsl:stylesheet>
