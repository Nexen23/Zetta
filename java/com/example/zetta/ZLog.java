package com.example.zetta;

import java.util.HashMap;

import android.util.Log;

public final class ZLog 
{
	static final public String		TAG_LOG = "TAG_LOG",
									TAG_ACTIVITY = "TAG_ACTIVITY", 
									TAG_THREAD_GL = "TAG_THREAD_GL",
									TAG_THREAD_GAME = "TAG_THREAD_GAME",
									
									TAG_TOUCHSCREEN = "TAG_TOUCHSCREEN",
									TAG_MEMORY_USAGE = "TAG_MEMORY_USAGE",
									TAG_GRAPHICS = "TAG_GRAPHICS",
									TAG_AI = "TAG_AI";
	static final public int			SECURITY_LEVEL_NOTIFICATION = 0,
									SECURITY_LEVEL_WARNING = 1,
									SECURITY_LEVEL_ERROR = 2,
									
									STACKTRACE_OFFSET = 3;
	
	static final private boolean 	DEBUG = true;
	static final private HashMap<String, Boolean> switcher = new HashMap<String, Boolean>();
	
	static 
	{
		switcher.put(TAG_LOG, true);
		switcher.put(TAG_ACTIVITY, true);
		switcher.put(TAG_THREAD_GL, true);
		switcher.put(TAG_THREAD_GAME, true);
		
		switcher.put(TAG_TOUCHSCREEN, true);
		switcher.put(TAG_MEMORY_USAGE, true);
		switcher.put(TAG_GRAPHICS, true);
		switcher.put(TAG_AI, true);
	}
	
	static public void d(int securityLevel, String tag, String data)
	{
		if (DEBUG == true)
		{
			StackTraceElement[] stackTraceElements;
			String calledClass, calledMethod;
			stackTraceElements = Thread.currentThread().getStackTrace();
			calledClass = ParseClassName(stackTraceElements[STACKTRACE_OFFSET].getClassName());
			calledMethod = stackTraceElements[STACKTRACE_OFFSET].getMethodName();
			if (switcher.containsKey(tag) == false)
			{
				Log.d(TAG_LOG, "Error[" + calledClass + "." + calledMethod + "()]: no such tag (" + tag + ")");
			}
			else
				if (switcher.get(tag) == true)
				{
					switch(securityLevel)
					{
					case SECURITY_LEVEL_NOTIFICATION:
						Log.d(tag, "Notification[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_WARNING :
						Log.d(tag, "Warning[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_ERROR :
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					default:
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: no such security level");
						break;
					}
				}
		}
	}
	
	static public void d(int securityLevel, String tag)
	{
		if (DEBUG == true)
		{
			StackTraceElement[] stackTraceElements;
			String calledClass, calledMethod, data = "";
			stackTraceElements = Thread.currentThread().getStackTrace();
			calledClass = ParseClassName(stackTraceElements[STACKTRACE_OFFSET].getClassName());
			calledMethod = stackTraceElements[STACKTRACE_OFFSET].getMethodName();
			if (switcher.containsKey(tag) == false)
			{
				Log.d(TAG_LOG, "Error[" + calledClass + "." + calledMethod + "()]: no such tag (" + tag + ")");
			}
			else
				if (switcher.get(tag) == true)
				{
					switch(securityLevel)
					{
					case SECURITY_LEVEL_NOTIFICATION:
						Log.d(tag, "Notification[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_WARNING :
						Log.d(tag, "Warning[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_ERROR :
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					default:
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: no such security level");
						break;
					}
				}
		}
	}
	
	static public void d(String tag, String data)
	{
		if (DEBUG == true)
		{
			StackTraceElement[] stackTraceElements;
			int stackTraceElementsLength, securityLevel = SECURITY_LEVEL_NOTIFICATION;
			stackTraceElements = Thread.currentThread().getStackTrace();
			String calledClass, calledMethod;
			calledClass = ParseClassName(stackTraceElements[STACKTRACE_OFFSET].getClassName());
			calledMethod = stackTraceElements[STACKTRACE_OFFSET].getMethodName();
			if (switcher.containsKey(tag) == false)
			{
				Log.d(TAG_LOG, "Error[" + calledClass + "." + calledMethod + "()]: no such tag (" + tag + ")");
			}
			else
				if (switcher.get(tag) == true)
				{
					switch(securityLevel)
					{
					case SECURITY_LEVEL_NOTIFICATION:
						Log.d(tag, "Notification[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_WARNING :
						Log.d(tag, "Warning[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_ERROR :
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					default:
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: no such security level");
						break;
					}
				}
		}
	}
	
	static public void d(String tag)
	{
		if (DEBUG == true)
		{
			StackTraceElement[] stackTraceElements;
			int securityLevel = SECURITY_LEVEL_NOTIFICATION;
			String calledClass, calledMethod, data = "";
			stackTraceElements = Thread.currentThread().getStackTrace();
			calledClass = ParseClassName(stackTraceElements[STACKTRACE_OFFSET].getClassName());
			calledMethod = stackTraceElements[STACKTRACE_OFFSET].getMethodName();
			if (switcher.containsKey(tag) == false)
			{
				Log.d(TAG_LOG, "Error[" + calledClass + "." + calledMethod + "()]: no such tag (" + tag + ")");
			}
			else
				if (switcher.get(tag) == true)
				{
					switch(securityLevel)
					{
					case SECURITY_LEVEL_NOTIFICATION:
						Log.d(tag, "Notification[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_WARNING :
						Log.d(tag, "Warning[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					case SECURITY_LEVEL_ERROR :
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: " + data);
						break;
						
					default:
						Log.d(tag, "Error[" + calledClass + "." + calledMethod + "()]: no such security level");
						break;
					}
				}
		}
	}
	
	static private String ParseClassName(String name) 
	{
		return name.substring(name.lastIndexOf('.') + 1);
	}
}
