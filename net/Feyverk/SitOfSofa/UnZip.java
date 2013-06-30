/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author Пётр
 */
public class UnZip
{

    private static void write(InputStream in, OutputStream out)
            throws IOException
    {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
        {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    public static boolean Unzip(String Arhive, String Out, String FileName)
    {
        Enumeration entries;
        ZipFile zip;
        try
        {
            zip = new ZipFile(Arhive);
            entries = zip.entries();

            while (entries.hasMoreElements())
            {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.getName().compareTo(FileName) == 0)
                {
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(Out + "/" + entry.getName())));
                    break;
                }
            }

            zip.close();
        } catch (IOException e)
        {
            LOG.warning(e.getStackTrace().toString());
            return false;
        }
        return true;
    }
    private static final Logger LOG = Logger.getLogger(UnZip.class.getName());
}
