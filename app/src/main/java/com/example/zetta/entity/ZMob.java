package com.example.zetta.entity;

import com.example.zetta.ZConstants;
import com.example.zetta.core.ZAI;
import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZPhysics;

public final class ZMob extends ZCreature 
{
	public class GraphicsData extends ZCreature.GraphicsData
	{
		public GraphicsData()
		{
			super();
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
		}

		@Override
		public void draw() 
		{
			animationOnMoving.draw();
		}

		@Override
		public void update(long timeElapsed) 
		{
			if (physicsData.isMoving == false)
			{
				animationOnMoving.zeroStep();
			}
			animationOnMoving.setPlaying(physicsData.isMoving);
			animationOnMoving.setState(physicsData.direction);
			animationOnMoving.setModelPosition(physicsData.position);
			animationOnMoving.setModelSizeHalfMapCell();
			animationOnMoving.update(timeElapsed);
		}
	}

	public class PhysicsData extends ZCreature.PhysicsData
	{
		public int damage, detectionDistance;
		public boolean isFlying = false, isImmortal = false, isSpectral = false, isHostile = true;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			damage = d.damage;
			detectionDistance = d.detectionDistance;
			isFlying = d.isFlying;
			isImmortal = d.isImmortal;
			isSpectral = d.isSpectral;
			isHostile = d.isHostile;
		}

		@Override
		public void update(long timeElapsed) 
		{
			ZPlayer player = ZGame.level.player;
			if (isHostile == true && 
					position.distance(player.physicsData.position) <= (float)detectionDistance)
			{
				if (aiData.isPursuing == false)
				{
					aiData.isPursuing = true;
					if (isMoving == true)
					{
						ZAI.ComputeNearestPosition(ZMob.this);
					}
					else
					{
						ZAI.ComputeNextPosition(ZMob.this);
					}
				}

				movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[direction]);
				movementVector.multiply(speed * timeElapsed);
				setPosition(movementVector.x + position.x,
						movementVector.y + position.y);
				if (positionPrevious.distance(position) >=
					positionPrevious.distance(aiData.positionNext))
				{
					position.set(positionPrevious);
					setPosition(aiData.positionNext);
					ZAI.ComputeNextPosition(ZMob.this);
				}
			}
			else
			{
				aiData.isPursuing = false;
				if (isMoving == true)
				{
					movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[direction]);
					movementVector.multiply(speed * timeElapsed);
					setPosition(movementVector.x + position.x,
							movementVector.y + position.y);
					if (positionPrevious.distance(position) >=
						positionPrevious.distance(aiData.positionNext))
					{
						position.set(positionPrevious);
						setPosition(aiData.positionNext);
						isMoving = false;
						aiData.timeWaiting = ZAI.GetTimeWaiting();
					}
				}
				else
				{
					aiData.timeWaiting -= timeElapsed;
					if (aiData.timeWaiting <= 0)
					{
						ZAI.ComputeNextPosition(ZMob.this);
					}
				}		
			}
		}
	}
	
	public class AIData extends ZAI.AIData
	{
		public boolean isPursuing;
		
		public AIData()
		{
			super();
			isPursuing = false;
		}
		
		public AIData(AIData d)
		{
			super(d);
			isPursuing = d.isPursuing;
		}

		@Override
		public void update(long timeElapsed) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	public int score;
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	public AIData aiData;
	
	public ZMob()
	{
		super();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
		aiData = new AIData();
	}
	
	public ZMob(int id)
	{
		super(id);
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
		aiData = new AIData();
	}
	
	public ZMob(ZMob m)
	{
		super(m);
		score = m.score;
		graphicsData = new GraphicsData(m.graphicsData);
		physicsData = new PhysicsData(m.physicsData);
		aiData = new AIData(m.aiData);
	}

	@Override
	public void onDestroy() 
	{
		ZPhysics.wasMobDestroyed = true;
	}
}
