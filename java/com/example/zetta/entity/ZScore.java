package com.example.zetta.entity;

import com.example.zetta.ZConstants;

public final class ZScore 
{
	public float amount,
		factorTimerTicked, factorPlayerMoved, factorItemPicked, factorMobKilled, factorLevelPassed;
	
	public ZScore()
	{
		amount = 0f; 
		factorTimerTicked = factorPlayerMoved = factorItemPicked = 
			factorMobKilled = factorLevelPassed = ZConstants.SCORE_MULTIPLIER_INITIAL;
	}
	
	public ZScore(ZScore s)
	{
		amount = s.amount;
		factorTimerTicked = s.factorTimerTicked;
		factorPlayerMoved = s.factorPlayerMoved;
		factorItemPicked = s.factorItemPicked;
		factorMobKilled = s.factorMobKilled;
		factorLevelPassed = s.factorLevelPassed;
	}
	
	public ZScore(float amount)
	{
		this.amount = amount;
		factorTimerTicked = factorPlayerMoved = factorItemPicked = 
			factorMobKilled = factorLevelPassed = ZConstants.SCORE_MULTIPLIER_INITIAL;
	}
	
	public void setMultiplier(float factor)
	{
		factorTimerTicked = factorPlayerMoved = factorItemPicked = 
			factorMobKilled = factorLevelPassed = factor;
	}
	
	public void setMultiplier(float factorTimerTicked, float factorPlayerMoved, float factorItemPicked,
			float factorMobKilled, float factorLevelPassed)
	{
		this.factorTimerTicked = factorTimerTicked;
		this.factorPlayerMoved = factorPlayerMoved;
		this.factorItemPicked = factorItemPicked;
		this.factorMobKilled = factorMobKilled;
		this.factorLevelPassed = factorLevelPassed;
	}
	
	public void timerTicked(long time)
	{
		amount += ((float)time) * factorTimerTicked;
	}
	
	public void playerMoved(float distance)
	{
		amount += distance * factorPlayerMoved;
	}
	
	public void itemPicked(int score)
	{
		amount += ((float)score) * factorItemPicked;
	}
	
	public void mobKilled(int score)
	{
		amount += ((float)score) * factorMobKilled;
	}
	
	public void levelPassed(int score)
	{
		amount += ((float)score) * factorLevelPassed;
	}
}
