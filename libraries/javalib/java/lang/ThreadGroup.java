/*
 * Java core library component.
 *
 * Copyright (c) 1997, 1998
 *      Transvirtual Technologies, Inc.  All rights reserved.
 *
 * See the file "license.terms" for information on usage and redistribution
 * of this file.
 */

package java.lang;


public class ThreadGroup
{
	private ThreadGroup parent;
	private String name;
	private int maxPriority;
	private boolean destroyed;
	private boolean daemon;
	private int nthreads;
	private Thread[] threads = new Thread[0];
	private int ngroups;
	private ThreadGroup[] groups = new ThreadGroup[0];

private ThreadGroup() {
	this(Thread.currentThread().getThreadGroup(), "main");
}

public ThreadGroup(String name) {
	this (Thread.currentThread().getThreadGroup(), name);
}

public ThreadGroup(ThreadGroup parent, String name) {
	parent.checkAccess();
	this.name = name;
	this.parent = parent;
	this.maxPriority = parent.getMaxPriority();
	this.daemon = parent.isDaemon();
	parent.add(this);
}

public synchronized int activeCount() {
	int result = nthreads;
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			result = result + groups[i].activeCount();
		}
	}

	return (result);
}

public synchronized int activeGroupCount() {
	int i;
	int result = ngroups;

	for (i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			result = result + groups[i].activeGroupCount();
		}
	}

	return result;
}

synchronized void add(Thread thread) {
	int i;

	nthreads++;

	if (threads == null) {
		threads = new Thread[1];
	}

	for (i = 0; i < threads.length; i++) {
		if (threads[i] == null) {
			threads[i] = thread;
			return;
		}
	}

	/* Allocate some extra space */
	Thread[] newthreads = new Thread[threads.length + 1];
	System.arraycopy(threads, 0, newthreads, 0, threads.length);
	threads = newthreads;
	threads[threads.length - 1] = thread;
}

synchronized void add(ThreadGroup group) {
	ngroups++;

	for (int i = 0; i < groups.length; i++) {
		if (groups[i] == null) {
			groups[i] = group;
			return;
		}
	}
	ThreadGroup[] newgroups = new ThreadGroup[groups.length + 1];
	System.arraycopy(groups, 0, newgroups, 0, groups.length);
	groups = newgroups;
	groups[groups.length - 1] = group;
}

public boolean allowThreadSuspension(boolean b) {
	return (false);
}

final public void checkAccess() {
	SecurityManager sm = System.getSecurityManager();
	if (sm != null)
		sm.checkAccess(this);
}

final public synchronized void destroy() {
	checkAccess();

	if (destroyed || activeCount() > 0) {
		throw new IllegalThreadStateException();
	}

	// Destroy any child groups.
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			groups[i].destroy();
			groups[i] = null;
		}
	}
	ngroups = 0;

	if (parent != null) {
		parent.remove(this);
	}

	destroyed = true;
}

public int enumerate(ThreadGroup list[]) {
	checkAccess();
	return enumerate(list, true, 0);
}

public int enumerate(ThreadGroup list[], boolean recurse) {
	checkAccess();
	return enumerate(list, recurse, 0);
}

private int enumerate(ThreadGroup list[], boolean recurse, int pos) {
	ThreadGroup[] gr = null;

	/* First do the local threads */
	synchronized (this) {
		for (int i = 0; i < groups.length && pos < list.length; i++) {
			if (groups[i] != null)
				list[pos++] = groups[i];
		}

		if (recurse && groups != null) {
			gr = new ThreadGroup[groups.length];
			System.arraycopy (groups, 0, gr, 0, groups.length);
		}
	}

	/* Iterate through sub-groups */
	if (gr != null) {
		for (int i = 0; i < gr.length; i++) {
			if (gr[i] != null) {
				pos = gr[i].enumerate(list, true, pos);
			}
		}
	}

	return pos;
}

