/* SSLSocketFactory.java -- factory for SSL client sockets.
   Copyright (C) 2003  Casey Marshall <rsdio@metastatic.org>

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 2 of the License, or (at your
option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the

   Free Software Foundation, Inc.,
   59 Temple Place, Suite 330,
   Boston, MA  02111-1307
   USA

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under terms
of your choice, provided that you also meet, for each linked independent
module, the terms and conditions of the license of that module.  An
independent module is a module which is not derived from or based on
this library.  If you modify this library, you may extend this exception
to your version of the library, but you are not obligated to do so.  If
you do not wish to do so, delete this exception statement from your
version.  */


package javax.net.ssl;

import java.io.IOException;
import java.net.Socket;
import java.security.Security;
import javax.net.SocketFactory;

public abstract class SSLSocketFactory extends SocketFactory
{

  // Constructor.
  // -----------------------------------------------------------------

  public SSLSocketFactory()
  {
    super();
  }

  // Class methods.
  // -----------------------------------------------------------------

  public static synchronized SocketFactory getDefault()
  {
    try
      {
        String s = Security.getProperty("ssl.SocketFactory.provider");
        if (s != null)
          return (SocketFactory) Class.forName(s).newInstance();
      }
    catch (Exception e)
      {
      }
    throw new Error();
    //return new TLSSocketFactory();
  }

  // Abstract methods.
  // -----------------------------------------------------------------

  public abstract Socket createSocket(Socket socket, String host,
                                      int port, boolean autoClose)
    throws IOException;

  public abstract String[] getDefaultCipherSuites();

  public abstract String[] getSupportedCipherSuites();
}