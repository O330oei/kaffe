/*
 * Java core library component.
 *
 * Copyright (c) 1997, 1998
 *      Transvirtual Technologies, Inc.  All rights reserved.
 *
 * See the file "license.terms" for information on usage and redistribution
 * of this file.
 */

package java.io;


public class FileOutputStream
  extends OutputStream {

static {
        System.loadLibrary("io");
}

private FileDescriptor fd = new FileDescriptor();

public FileOutputStream(File file) throws IOException
{
	this(file.getPath());
}

public FileOutputStream(FileDescriptor fdObj)
	{
	final SecurityManager sm = System.getSecurityManager();
	if (sm != null)
		sm.checkWrite(fdObj);
	fd = fdObj;
}

public FileOutputStream(String name) throws FileNotFoundException
{
	this(name, false);
}

public FileOutputStream(String name, boolean append) throws FileNotFoundException
{
	final SecurityManager sm = System.getSecurityManager();
	if (sm != null)
		sm.checkWrite(name);
	if (!append) {
		open(name);
	}
	else {
		openAppend(name);
	}
}

native public void close() throws IOException;

protected void finalize() throws IOException
{
	close();
	try {
	    super.finalize();
	}
	catch(Throwable e){
	    throw new IOException(e.getMessage());
	}
}

final public FileDescriptor getFD()  throws IOException
{
	return (fd);
}

native private void open(String name);

native private void openAppend(String name);

public void write(byte b[]) throws IOException
{
	write(b, 0, b.length);
}

public void write(byte b[], int off, int len) throws IOException
{
	writeBytes(b, off, len);
}

native public void write(int b) throws IOException;

native private void writeBytes(byte b[], int off, int len);
}
