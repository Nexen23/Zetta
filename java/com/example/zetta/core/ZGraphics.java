package com.example.zetta.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.zetta.ZConstants;
import com.example.zetta.ZLog;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.ZPrimitive.Size2f;
import com.example.zetta.ZPrimitive.Size2i;
import com.example.zetta.ZSettings;
import com.example.zetta.core.ZGame.IUpdatable;
import com.example.zetta.entity.ZEffect;
import com.example.zetta.entity.ZItem;
import com.example.zetta.entity.ZLevel;
import com.example.zetta.entity.ZMachinery;
import com.example.zetta.entity.ZMap;
import com.example.zetta.entity.ZMob;
import com.example.zetta.entity.ZObject;
import com.example.zetta.entity.ZPlayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public final class ZGraphics 
{
	static public abstract interface IDrawable 
	{
		public void draw();
	}
	
	static private final class Surface extends GLSurfaceView
	{
		@Override
		public void surfaceCreated(SurfaceHolder holder) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			super.surfaceCreated(holder);
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			super.surfaceChanged(holder, format, w, h);
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			super.surfaceDestroyed(holder);
		}
		
		public Surface(Context context) 
		{
			super(context);
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			ZTouchScreen.onTouchEvent(event);
			return true;
		}
	}
	
	static private final class Renderer implements GLSurfaceView.Renderer
	{
		@Override
		public void onDrawFrame(GL10 gl) 
		{		
			ZLog.d(ZLog.TAG_THREAD_GL);
			
			GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT);
			
			GLES10.glMatrixMode(GLES10.GL_PROJECTION);
			GLES10.glLoadMatrixf(cameraMatrix, 0);
		
			GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
			GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
			
			map.graphicsData.draw();
			
			for (int i = 0, n = map.itemsCount; i < n; ++i) //TODO: проблема в копиях
			{
				items.get(i).graphicsData.draw();
			}
			for (int i = 0, n = ZGame.level.map.objectsCount; i < n; ++i)
			{
				objects.get(i).graphicsData.draw();
			}
			for (int i = 0, n = ZGame.level.map.machineriesCount; i < n; ++i)
			{
				machineries.get(i).graphicsData.draw();
			}
			for (int i = 0, n = ZGame.level.map.mobsCount; i < n; ++i)
			{
				mobs.get(i).graphicsData.draw();
			}
			
			for (int i = 0, n = ZGame.level.map.effectsCount; i < n; ++i)
			{
				effects.get(i).graphicsData.draw();
			}
			
			player.graphicsData.draw();
			
			for (int i = 0, n = postSingleAnimationsCount; i < n; ++i)
			{
				postSingleAnimations.get(i).draw();
			}
			 
			GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY); 
	        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
	        
	        //TODO
	        //GLES10.glDisable(GLES10.GL_ALPHA_TEST);
			//GLES10.glDisable(GLES10.GL_BLEND); 
			//GLES10.glDisable(GLES10.GL_TEXTURE_2D);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			
			ZSettings.SetScreenResolution(width, height);
			
			GLES10.glViewport(0, 0, width, height);
			/*GLES10.glMatrixMode(GLES10.GL_PROJECTION);
			GLES10.glLoadIdentity();
			GLES10.glOrthof(-1, 1, -1, 1, -1, 1);*/
			//TODO don't need?
			
			GLES10.glEnable(GL10.GL_CULL_FACE);
			GLES10.glCullFace(GL10.GL_BACK);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) 
		{
			ZLog.d(ZLog.TAG_GRAPHICS);
			
			GLES10.glDisable(GLES10.GL_DITHER);  
			GLES10.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_FASTEST);
			GLES10.glClearColor(0.5f, 0.5f, 0.5f, 0);
			GLES10.glShadeModel(GLES10.GL_SMOOTH);      
			
			GLES10.glEnable(GLES10.GL_TEXTURE_2D);
			GLES10.glEnable(GLES10.GL_ALPHA_TEST);			
			GLES10.glEnable(GLES10.GL_BLEND); 
			GLES10.glBlendFunc(GLES10.GL_SRC_ALPHA, GLES10.GL_ONE_MINUS_SRC_ALPHA);
			
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR);                                                     // 5a 
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR); 
			
			for (int i = 0, n = ZFileSystem.spriteResources.length; i < n; ++i)
			{
				if (ZFileSystem.spriteResources[i].isUsed == true)
				{
					ZFileSystem.spriteResources[i].DecodeBitmap();
					int x = ZFileSystem.spriteResources[i].bitmap.getWidth();
					ZFileSystem.spriteResources[i].BindTexture();
				}
			}
		}
	}
	
	static public abstract class Sprite implements IDrawable, IUpdatable
	{
		public FloatBuffer modelMeshBuffer, textureMeshBuffer;
		public float[] modelMesh, textureMesh, modelMatrix, textureMatrix;
		public Point2f texturePosition, modelPosition;
		public Size2f modelSizeHalf, textureSize;
		public SpriteResource resource;
		
		protected ByteBuffer byteBuffer;
		
		public Sprite()
		{
			textureMatrix = new float[ZConstants.GRAPHICS_MATRIX_SIZE];
			modelMatrix = new float[ZConstants.GRAPHICS_MATRIX_SIZE];
			texturePosition = new Point2f();
			textureSize = new Size2f();
			modelPosition = new Point2f();
			modelSizeHalf = new Size2f();
			resource = null;
		}
		
		public Sprite(Sprite p)
		{
			textureMatrix = new float[ZConstants.GRAPHICS_MATRIX_SIZE];
			System.arraycopy(p.textureMatrix, 0, textureMatrix, 0, p.textureMatrix.length);
			modelMatrix = new float[ZConstants.GRAPHICS_MATRIX_SIZE];
			System.arraycopy(p.modelMatrix, 0, modelMatrix, 0, p.modelMatrix.length);
			resource = p.resource;
			
			texturePosition = new Point2f(p.texturePosition);
			textureSize = new Size2f(p.textureSize);
			modelPosition = new Point2f(p.modelPosition);
			modelSizeHalf = new Size2f(p.modelSizeHalf);
			
			modelMesh = new float[p.modelMesh.length];
			System.arraycopy(p.modelMesh, 0, modelMesh, 0, p.modelMesh.length);
			byteBuffer = ByteBuffer.allocateDirect(modelMesh.length * 4);   
	        byteBuffer.order(ByteOrder.nativeOrder()); 
	        modelMeshBuffer = byteBuffer.asFloatBuffer(); 
	        modelMeshBuffer.put(modelMesh); 
	        modelMeshBuffer.position(0); 
			
	        textureMesh = new float[p.textureMesh.length];
			System.arraycopy(p.textureMesh, 0, textureMesh, 0, p.textureMesh.length);
			byteBuffer = ByteBuffer.allocateDirect(textureMesh.length * 4);   
	        byteBuffer.order(ByteOrder.nativeOrder()); 
	        textureMeshBuffer = byteBuffer.asFloatBuffer(); 
	        textureMeshBuffer.put(this.textureMesh); 
	        textureMeshBuffer.position(0);
		}
		
		public final void setMeshes(float[] _modelMesh, float[] _textureMesh)
		{			
			modelMesh = new float[_modelMesh.length];
			System.arraycopy(_modelMesh, 0, modelMesh, 0, _modelMesh.length);
			byteBuffer = ByteBuffer.allocateDirect(modelMesh.length * 4);   
	        byteBuffer.order(ByteOrder.nativeOrder()); 
	        modelMeshBuffer = byteBuffer.asFloatBuffer(); 
	        modelMeshBuffer.put(modelMesh); 
	        modelMeshBuffer.position(0); 
			
	        textureMesh = new float[_textureMesh.length];
			System.arraycopy(_textureMesh, 0, textureMesh, 0, _textureMesh.length);
			byteBuffer = ByteBuffer.allocateDirect(textureMesh.length * 4);   
	        byteBuffer.order(ByteOrder.nativeOrder()); 
	        textureMeshBuffer = byteBuffer.asFloatBuffer(); 
	        textureMeshBuffer.put(this.textureMesh); 
	        textureMeshBuffer.position(0);
		}	
		
		//TODO �� ������ ���� ������ ���������������!!!
		public final void setModelPosition(Point2f modelPositionOnMap)
		{
			modelPosition.set(
					modelPositionOnMap.x * ZSettings.MAP_CELL_SIZE_RELATIVE.width - 
						map.graphicsData.tile.modelSizeHalf.width,
					modelPositionOnMap.y * ZSettings.MAP_CELL_SIZE_RELATIVE.height - 
						map.graphicsData.tile.modelSizeHalf.height);
		}
		
		public final void setModelPosition(float x, float y)
		{
			modelPosition.set(
					x * ZSettings.MAP_CELL_SIZE_RELATIVE.width - 
						map.graphicsData.tile.modelSizeHalf.width,
					y * ZSettings.MAP_CELL_SIZE_RELATIVE.height - 
						map.graphicsData.tile.modelSizeHalf.height);
		}
		
		//TODO �� ������ ���� ����� �������!!!
		public final void setModelPositionMap()
		{
			modelPosition.set(-ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
					-ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);		
		}
		
		//TODO wtf?
		/*public final void setModelSizeHalf(Size2f modelSize)
		{
			modelSizeHalf.set(modelSize.width / 2, modelSize.height / 2);
		}*/
		
		//TODO wtf?
		public final void setModelSizeHalf(Size2i modelSize)
		{
			modelSizeHalf.set((float)modelSize.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
					(float)modelSize.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		}
		
		//TODO wtf?
		public final void setModelSizeHalf(int x, int y)
		{
			modelSizeHalf.set((float)x * ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2, 
					(float)y * ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		}
		
		public final void setModelSizeHalfMapCell()
		{
			modelSizeHalf.set(ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
					ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		}
		
		public final void setModelSizeHalfGuiCell()
		{
			modelSizeHalf.set(ZSettings.GUI_CELL_SIZE_RELATIVE.width / 2,
					ZSettings.GUI_CELL_SIZE_RELATIVE.height / 2);
		}
		
		public void computeMatrixes()
		{
			Matrix.setIdentityM(textureMatrix, 0);
			Matrix.translateM(textureMatrix, 0, texturePosition.x, texturePosition.y, 0);
			Matrix.scaleM(textureMatrix, 0, textureSize.width, textureSize.height, 0);
			
			Matrix.setIdentityM(modelMatrix, 0);
			Matrix.translateM(modelMatrix, 0, modelPosition.x, modelPosition.y, 0);			
			Matrix.scaleM(modelMatrix, 0, modelSizeHalf.width, modelSizeHalf.height, 0);
		}
		
		//TODO: is it need?
		/*public void computeTextureMatrix()
		{
			Matrix.setIdentityM(textureMatrix, 0);
			Matrix.translateM(textureMatrix, 0, texturePosition.x, texturePosition.y, 0);
			Matrix.scaleM(textureMatrix, 0, textureSize.width, textureSize.height, 0);
		}
		
		public void computeModelMatrix()
		{
			Matrix.setIdentityM(modelMatrix, 0);
			Matrix.translateM(modelMatrix, 0, modelPosition.x, modelPosition.y, 0);			
			Matrix.scaleM(modelMatrix, 0, modelSizeHalf.width, modelSizeHalf.height, 0);
		}*/
		
		@Override
		public void draw() 
		{
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR);                                                     // 5a 
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR);
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S, GLES10.GL_REPEAT); 
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T, GLES10.GL_REPEAT);
			
			GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, resource.textureId[0]);  
			GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, textureMeshBuffer);
			
			GLES10.glMatrixMode(GLES10.GL_TEXTURE);
			GLES10.glLoadMatrixf(textureMatrix, 0);
			
			GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
			GLES10.glLoadMatrixf(modelMatrix, 0);
			
			GLES10.glVertexPointer(2, GLES10.GL_FLOAT, 0, modelMeshBuffer); 
			GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
	
	static public final class Image extends Sprite
	{		
		public Image()
		{
			super();
		}
		
		public Image(Image s)
		{
			super(s);
		}

		@Override
		public void update(long timeElapsed) 
		{
			computeMatrixes();
		}
	}
	
	static public abstract class Animation extends Sprite
	{	
		public long time = 0;
		public int step = 0;
		public boolean isPlaying = false;
		
		public Animation()
		{
			super();
		}
		
		public Animation(Animation a)
		{
			super(a);
			time = a.time;
			step = a.step;
			isPlaying = a.isPlaying;
		}
		
		public void setPlaying(boolean _isPlaying)
		{
			if (isPlaying != _isPlaying)
			{
				isPlaying = _isPlaying;
				step = 0;
			}
		}
	}
	
	static public final class SingleAnimation extends Animation
	{
		public int stepsCount;
		public long timeStepSwitching;
		boolean isFinished = false;
		
		public SingleAnimation()
		{
			super();
		}
		
		public SingleAnimation(SingleAnimation a)
		{
			super(a);
			stepsCount = a.stepsCount;
			timeStepSwitching = a.timeStepSwitching;
		}

		@Override
		public void update(long timeElapsed) 
		{
			if (isPlaying == true)
			{
				time += timeElapsed;
				int stepsElapsed = (int)(time / timeStepSwitching);
				if (step + stepsElapsed < stepsCount)
				{
					step += stepsElapsed;
					time %= timeStepSwitching;
					texturePosition.add(textureSize.width * (float)stepsElapsed, 0f);
				}
				else
				{
					isFinished = true;
				}
			}
			computeMatrixes();
		}
	}
	
	static public final class ReversibleAnimation extends Animation
	{	
		public int stepsCount;
		public boolean isReversible = true;
		public long[] timesStepSwitching;
		
		public ReversibleAnimation()
		{
			super();
		}
		
		public ReversibleAnimation(ReversibleAnimation a)
		{
			super(a);
			stepsCount = a.stepsCount;
			isReversible = a.isReversible;
			timesStepSwitching = new long[a.timesStepSwitching.length];
			System.arraycopy(a.timesStepSwitching, 0, timesStepSwitching, 0, a.timesStepSwitching.length);
		}
		
		@Override
		public void update(long timeElapsed) 
		{
			if (isPlaying == true)
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
						texturePosition.reduce(textureSize.width, 0f);
					}
					else
					{
						++step;
						texturePosition.add(textureSize.width, 0f);
					}
				}
			}
			computeMatrixes();
		}
	}
	
	static public final class IteratableAnimation extends Animation
	{
		
		
		public int state, statesCount;
		public long stepSwitchTime;
		public Point2f[] texturePositionsInitial;
		public int[] stepsCount;
		
		public IteratableAnimation()
		{
			super();
		}
		
		public IteratableAnimation(IteratableAnimation a)
		{
			super(a);
			int i, n;
			state = a.state;
			statesCount = a.statesCount;
			stepSwitchTime = a.stepSwitchTime;
			
			n = a.stepsCount.length;
			stepsCount = new int[n];
			System.arraycopy(a.stepsCount, 0, stepsCount, 0, n);
			
			
			texturePositionsInitial = new Point2f[n];
			for (i = 0, n = a.texturePositionsInitial.length; i < n; ++i)
			{
				texturePositionsInitial[i] = new Point2f(a.texturePositionsInitial[i]);
			}
		}
				
		public void setState(int _state)
		{
			if (state != _state)
			{
				state = _state;
				step = 0;
				texturePosition.set(texturePositionsInitial[_state]);
			}
		}
		
		public void zeroStep()
		{
			step = 0;
			texturePosition.set(texturePositionsInitial[state]);
		}

		@Override
		public void update(long timeElapsed) 
		{
			if (isPlaying == true)
			{
				time += timeElapsed;
				if (time > stepSwitchTime)
				{
					step += time / stepSwitchTime;
					time %= stepSwitchTime;
					step %= stepsCount[state];
					texturePosition.set(texturePositionsInitial[state]);
					texturePosition.add(textureSize.width * step, 0f);
				}
			}
			computeMatrixes();
		}
	}
	
	static public abstract class GraphicsData implements IDrawable, IUpdatable
	{
		public GraphicsData()
		{
			
		}
		
		public GraphicsData(GraphicsData d)
		{
			
		}
	}
	
	static public final class SpriteResource
	{
		public int resourceId;
		public int[] textureId = new int[1];
		public Bitmap bitmap;
		public boolean isUsed = false;
		
		public SpriteResource()
		{
			resourceId = ZConstants.RESOURCE_ID_INVALID;
			textureId[0] = ZConstants.RESOURCE_ID_INVALID;
			bitmap = null;
		}
		
		public SpriteResource(int _resourceId)
		{
			resourceId = _resourceId;
			textureId[0] = ZConstants.RESOURCE_ID_INVALID;
			bitmap = null;
		}
		
		public void DecodeBitmap()
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			bitmap = BitmapFactory.decodeResource(ZGame.resources, resourceId, options);
		}
		
		public void BindTexture()
		{
			GLES10.glGenTextures(1, textureId, 0);     
			GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textureId[0]); 
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR);                                                     // 5a 
			GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR); 
			GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
		}
		
		public void DeleteTexture()
		{
			GLES10.glDeleteTextures(1, textureId, 0);
			textureId[0] = ZConstants.IDENTITY_ID_INVALID;
			isUsed = false;
		}
		
		static public Bitmap DecodeBitmap(int id)
		{
			return BitmapFactory.decodeResource(ZGame.resources, id);
		}
	}
	
	static public Surface surface;
	static public Renderer renderer;
	
	static private ArrayList<SingleAnimation> postSingleAnimations;
	static private int postSingleAnimationsCount;
	static private float[] cameraMatrix = new float[ZConstants.GRAPHICS_MATRIX_SIZE];
	
	static private ZPlayer player;
	static private ZMap map;
	static private ArrayList<ZMob> mobs;
	static private ArrayList<ZEffect> effects;
	static private ArrayList<ZItem> items;
	static private ArrayList<ZMachinery> machineries;
	static private ArrayList<ZObject> objects;
	
	static 
	{
		postSingleAnimations = new ArrayList<SingleAnimation>();
	}
	
 	static public void Adjust()
	{
		ZLog.d(ZLog.TAG_GRAPHICS);
		renderer = new Renderer();
		surface = new Surface(ZGame.context);
        surface.setRenderer(renderer); 
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        ZGame.activity.setContentView(surface);
	}
 	
 	static public void SetScene(ZLevel level)
 	{
 		player = level.player;
 		map = level.map;
 		
 		effects = level.map.effects;
 		mobs = level.map.mobs;
 		items = level.map.items;
 		objects = level.map.objects;
 		machineries = level.map.machineries;
 	}

	static public void OnResume()
	{
		ZLog.d(ZLog.TAG_GRAPHICS);
		//TODO don't use it
		surface.onResume();
	}
	
	static public void OnPause()
	{
		ZLog.d(ZLog.TAG_GRAPHICS);
		//TODO don't use it
		surface.onPause();
	}

	static private void ComputeCameraMatrix()
	{
		//TODO rewrite		
		Point2f translateDelta = new Point2f(), playerPoint = ZGame.level.player.physicsData.position;
		Size2f mapSizeHalf = ZGame.level.map.graphicsData.tile.modelSizeHalf;
		Matrix.setIdentityM(cameraMatrix, 0);
		//Matrix.orthoM(cameraMatrix, 0, -1, 1, -1, 1, -1, 1);
		
		if (mapSizeHalf.width <= 1f)
		{
			translateDelta.x = ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2;
		}
		else
			if (playerPoint.x * ZSettings.MAP_CELL_SIZE_RELATIVE.width + 
					ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2 < 1f)
			{
				translateDelta.x = mapSizeHalf.width - 1f + ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2;
			}
			else
				if ((mapSizeHalf.width + mapSizeHalf.width) - playerPoint.x * ZSettings.MAP_CELL_SIZE_RELATIVE.width - 
						ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2 < 1f)
				{
					translateDelta.x = 1f - mapSizeHalf.width + ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2;
				}
				else
				{
					translateDelta.x = mapSizeHalf.width - playerPoint.x * ZSettings.MAP_CELL_SIZE_RELATIVE.width;
				}
		
		if (mapSizeHalf.height <= 1f)
		{
			translateDelta.y = ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2;
		}
		else
			if (playerPoint.y * ZSettings.MAP_CELL_SIZE_RELATIVE.height +
					ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2  < 1f)
			{
				translateDelta.y = mapSizeHalf.height - 1f + ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2;
			}
			else
				if ((mapSizeHalf.height + mapSizeHalf.height) - playerPoint.y * ZSettings.MAP_CELL_SIZE_RELATIVE.height -
						ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2 < 1f)
				{
					translateDelta.y = 1f - mapSizeHalf.height + ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2;
				}
				else
				{
					translateDelta.y = mapSizeHalf.height - playerPoint.y * ZSettings.MAP_CELL_SIZE_RELATIVE.height;
				}
		
		
		//Matrix.translateM(cameraMatrix, 0, translateDelta.x, translateDelta.y, 0);
		Matrix.orthoM(cameraMatrix, 0, -1 - translateDelta.x, 1 - translateDelta.x, 
				-1 - translateDelta.y, 1 - translateDelta.y, -1, 1);
	}
	
	static public void AddPostSingleAnimation(SingleAnimation a)
	{
		if (postSingleAnimations.size() > postSingleAnimationsCount)
		{
			postSingleAnimations.set(postSingleAnimationsCount, a);
			++postSingleAnimationsCount;
		}
		else
		{
			postSingleAnimations.add(a);
			postSingleAnimationsCount = postSingleAnimations.size();
		}
	}
	
	static public void DeletePostSingleAnimation(int index)
	{
		--postSingleAnimationsCount;
		postSingleAnimations.set(index, postSingleAnimations.get(postSingleAnimationsCount));
		postSingleAnimations.set(postSingleAnimationsCount, null);
	}
	
	static public void Update(long timeElapsed)
	{
		int i, n;
		
		map.graphicsData.update(timeElapsed);
		player.graphicsData.update(timeElapsed);
		
		for (i = 0, n = map.effectsCount; i < n; ++i)
		{
			effects.get(i).graphicsData.update(timeElapsed);
		}
		
		for (i = 0, n = map.itemsCount; i < n; ++i)
		{
			items.get(i).graphicsData.update(timeElapsed);
		}
		
		for (i = 0, n = map.machineriesCount; i < n; ++i)
		{
			machineries.get(i).graphicsData.update(timeElapsed);
		}
		
		for (i = 0, n = map.mobsCount; i < n; ++i)
		{
			mobs.get(i).graphicsData.update(timeElapsed);
		}
		
		for (i = 0, n = map.objectsCount; i < n; ++i)
		{
			objects.get(i).graphicsData.update(timeElapsed);
		}
		
		i = 0;
		while (i < postSingleAnimationsCount)
		{
			postSingleAnimations.get(i).update(timeElapsed);
			if (postSingleAnimations.get(i).isFinished == false)
			{
				++i;
			}
			else
			{
				DeletePostSingleAnimation(i);
			}
		}
	}
	
	static public void RequestRender()
	{		
		ComputeCameraMatrix();
		surface.requestRender();
	}
}
