package org.dasein.cloud.aws;

import org.dasein.cloud.CloudException;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Eugene Yaroslavtsev
 * @since 11.09.2014
 */
public class AWSCloudTests {
    private static DocumentBuilder documentBuilder;

    static {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch( ParserConfigurationException e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTimestampValue() throws Exception {
        Element node =  documentBuilder
                .parse(new ByteArrayInputStream("<node>2014-07-01T15:08:28Z</node>".getBytes()))
                .getDocumentElement();

        Element nodeWithMs =  documentBuilder
                .parse(new ByteArrayInputStream("<node>2014-07-01T15:08:28.001Z</node>".getBytes()))
                .getDocumentElement();

        assertEquals(1404227308000L, AWSCloud.getTimestampValue(node));
        assertEquals(1404227308001L, AWSCloud.getTimestampValue(nodeWithMs));
    }

    @Test
    public void negativeTestGetTimestampValue() throws Exception {
        Element brokenNode =  documentBuilder
                .parse(new ByteArrayInputStream("<node>2014-07-01T15:08:28.001.0Z</node>".getBytes()))
                .getDocumentElement();

        Element secondBrokenNode =  documentBuilder
                .parse(new ByteArrayInputStream("<node>2014-07-01T</node>".getBytes()))
                .getDocumentElement();

        waitExceptionForGetTimestampValue(brokenNode);
        waitExceptionForGetTimestampValue(secondBrokenNode);
    }

    private static void waitExceptionForGetTimestampValue( Node node ) {
        try {
            AWSCloud.getTimestampValue(node);
            throw new AssertionError();
        } catch( CloudException ex ) {
            //expected exception
        }
    }
}
