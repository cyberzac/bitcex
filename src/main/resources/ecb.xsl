<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output indent="yes" encoding="UTF-8"/>
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="/*:Envelope">      
            <xsl:apply-templates/>       
    </xsl:template>
    
    <xsl:template match="*:subject"/>
    <xsl:template match="*:Sender"/>
    
    <xsl:template match="*:Cube[@time]">
        <ecb>
            <xsl:attribute name="timestamp">
                <xsl:value-of select="@time"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </ecb>
    </xsl:template>

    <xsl:template match="*:Cube[@currency]">
        <xsl:variable name="name"><xsl:value-of select="@currency"/></xsl:variable>
        <xsl:element name="{$name}">
            <xsl:value-of select="@rate"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>