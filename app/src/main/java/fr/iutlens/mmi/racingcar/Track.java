package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 * Created by dubois on 27/12/2017.
 */

public class Track {

    public static final int HAUT = 1;
    public static final int DROIT = 2;
    public static final int BAS = 4;
    public static final int GAUCHE = 8;
    private static final float MUR = 0.12f;
    private int[][] data;
    private final String DIGITS ="0123456789ABCDEF";
    // 0123
    // 4567
    // 89AB
    // CDEF
    private final String[] def = {
            "93B951153B",
            "AC2C3EC3AA",
            "ABC1695686",
            "C2BC3853C3",
            "96C56C747A",
            "C15153953A",
            "96D6D6A96A",
            "C51395683A",
            "956A853AC6",
            "C57C696C57"};

    private final int char2hex(char c){
        return DIGITS.indexOf(c);
    }

    private SpriteSheet sprite;

    public Track(String[] data, int sprite_id){
        sprite = SpriteSheet.get(sprite_id);
        if (data == null) data = def;
        this.data = new int[data.length][data[0].length()];
        for(int i = 0; i < data.length ; ++i){
            for(int j= 0; j< data[i].length();++j){
                this.data[i][j] =char2hex(data[i].charAt(j));
            }
        }
    }

    public int get(int i, int j){
        return data[j][i];
    }

    public int getSizeY(){
        return data.length;
    }
    public int getSizeX(){
        return data[0].length;
    }

    public int getWidth(){
        return getSizeX()*sprite.w;
    }
    public int getHeight(){
        return getSizeY()*sprite.h;
    }

    public int getTileWidth(){
        return sprite.w;
    }
    public int getTileHeight(){
        return sprite.h;
    }

    public void paint(Canvas canvas){
        for(int i = 0; i < data.length ; ++i) {
            for (int j = 0; j < data[i].length; ++j) {
                sprite.paint(canvas, data[i][j], j * sprite.w, i * sprite.h);
            }
        }
    }

    public boolean valid(float x, float y) {
        if (x <0 || y <0) return true;
        int i = (int) x;
        int j = (int) y;
        if (i >= getSizeX() || j >= getSizeY()) {
            return true;
        }
        x= x-i;
        y =y-j;

        int code= get(i,j);

        return mask(code,x,y);
/*        if ((code & HAUT)!= 0){

        }
*/

//        return true;
    }

    private boolean mask(int code, float x, float y) {
        if ((code & HAUT)!= 0 && y< MUR) return false;
        if ((code & BAS)!= 0 && y > 1-MUR) return false;
        if ((code & GAUCHE)!= 0 && x< MUR) return false;
        if ((code & DROIT)!= 0 && x > 1-MUR) return false;


        return true;
    }

    public boolean dehors(float x, float y) {
         if (x <0 || y <0) return true;
        int i = (int) x;
        int j = (int) y;
        if (i >= getSizeX() || j >= getSizeY()) {
            return true;
        }
        return false;
    }
}
