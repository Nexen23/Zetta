package com.example.zetta.entity;

public final class ZCell 
{
	public ZObject object = null;
	public ZMachinery machinery = null;
	public ZItem item = null;
	public ZEffect effect = null;
	
	public ZCell()
	{

	}
	
	public ZCell(ZObject _object, ZMachinery _machinery, ZItem _item, ZEffect _effect)
	{
		object = (_object == null) ? null : new ZObject(_object);
		machinery = (_machinery == null) ? null : new ZMachinery(_machinery);
		item = (_item == null) ? null : new ZItem(_item);
		effect = (_effect == null) ? null : new ZEffect(_effect);
	}
	
	public ZCell(ZCell c)
	{
		object = (c.object == null) ? null : new ZObject(c.object);
		machinery = (c.machinery == null) ? null : new ZMachinery(c.machinery);
		item = (c.item == null) ? null : new ZItem(c.item);
		effect = (effect == null) ? null : new ZEffect(c.effect);
	}
}