public int enumerate(Thread list[]) {
	checkAccess();
	return enumerate(list, true, 0);
}

public int enumerate(Thread list[], boolean recurse) {
	checkAccess();
	return enumerate(list, recurse, 0);
}

private int enumerate(Thread list[], boolean recurse, int pos) {
	ThreadGroup[] gr = null;

	/* First do the local threads */
	synchronized (this) {
		/* Include active threads only */
		for (int i = 0; i < threads.length && pos < list.length; i++) {
			if (threads[i] != null && threads[i].isAlive())
				list[pos++] = threads[i];
		}
		if (recurse && groups != null) {
			gr = new ThreadGroup[groups.length];
			System.arraycopy (groups, 0, gr, 0, groups.length);
		}
	}

	/* Iterate through sub-groups */
	if (gr != null) {
		for (int i = 0; i < gr.length; i++) {
			if (gr[i] != null) {
				pos = gr[i].enumerate(list, true, pos);
			}
		}
	}

	return pos;
}

final public int getMaxPriority() {
	return maxPriority;
}

final public String getName() {
	return name;
}

final public ThreadGroup getParent() {
	return parent;
}

final public boolean isDaemon() {
	return this.daemon;
}

public synchronized boolean isDestroyed() {
	return (destroyed);
}

public synchronized void list() {
	list(0);
}

private void list(int tabulation) {
	printLine(this.toString(), tabulation);
	printThreads(tabulation+1);
	printGroups(tabulation+1);
}

final public boolean parentOf(ThreadGroup g) {
	return ((parent == g) || (parentOf(g.getParent())));
}

private void printGroups(int tabulation) {
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null)
			groups[i].list(tabulation);
	}
}

private void printLine(String str, int tabulation) {
	for (int tabs=0; tabs<tabulation; tabs++) {
		System.out.print("    ");
	}

	System.out.println(str);
}

private void printThreads(int tabulation) {
	for (int i = 0; i < threads.length; i++) {
		if (threads[i] != null)
			printLine(threads[i].toString(), tabulation);
	}
}

synchronized void remove(Thread thread) {
	int i;

	for (i = 0; i < threads.length; i++) {
		if (threads[i] == thread) {
			threads[i] = null;
			nthreads--;
			break;
		}
	}
}

synchronized void remove(ThreadGroup group) {
	int i;

	for (i = 0; i < groups.length; i++) {
		if (groups[i] == group) {
			groups[i] = null;
			ngroups--;
			break;
		}
	}
}

/**
 * @deprecated
 */
final public synchronized void resume() {
	checkAccess();
	for (int i = 0; i < threads.length; i++) {
		if (threads[i] != null) {
			threads[i].resume();
		}
	}
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			groups[i].resume();
		}
	}
}

final public void setDaemon(boolean d) {
	checkAccess();

	daemon = d;
}

final public synchronized void setMaxPriority(int pri) {
	checkAccess();

	maxPriority = pri;
}

/**
 * @deprecated
 */
final public synchronized void stop() {
	checkAccess();
	for (int i = 0; i < threads.length; i++) {
		if (threads[i] != null) {
			threads[i].stop();
		}
	}
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			groups[i].stop();
		}
	}
}

/**
 * @deprecated
 */
final public synchronized void suspend() {
	checkAccess();
	for (int i = 0; i < threads.length; i++) {
		if (threads[i] != null) {
			threads[i].suspend();
		}
	}
	for (int i = 0; i < groups.length; i++) {
		if (groups[i] != null) {
			groups[i].suspend();
		}
	}
}

public String toString() {
	return "java.lang.ThreadGroup[name="+name+",maxpri="+maxPriority+"]";
}

public void uncaughtException(Thread t, Throwable e) {
	if (parent != null) {
		parent.uncaughtException(t, e);
	}
	// be quiet about ThreadDeath exceptions
	else if (!(e instanceof ThreadDeath)) {
		e.printStackTrace(System.err);
	}
}
}
