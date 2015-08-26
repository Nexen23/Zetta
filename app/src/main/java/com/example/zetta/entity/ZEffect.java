package com.example.zetta.entity;

import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZGraphics.IteratableAnimation;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZGame.Identity;

public final class ZEffect extends Identity
{
	public class GraphicsData extends ZGraphics.GraphicsData
	{
		IteratableAnimation animation;
		
		public GraphicsData()
		{
			super();
			animation = new IteratableAnimation();
		}
		
		public GraphicsData(IteratableAnimation _animation)
		{
			super();
			animation = new IteratableAnimation(_animation);
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			animation = new IteratableAnimation(d.animation);
		}

		@Override
		public void draw() 
		{
			animation.draw();
		}

		@Override
		public void update(long timeElapsed)
		{
			animation.setModelPosition(physicsData.position);
			animation.setModelSizeHalfMapCell();
			animation.update(timeElapsed);
		}
	}

	public class PhysicsData extends ZPhysics.PhysicsData
	{
		public long time, timeDestroy;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
		}
		
		@Override
		public void update(long timeElapsed) 
		{
			time += timeElapsed;
			if (time >= timeDestroy)
			{
				destroy();
			}
		}
	}
	
	public GraphicsData graphicsData;
	public PhysicsData physicsData;

	public ZEffect()
	{
		super();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZEffect(ZEffect e)
	{
		super(e);
		graphicsData = new GraphicsData(e.graphicsData);
		physicsData = new PhysicsData(e.physicsData);
	}
	
	@Override
	public void onDestroy() 
	{
		ZPhysics.wasEffectDestroyed = true;
		ZGame.level.map.cells[(int)physicsData.position.y][(int)physicsData.position.x].effect = null;
	}
}
