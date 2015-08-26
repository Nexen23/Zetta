package com.example.zetta.entity;

import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZGraphics.ReversibleAnimation;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZGame.Identity;

public final class ZMachinery extends Identity
{
	public final class GraphicsData extends ZGraphics.GraphicsData
	{
		public ReversibleAnimation animation;
		
		public GraphicsData()
		{
			super();
			animation = new ReversibleAnimation();
		}
		
		public GraphicsData(ReversibleAnimation _animation)
		{
			super();
			animation = new ReversibleAnimation(_animation);
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			animation = new ReversibleAnimation(d.animation);
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
	
	public final class PhysicsData extends ZPhysics.PhysicsData
	{
		public int damage, step, stepsCount;
		public long time;
		public long[] timesStepSwitching;
		public boolean isReversible = true;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			damage = d.damage;
			step = d.step;
			stepsCount = d.stepsCount;
			time = d.time;
			isReversible = d.isReversible;
			
			timesStepSwitching = new long[stepsCount];
			System.arraycopy(d.timesStepSwitching, 0, timesStepSwitching, 0, stepsCount);
		}

		@Override
		public void update(long timeElapsed) 
		{
			time += timeElapsed;
			if (time > timesStepSwitching[step])
			{
				time %= timesStepSwitching[step];
				if (step == stepsCount - 1)
				{
					isReversible = true;
				}
				else
					if (step == 0)
					{
						isReversible = false;
					}

				if (isReversible == true)
				{
					--step;
				}
				else
				{
					++step;
				}
			}
		}
	}
	
	public int score;
	
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	
	public ZMachinery()
	{
		super();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZMachinery(int id)
	{
		super(id);
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZMachinery(ZMachinery m)
	{
		super(m);
		graphicsData = new GraphicsData(m.graphicsData);
		physicsData = new PhysicsData(m.physicsData);
	}

	@Override
	public void onDestroy() 
	{
		ZPhysics.wasMachineryDestroyed = true;
		ZGame.level.map.cells[(int)physicsData.position.y][(int)physicsData.position.x].machinery = null;
	}
}
