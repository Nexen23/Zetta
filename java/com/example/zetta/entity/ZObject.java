package com.example.zetta.entity;

import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZGame.Identity;
import com.example.zetta.core.ZGraphics.Image;

public class ZObject extends Identity
{
	public final class GraphicsData extends ZGraphics.GraphicsData
	{
		public Image image;
		
		public GraphicsData()
		{
			super();
			image = new Image();
		}
		
		public GraphicsData(Image _image)
		{
			super();
			image = new Image(_image);
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			image = new Image(d.image);
		}

		@Override
		public void draw()
		{
			image.draw();			
		}

		@Override
		public void update(long timeElapsed) 
		{
			image.setModelPosition(physicsData.position);
			image.setModelSizeHalfMapCell();
			image.update(timeElapsed);
		}
	}

	public final class PhysicsData extends ZPhysics.PhysicsData
	{
		public int direction, damage;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			direction = d.direction;
			damage = d.damage;
		}

		@Override
		public void update(long timeElapsed) 
		{
			
		}
	}
	
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	
	public ZObject()
	{
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZObject(int id)
	{
		super(id);
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZObject(ZObject o)
	{
		super(o);
		graphicsData = new GraphicsData(o.graphicsData);
		physicsData = new PhysicsData(o.physicsData);
	}

	@Override
	public void onDestroy() 
	{
		ZPhysics.wasObjectDestroyed = true;
		ZGame.level.map.cells[(int)physicsData.position.y][(int)physicsData.position.x].object = null;
	}
}
