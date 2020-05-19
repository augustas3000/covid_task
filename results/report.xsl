<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Highest Death Toll for 15 selected countries, from the last 10 days data</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th style="text-align:left">Country Name</th>
                        <th style="text-align:left">Date</th>
                        <th style="text-align:left">DeathToll</th>
                    </tr>

                    <xsl:for-each select="selected-5-countries-10-days-report/country">
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="daily-data/date"/></td>
                            <td><xsl:value-of select="totalDeaths"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>