package com.example.zetta;

import com.example.zetta.ZPrimitive.Size2f;
import com.example.zetta.ZPrimitive.Size2i;

import static java.lang.Math.min;

public final class ZSettings 
{
	static public boolean 		SCREEN_FULLSCREEN = true;
	static public float 		SCREEN_RATIO, SCREEN_RESOLUTION_RELATIVE = 2.0f;
	static public Size2i 		SCREEN_RESOLUTION_ABSOLUTE; 
	static public Size2f 		MAP_CELL_SIZE_RELATIVE, GUI_CELL_SIZE_RELATIVE;
	static public int 			MAP_CELL_SIZE_ABSOLUTE/* = 64*/, GUI_CELL_SIZE_ABSOLUTE/* = 32*/,
								TOUCHSCREEN_CONTROL = ZConstants.TOUCHSCREEN_CONTROL_E6;

	static public int           MAP_CELLS_PER_SCREEN_MINSIZE = 8,
								GUI_CELLS_PER_SCREEN_MINSIZE= 16;
	
	static public boolean		MAP_GRID = true, 
								MAP_CELL_HIGHLIGHT_PLAYER = true,
								MAP_CELL_OBJECT_PASS_HELP = true;
	
	static 
	{
		MAP_CELL_SIZE_RELATIVE = new ZPrimitive.Size2f();
		GUI_CELL_SIZE_RELATIVE = new ZPrimitive.Size2f();
		SCREEN_RESOLUTION_ABSOLUTE = new ZPrimitive.Size2i();
	}
	
	static public void SetScreenResolution(int width, int height)
	{
		
		SCREEN_RESOLUTION_ABSOLUTE.width = width; SCREEN_RESOLUTION_ABSOLUTE.height = height;
		SCREEN_RATIO = (float)width / height;
		int minSize = min(width, height);

		MAP_CELL_SIZE_ABSOLUTE = minSize / MAP_CELLS_PER_SCREEN_MINSIZE;
		GUI_CELL_SIZE_ABSOLUTE = minSize / GUI_CELLS_PER_SCREEN_MINSIZE;
		
		MAP_CELL_SIZE_RELATIVE.set(	SCREEN_RESOLUTION_RELATIVE *
									(float)MAP_CELL_SIZE_ABSOLUTE / width, 
									SCREEN_RESOLUTION_RELATIVE * 
									(float)MAP_CELL_SIZE_ABSOLUTE / height);
		GUI_CELL_SIZE_RELATIVE.set(	SCREEN_RESOLUTION_RELATIVE * 
									(float)GUI_CELL_SIZE_ABSOLUTE / width, 
									SCREEN_RESOLUTION_RELATIVE * 
									(float)GUI_CELL_SIZE_ABSOLUTE / height);
	}
}
