/*
 * Java core library component.
 *
 * Copyright (c) 1997, 1998
 *      Transvirtual Technologies, Inc.  All rights reserved.
 *
 * See the file "license.terms" for information on usage and redistribution
 * of this file.
 */

package java.util.zip;

public class Inflater {

  static {
    System.loadLibrary("zip");
  }

  private int strm;
  protected byte[] buf;
  protected int off;
  protected int len;
  private boolean finished;
  private boolean needsDictionary;

  public Inflater(boolean nowrap)
  {
    buf = null;
    off = 0;
    len = 0;
    finished = false;
    needsDictionary = false;
    init(nowrap);
  }

  public Inflater()
  {
    this(false);
  }

  public synchronized void setInput(byte b[], int o, int l)
  {
    buf = b;
    off = o;
    len = l;
  }

  public void setInput(byte b[])
  {
    setInput(b, 0, b.length);
  }

  public void setDictionary(byte b[])
  {
    setDictionary(b, 0, b.length);
  }

  public synchronized int getRemaining()
  {
    return (len);
  }

  public synchronized boolean needsInput()
  {
    if (len == 0 && !finished) {
      return (true);
    }
    else {
      return (false);
    }
  }

  public synchronized boolean needsDictionary()
  {
    return (needsDictionary);
  }

  public synchronized boolean finished()
  {
    return (finished);
  }

  public int inflate(byte b[]) throws DataFormatException
  {
    try {
      return (inflate(b, 0, b.length));
    }
    catch (NullPointerException _) {
      throw new DataFormatException("null buffer");
    }
  }

  protected void finalize()
  {
    end();
  }

  public native synchronized void setDictionary(byte b[], int off, int len);
  public native synchronized int inflate(byte b[], int off, int len) throws DataFormatException;
  public native synchronized int getAdler();
  public native synchronized int getTotalIn();
  public native synchronized int getTotalOut();
  public native synchronized void reset();
  private native synchronized void init(boolean nowrap);
  public native synchronized void end();

}
