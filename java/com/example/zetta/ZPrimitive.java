package com.example.zetta;

public final class ZPrimitive 
{
	static public class Size2i
	{
		public int width, height;
		
		public Size2i()
		{
			
		}
		
		public Size2i(int w, int h)
		{
			width = w;
			height = h;
		}
		
		public Size2i(Size2i s)
		{
			width = s.width;
			height = s.height;
		}
		
		public void set(int w, int h)
		{
			width = w;
			height = h;
		}
		
		public float hypotenuse()
		{
			return (float)Math.sqrt((double)width * width + (double)height * height);
		}
	}

	static public class Size2f
	{
		public float width, height;
		
		public Size2f()
		{
			
		}
		
		public Size2f(float w, float h)
		{
			width = w;
			height = h;
		}
		
		public Size2f(Size2f s)
		{
			width = s.width;
			height = s.height;
		}
		
		public void set(float w, float h)
		{
			width = w;
			height = h;
		}
		
		public float hypotenuse()
		{
			return (float)Math.sqrt((double)width * width + (double)height * height);
		}
	}

	static public class Vector2f
	{
		public float x, y;
		
		public Vector2f()
		{
			
		}
		
		public Vector2f(float x, float y)
		{
			this.x = x; this.y = y;
		}
		
		public Vector2f(Vector2f v)
		{
			x = v.x; y = v.y;
		}
		
