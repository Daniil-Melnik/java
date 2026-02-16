package testing.LevelFour;

import java.awt.*;

public class GBC extends GridBagConstraints {
    public GBC(int gx, int gy){
        this.gridx = gx;
        this.gridy = gy;
    }

    public GBC(int gx, int gy, int w, int h){
        this.gridx = gx;
        this.gridy = gy;
        this.gridwidth = w;
        this.gridheight = h;
    }

    public GBC setAnchor(int a){
        this.anchor = a;
        return this;
    }

    public GBC setFill(int f){
        this.fill = f;
        return this;
    }

    public GBC setWeight(double wx, double wy){
        this.weightx = wx;
        this.weighty = wy;
        return this;
    }

    public GBC setInsets(int dist){
        this.insets = new Insets(dist, dist, dist, dist);
        return this;
    }

    public GBC setInsets(int t, int b, int l, int r){
        this.insets = new Insets(t, l, b, r);
        return this;
    }
}
