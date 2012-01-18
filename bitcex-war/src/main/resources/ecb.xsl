<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:gesmes="http://www.gesmes.org/xml/2002-08-01" 
    xmlns:tns="http://www.ecb.int/vocabulary/2002-08-01/eurofxref">
    <xsl:output indent="yes" encoding="UTF-8"/>
    <xsl:output exclude-result-prefixes="#all"/>
    <xsl:output />   
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="gesmes:subject"/>
    <xsl:template match="gesmes:Sender"/>

    <xsl:template match="tns:Cube[@time]">
        <ecb xmlns="http://bitcex.org/rates">
            <xsl:attribute name="timestamp">
                <xsl:value-of select="@time"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </ecb>
    </xsl:template>

    <xsl:template match="tns:Cube[@currency]">
        <xsl:variable name="name"><xsl:value-of select="@currency"/></xsl:variable>
        <xsl:element name="{$name}">
            <xsl:value-of select="@rate"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>