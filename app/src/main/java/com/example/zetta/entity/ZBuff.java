package com.example.zetta.entity;

import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZLogic;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZGame.Identity;
import com.example.zetta.core.ZGraphics.Image;

public final class ZBuff extends Identity
{
	public final class GraphicsData extends ZGraphics.GraphicsData
	{
		public Image icon;
		
		public GraphicsData()
		{
			super();	
			icon = new Image();
		}
		
		public GraphicsData(Image _icon)
		{
			super();
			icon = new Image(_icon);
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			icon = new Image(d.icon);
		}

		@Override
		public void draw() 
		{
			icon.draw();
		}

		@Override
		public void update(long timeElapsed) 
		{
			icon.setModelPosition(physicsData.position);
			icon.setModelSizeHalfGuiCell();
			icon.update(timeElapsed);
		}
	}
	
	public final class PhysicsData extends ZPhysics.PhysicsData
	{
		public long timeDestroying, time;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			timeDestroying = d.timeDestroying;
			time = d.time;
		}

		@Override
		public void update(long timeElapsed) 
		{
			time += timeElapsed;
			if (time >= timeDestroying)
			{
				ZLogic.ReleaseBuff(ZBuff.this);
				destroy();
			}
		}
	}
	
	public int count, countMax;
	
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	
	public ZBuff()
	{
		super();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZBuff(int id)
	{
		super(id);
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZBuff(ZBuff b)
	{
		super(b);
		count = b.count;
		countMax = b.countMax;
		graphicsData = new GraphicsData(b.graphicsData);
		physicsData = new PhysicsData(b.physicsData);
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
	}
}
