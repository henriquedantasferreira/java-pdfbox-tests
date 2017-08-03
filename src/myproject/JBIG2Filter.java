package myproject;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSDictionary;


public class JBIG2Filter implements Filter
{

    /** Log instance */
    private static final Log log = LogFactory.getLog(JBIG2Filter.class);
  
    /**
     * Decode JBIG2 data using Java ImageIO library.
     * 
     * {@inheritDoc}
     * 
     */
    public void decode( InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex ) 
        throws IOException
    {
        BufferedImage bi = ImageIO.read( compressedData );
        if ( bi != null )
        {
            DataBuffer dBuf = bi.getData().getDataBuffer();
            if ( dBuf.getDataType() == DataBuffer.TYPE_BYTE )
            {
                result.write( ( ( DataBufferByte ) dBuf ).getData() );
            }
            else
            {
                log.error( "Image data buffer not of type byte but type " + dBuf.getDataType() );
            }
        }
    }

     /**
     * {@inheritDoc}
     */
    public void encode( InputStream rawData, OutputStream result, COSDictionary options, int filterIndex ) 
        throws IOException
    {
        System.err.println( "Warning: JBIG2.encode is not implemented yet, skipping this stream." );
    }

	@Override
	public boolean isLoggable(LogRecord record) {
		// TODO Auto-generated method stub
		return false;
	}
}
