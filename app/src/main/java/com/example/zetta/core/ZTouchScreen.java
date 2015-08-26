package com.example.zetta.core;

import java.util.concurrent.Semaphore;

import com.example.zetta.ZLog;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.ZConstants;
import com.example.zetta.ZSettings;

import android.view.MotionEvent;

public final class ZTouchScreen 
{	
	static private boolean isPressing = false;
	static private int direction = ZConstants.DIRECTION_INVALID, pointerCurrentIndex;
	static private Point2f pointerCoordinates;
	static private Semaphore directionLock, isPressingLock;
	
	static
	{
		pointerCoordinates = new Point2f();
		directionLock = new Semaphore(1, true);
		isPressingLock = new Semaphore(1, true);
	}

	static public void onTouchEvent(MotionEvent event) 
	{
		int direction = ZConstants.DIRECTION_INVALID, auxiliary;
		if (event.getPointerCount() > ZConstants.TOUCHSCREEN_POINTERS_COUNT_MAX)
		{
			ZLog.d(ZLog.SECURITY_LEVEL_ERROR, ZLog.TAG_TOUCHSCREEN, "pointers - max count of pointers was exceeded");
			return;
		}
		
		int action = event.getAction(), width = ZSettings.SCREEN_RESOLUTION_ABSOLUTE.width,
			height = ZSettings.SCREEN_RESOLUTION_ABSOLUTE.height,
			pointerIndexCurrent = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		action &= MotionEvent.ACTION_MASK;
		
		switch(action)
		{
		case MotionEvent.ACTION_MOVE:
			pointerCoordinates.x = event.getX(pointerCurrentIndex); 
			pointerCoordinates.y = event.getY(pointerCurrentIndex); 
			ZLog.d(ZLog.TAG_TOUCHSCREEN, "action - move");
			break;
		
		case MotionEvent.ACTION_POINTER_DOWN:
			pointerCurrentIndex = pointerIndexCurrent;
			pointerCoordinates.x = event.getX(pointerCurrentIndex); 
			pointerCoordinates.y = event.getY(pointerCurrentIndex); 
			ZLog.d(ZLog.TAG_TOUCHSCREEN, "action - pointer down");
			break;
			
		case MotionEvent.ACTION_POINTER_UP:
			if (pointerCurrentIndex == pointerIndexCurrent)
			{
				pointerCurrentIndex = (pointerCurrentIndex == 1) ? 0 : 1;
			}
			else
			{
				pointerCurrentIndex = (pointerCurrentIndex == 0) ? 0 : 1;
			}
			pointerCoordinates.x = event.getX(pointerCurrentIndex); 
			pointerCoordinates.y = event.getY(pointerCurrentIndex); 
			pointerCurrentIndex = 0;
			ZLog.d(ZLog.TAG_TOUCHSCREEN, "action - pointer up");
			break;
			
		case MotionEvent.ACTION_DOWN: 	
			try 
			{
				isPressingLock.acquire();
				isPressing = true;	
				isPressingLock.release();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			pointerCurrentIndex = pointerIndexCurrent;
			pointerCoordinates.x = event.getX(pointerCurrentIndex); 
			pointerCoordinates.y = event.getY(pointerCurrentIndex); 
			ZLog.d(ZLog.TAG_TOUCHSCREEN, "action - down");
			break;
			
		case MotionEvent.ACTION_UP: 
			try 
			{
				isPressingLock.acquire();
				isPressing = false;	
				isPressingLock.release();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			pointerCurrentIndex = pointerIndexCurrent;
			pointerCoordinates.x = event.getX(pointerCurrentIndex); 
			pointerCoordinates.y = event.getY(pointerCurrentIndex); 
			ZLog.d(ZLog.TAG_TOUCHSCREEN, "action - up");
			break;
		}
		
		switch(ZSettings.TOUCHSCREEN_CONTROL)
		{
		case ZConstants.TOUCHSCREEN_CONTROL_C4:
			width >>= 1;
	    	height >>= 1;
	    	width -= (int)pointerCoordinates.x;
	    	height -= (int)pointerCoordinates.y;
	    	if (Math.abs(width) > Math.abs(height))
	    	{
	    		direction = (width < 0) ? (byte)ZConstants.DIRECTION_RIGHT : (byte)ZConstants.DIRECTION_LEFT;
	    	}
	    	else
	    	{
	    		direction = (height < 0) ? (byte)ZConstants.DIRECTION_DOWN : (byte)ZConstants.DIRECTION_UP;
	    	}
	    	break;
	    	
		case ZConstants.TOUCHSCREEN_CONTROL_E6:
			auxiliary = height / 3;
			if ((int)pointerCoordinates.y < auxiliary)
			{
				direction = ZConstants.DIRECTION_UP;
			}
			else
				if ((int)pointerCoordinates.y > height - auxiliary)
				{
					direction = ZConstants.DIRECTION_DOWN;
				}
				else
				{
					auxiliary = (width >> 1) - (int)pointerCoordinates.x;
					if (auxiliary < 0)
					{
						direction = ZConstants.DIRECTION_RIGHT;
					}
					else
					{
						direction = ZConstants.DIRECTION_LEFT;
					}
				}
			break;
		}
		
		try 
		{
			directionLock.acquire();
	    	ZTouchScreen.direction = direction;
	    	directionLock.release();
	    	
			String directionString;
			boolean directionCorrect = true;
			switch(direction)
			{
			case ZConstants.DIRECTION_LEFT: directionString = "left"; break;
			case ZConstants.DIRECTION_UP: directionString = "up"; break;
			case ZConstants.DIRECTION_RIGHT: directionString = "right"; break;
			case ZConstants.DIRECTION_DOWN: directionString = "down"; break;
			default: directionString = "nothere!"; directionCorrect = false; break;
			}
			if (directionCorrect == true)
			{
				ZLog.d(ZLog.TAG_TOUCHSCREEN, 
						"pointer - coordinates(" + pointerCoordinates.x + ", " + pointerCoordinates.y + ")");
				ZLog.d(ZLog.TAG_TOUCHSCREEN, 
						"direction - " + direction + "(" + directionString + ")");
			}
			else
			{
				ZLog.d(ZLog.SECURITY_LEVEL_ERROR, ZLog.TAG_TOUCHSCREEN, 
						"pointer - coordinates(" + pointerCoordinates.x + ", " + pointerCoordinates.y + ")");
				ZLog.d(ZLog.SECURITY_LEVEL_ERROR, ZLog.TAG_TOUCHSCREEN, 
						"direction - " + direction + "(" + directionString + ")");
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	static public boolean GetIsPressing()
	{
		boolean isPressingResult = false;
		try 
		{
			isPressingLock.acquire();
			isPressingResult = isPressing;	
			isPressingLock.release();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return isPressingResult;
	}
	
	static public int GetDirection()
	{
		int directionResult = ZConstants.DIRECTION_INVALID;
		try 
		{
			directionLock.acquire();
			directionResult = direction;
			directionLock.release();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return directionResult;
	}
	
	static public void SetDirectionAsync(int _direction)
	{
		direction = _direction;
	}
}
