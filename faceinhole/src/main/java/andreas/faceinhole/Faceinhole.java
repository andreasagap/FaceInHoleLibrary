package andreas.faceinhole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Andreas Agapitos on 24-Oct-17.
 */

public class Faceinhole extends RelativeLayout {
    private ImageView body,face;
    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;
    private boolean bitmap=false;
    public Faceinhole(Context context) {
        super(context);
        initialize(null,0);
    }

    public Faceinhole(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs,0);
    }
    private void initialize(AttributeSet attrs, int defStyle){
        inflate(getContext(), R.layout.view,this);
        body=(ImageView) findViewById(R.id.body);
        face=(ImageView) findViewById(R.id.face);
        face.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return Faceinhole.this.onTouch(v,event);
            }
        });
    }
    public void setImages(Drawable f,Drawable b)
    {
        face.setImageDrawable(f);
        body.setImageDrawable(b);
        bitmap=false;
    }
    public void setImages(Bitmap f,Bitmap b)
    {
        face.setImageBitmap(f);
        body.setImageBitmap(b);
        bitmap=true;
    }
    public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
        face = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null && event.getPointerCount() == 2 || event.getPointerCount() == 3) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (face.getWidth() / 2) * sx;
                        float yc = (face.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        face.setImageMatrix(matrix);
        return true;
    }

    // Determine the space between the first two fingers

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float s=x * x + y * y;
        return (float)Math.sqrt(s);
    }


    //Calculate the mid point of the first two fingers

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    //Calculate the degree to be rotated by.

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
    public Bitmap mergeImages(){
        Bitmap bitmapback=null,bitmapfore=null;
        if(!bitmap)
        {
            BitmapDrawable background= (BitmapDrawable) body.getDrawable();
            bitmapback = background.getBitmap();
            BitmapDrawable foreground=(BitmapDrawable) face.getDrawable();
            bitmapfore=foreground.getBitmap();
        }
        else{
            body.buildDrawingCache();
            bitmapback = body.getDrawingCache();
            face.buildDrawingCache();
            bitmapfore=face.getDrawingCache();
        }


        Bitmap cs = null;
        int width, height = 0;
        width = body.getWidth();
        height = body.getHeight();
        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        comboImage.drawBitmap(bitmapfore, face.getImageMatrix(), null);
        comboImage.drawBitmap(bitmapback, body.getImageMatrix(), null);
        return cs;
    }
    public void setBodyWidth(int size)
    {
        body.getLayoutParams().width=size;
    }
    public void setBodyHeight(int size)
    {
        body.getLayoutParams().height=size;
    }
    public void setFaceWidth(int size)
    {
        face.getLayoutParams().width=size;
    }
    public void setFaceHeight(int size)
    {
        face.getLayoutParams().height=size;
    }

}
