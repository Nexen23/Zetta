package com.example.zetta;

import com.example.zetta.ZPrimitive.Vector2f;

public final class ZConstants 
{
	//TODO DELETE
	static final public int mv = 42, sv = 23;
	//static final public int mv = 0, sv = 0;
	//TODO DELETE
	
	static final public Vector2f[]	DIRECTION_UNIT_VECTORS = new Vector2f[]
	                                    {
											new Vector2f(-1f, 0f),
											new Vector2f(0f, 1f),
											new Vector2f(1f, 0f),
											new Vector2f(0f, -1f)
	                                    };
	
	static final public int			DIRECTION_INVALID = -1,
									DIRECTION_LEFT = 0, 
									DIRECTION_UP = 1, 
									DIRECTION_RIGHT = 2, 
									DIRECTION_DOWN = 3,
								
									MAP_NO_BUFFS = 0, 
									MAP_BUFF_SLOW = 1, 
									MAP_BUFF_MOVING_INERTIAL = 2,
									
									CREATURE_CELLS_OCCUPIED_MAX = 4,
									
									GAME_STATE_PLAYING = 0,
									GAME_STATE_FAILED = -1,
									GAME_STATE_COMPLETED = 1,
									
									ITEM_BOMB_DAMAGE = 1,
									
									RESOURCE_ID_INVALID = -1,
									IDENTITY_ID_INVALID = 0, 
									
									ITEM_ID_COIN = 1,
									ITEM_ID_BOMB1 = 2,
									
									TOUCHSCREEN_CONTROL_C4 = 1,
									TOUCHSCREEN_CONTROL_E6 = 2,		
									TOUCHSCREEN_POINTERS_COUNT_MAX = 2,
									
									GRAPHICS_MATRIX_SIZE = 16,
									MIXER_VOLUME_MAX = 100,
									
									FILESYSTEM_MARK_MAX = 123456789;
	
	static final public long 		GAME_FPS = 30, 
									GAME_TIME_SLEEPING = 1000 / GAME_FPS,
								
									MOB_TIME_WAITING_AVERAGE = 1500,
									MOB_TIME_WAITING_DELTA = 400,
									ITEM_BUFF_TIME_INFINITE = -1;
									
	static final public float 		COMPARISON_PRECISION = 1E-13f,
									MAP_CELL_PRECISION = 1E-5f,
									
									SCORE_MULTIPLIER_INITIAL = 1.0f;
}
