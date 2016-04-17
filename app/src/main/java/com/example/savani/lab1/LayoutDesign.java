package com.example.savani.lab1;


import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * Created by savani on 4/17/16.
 */
public  class LayoutDesign {

    public static void landscapeLayoutDesign(Button counter, Button flagButton, Button restart){
        System.out.println("Inside LayoutDesign  ");
        GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                (GridLayout.spec(2, 2), GridLayout.spec(14, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        flagButton.setLayoutParams(buttonLayout);
        buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(4, 2), GridLayout.spec(14, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        counter.setLayoutParams(buttonLayout);
        buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(6, 2), GridLayout.spec(14, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        restart.setLayoutParams(buttonLayout);
    }


}
