package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 *
 *
 * Created by dubois on 27/12/2017.
 */

public class Car {
    private SpriteSheet sprite;

    float x,y,direction;
    float vX,vY,dd;

    public Car(int sprite_id, float x, float y, float direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.sprite = SpriteSheet.get(sprite_id);
//        this.v = 0;
        this.dd = 0;
    }

    public void paint(Canvas canvas, int unit_x, int unit_y){
        canvas.save();
        canvas.translate(x*unit_x,y*unit_y);
        canvas.rotate(direction);
        canvas.scale(0.5f,0.5f);
        int model = 0;
//        if (dd < -5) model = 1;
//        if (dd > 5) model = 2;
        sprite.paint(canvas,model,-sprite.w/2 , -sprite.h/2);
        canvas.restore();
    }


    public void update(Track track) {
//        direction += dd*v*sprite.h;
        direction = (float) Math.toDegrees(Math.atan2(vY,vX))+90;
//        x += (float) ( v*sprite.h*Math.cos(Math.toRadians(direction-90)));
//        y += (float) ( v*sprite.h*Math.sin(Math.toRadians(direction-90)));



        float c = 1;
        if (!track.valid(x+vX,y+vY)){
            c =0.3f;
        }

        if (track.valid(x+vX,y)){
            x += (float) ( vX*c);
        }
        if (track.valid(x,y+vY)){
            y += (float) ( vY*c);
        }
    }

    public double bound(double value, double max){
        if (value >= max) value = max;
        if (value <= -max) value = -max;
        return value;
    }

    public double rescale(double value, double max, double bound){
        value = bound(value, bound);
        value = value * max/ bound;
        return value;
    }

    public void setCommand(double pitch,double  roll) {
        pitch = rescale(pitch,90,15);
        roll = rescale(roll,90,15);


//        this.v = (float) (pitch*0.000025);


        this.vY = (float) -(pitch*0.0009);
        this.vX = (float) (roll*0.0009);

    }
}