		public Vector2f(Point2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void set(float x, float y)
		{
			this.x = x; this.y = y;
		}
		
		public void set(Vector2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void set(Point2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void add(Vector2f v)
		{
			x += v.x; y += v.y;
		}
		
		public void add(Point2f v)
		{
			x += v.x; y += v.y;
		}
		
		public void add(float x, float y)
		{
			this.x += x; this.y += y;
		}
		
		public void add(float m)
		{
			this.x += m; this.y += m;
		}
		
		public void reduce(Point2f v)
		{
			x -= v.x; y -= v.y;
		}
		
		public void reduce(Vector2f v)
		{
			x -= v.x; y -= v.y;
		}
		
		public void reduce(float x, float y)
		{
			this.x -= x; this.y -= y;
		}
		
		public void reduce(float m)
		{
			this.x -= m; this.y -= m;
		}
		
		public void multiply(float x, float y)
		{
			this.x *= x; this.y *= y;
		}
		
		public void multiply(Vector2f v)
		{
			this.x *= v.x; this.y *= v.y;
		}
		
		public void multiply(Point2f v)
		{
			this.x *= v.x; this.y *= v.y;
		}
		
		public void multiply(float m)
		{
			this.x *= m; this.y *= m;
		}
		
		public void divide(float x, float y)
		{
			this.x /= x; this.y /= y;
		}
		
		public void divide(Vector2f v)
		{
			this.x /= v.x; this.y /= v.y;
		}
		
		public void divide(Point2f v)
		{
			this.x /= v.x; this.y /= v.y;
		}
		
		public void divide(float m)
		{
			this.x /= m; this.y /= m;
		}
		
		public float length()
		{
			return (float)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public float lengthInverse()
		{
			return 1.0f / (float)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public void normalize()
		{
			float lengthInverse = 1.0f / (float)Math.sqrt((double)x * x + (double)y * y);
			x *= lengthInverse; y *= lengthInverse;
		}
		
		public boolean equals(Point2f v)
		{
			if (Compare(x, v.x) == true &&
					Compare(y, v.y) == true)
			{
				return true;
			}
			return false;
		}
	}
	
	static public class Point2f	
	{
		public float x, y;
		
		public Point2f()
		{
			
		}
		
		public Point2f(float x, float y)
		{
			this.x = x; this.y = y;
		}
		
		public Point2f(Vector2f v)
		{
			x = v.x; y = v.y;
		}
		
		public Point2f(Point2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void set(float x, float y)
		{
			this.x = x; this.y = y;
		}
		
		public void set(Vector2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void set(Point2f v)
		{
			x = v.x; y = v.y;
		}
		
		public void add(Vector2f v)
		{
			x += v.x; y += v.y;
		}
		
		public void add(Point2f v)
		{
			x += v.x; y += v.y;
		}
		
		public void add(float x, float y)
		{
			this.x += x; this.y += y;
		}
		
		public void add(float m)
		{
			this.x += m; this.y += m;
		}
		
		public void reduce(Vector2f v)
		{
			x -= v.x; y -= v.y;
		}
		
		public void reduce(Point2f v)
		{
			x -= v.x; y -= v.y;
		}
		
		public void reduce(float x, float y)
		{
			this.x -= x; this.y -= y;
		}
		
		public void reduce(float m)
		{
			this.x -= m; this.y -= m;
		}
		
		public void multiply(float x, float y)
		{
			this.x *= x; this.y *= y;
		}
		
		public void multiply(Vector2f v)
		{
			this.x *= v.x; this.y *= v.y;
		}
		
		public void multiply(Point2f v)
		{
			this.x *= v.x; this.y *= v.y;
		}
		
		public void multiply(float m)
		{
			this.x *= m; this.y *= m;
		}
		
		public void divide(float x, float y)
		{
			this.x /= x; this.y /= y;
		}
		
		public void divide(Vector2f v)
		{
			this.x /= v.x; this.y /= v.y;
		}
		
		public void divide(Point2f v)
		{
			this.x /= v.x; this.y /= v.y;
		}
		
		public void divide(float m)
		{
			this.x /= m; this.y /= m;
		}
		
		public float distance(Point2f v)
		{
			return (float)Math.sqrt(Math.pow((double)(x - v.x), 2) + Math.pow((double)(y - v.y), 2));
		}
		
		public float length()
		{
			return (float)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public float lengthInverse()
		{
			return 1.0f / (float)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public boolean equals(Point2f v)
		{
			if (Compare(x, v.x) == true  &&
					Compare(y, v.y) == true)
			{
				return true;
			}
			return false;
		}
	}

	static public class Point2i	
	{
		public int x, y;
		
		public Point2i()
		{
			
		}
		
		public Point2i(int x, int y)
		{
			this.x = x; this.y = y;
		}
		
		public Point2i(Point2i v)
		{
			x = v.x; y = v.y;
		}
		
		public Point2i(Point2f v)
		{
			x = (int)v.x; y = (int)v.y;
		}
		
		public void set(int x, int y)
		{
			this.x = x; this.y = y;
		}
		
		public void set(Point2f v)
		{
			x = (int)v.x; y = (int)v.y;
		}
		
		public void add(Point2i v)
		{
			x += v.x; y += v.y;
		}
		
		public void add(int x, int y)
		{
			this.x += x; this.y += y;
		}
		
		public void add(int m)
		{
			this.x += m; this.y += m;
		}
		
		public void reduce(Point2i v)
		{
			x -= v.x; y -= v.y;
		}
		
		public void reduce(int x, int y)
		{
			this.x -= x; this.y -= y;
		}
		
		public void reduce(int m)
		{
			this.x -= m; this.y -= m;
		}
		
		public void multiply(int x, int y)
		{
			this.x *= x; this.y *= y;
		}
		
		public void multiply(Point2i v)
		{
			this.x *= v.x; this.y *= v.y;
		}
		
		public void multiply(int m)
		{
			this.x *= m; this.y *= m;
		}
		
		public void divide(int x, int y)
		{
			this.x /= x; this.y /= y;
		}

		public void divide(Point2i v)
		{
			this.x /= v.x; this.y /= v.y;
		}
		
		public void divide(int m)
		{
			this.x /= m; this.y /= m;
		}
		
		public float distance(Point2i v)
		{
			return (float)Math.sqrt(Math.pow((double)(x - v.x), 2) + Math.pow((double)(y - v.y), 2));
		}
		
		public float length()
		{
			return (float)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public float lengthInverse()
		{
			return 1.0f / (int)Math.sqrt((double)x * x + (double)y * y);
		}
		
		public boolean equals(Point2i v)
		{
			if (x == v.x  && y == v.y)
			{
				return true;
			}
			return false;
		}
	}
	
	static final public boolean Compare(float x, float y)
	{
		if (Math.abs(x - y) <= ZConstants.COMPARISON_PRECISION)
		{
			return true;
		}
		return false;
	}
}
