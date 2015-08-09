package com.example.zetta.entity;

import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZGraphics.SingleAnimation;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZSequencer;
import com.example.zetta.core.ZGame.Identity;
import com.example.zetta.core.ZGraphics.Image;
import com.example.zetta.core.ZSequencer.Sound;

public final class ZItem extends Identity
{
	public final class GraphicsData extends ZGraphics.GraphicsData
	{
		public Image image;
		public SingleAnimation postAnimation;
		
		public GraphicsData()
		{
			super();	
			image = new Image();
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			image = new Image(d.image);
			postAnimation = (d.postAnimation == null) ? null : new SingleAnimation(d.postAnimation);
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
			
		}
	}
	
	public final class SequencerData extends ZSequencer.SequencerData
	{
		public Sound postSound;
		
		public SequencerData()
		{
			super();
			postSound = new Sound();
		}
		
		public SequencerData(SequencerData d)
		{
			super(d);
			this.postSound = new Sound(d.postSound);
		}
	}
	
	public int score;
	public ZBuff buff;
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	public SequencerData sequencerData;
	
	public ZItem()
	{
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
		sequencerData = new SequencerData();
	}
	
	public ZItem(int id)
	{
		super(id);
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
		sequencerData = new SequencerData();
	}
	
	public ZItem(ZItem i)
	{
		super(i);
		score = i.score;
		buff = (i.buff == null) ? null : new ZBuff(i.buff);
		graphicsData = new GraphicsData(i.graphicsData);
		physicsData = new PhysicsData(i.physicsData);
		sequencerData = new SequencerData(i.sequencerData);
	}

	@Override
	public void onDestroy() 
	{
		ZPhysics.wasItemDestroyed = true;
		ZGame.level.map.cells[(int)physicsData.position.y][(int)physicsData.position.x].item = null;
		
		if (graphicsData.postAnimation != null)
		{
			graphicsData.postAnimation.setPlaying(true);
			graphicsData.postAnimation.setModelPosition(physicsData.position);
			graphicsData.postAnimation.setModelSizeHalf(3, 3);
			ZGraphics.AddPostSingleAnimation(graphicsData.postAnimation);
		}
		
		if (sequencerData.postSound != null)
		{
			ZSequencer.AddPostSound(sequencerData.postSound);
			sequencerData.postSound.start();
		}
	}
}
