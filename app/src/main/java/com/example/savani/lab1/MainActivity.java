package com.example.savani.lab1;

import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.Button;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.content.Context;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Board boardGame;
    GridLayout layout;
    int rows = 12;
    int cols = 8;
    int mines = 30;
    boolean flagOn = false;
    int remainingMines = mines;
    Button flagButton;
    Button counter;
    Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        layout = (GridLayout) findViewById(R.id.layout);

        boardGame = new Board(rows, cols, mines, this);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button b = boardGame.getCell(i, j).getButton();
                GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                        (GridLayout.spec(i), GridLayout.spec(j));
                buttonLayout.width = (size.x/cols);
                buttonLayout.height = (int)(size.y * 0.8)/rows;
                b.setLayoutParams(buttonLayout);
                b.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Button button = (Button)(v);
                        boolean victory = false;
                        int id = v.getId();
                        BoardCell cell = boardGame.getCell(id / cols, id % cols);
                        if(cell.isOpened()) return;
                        if (flagOn == false && !cell.isFlagged()) {
                            // Flag button is disabled
                            rippleEffect(id / 8, id % 8);
                            if (cell.isMine()) {
                                button.setBackgroundResource(R.mipmap.ic_mine2);
                                displayMines(MainActivity.this);
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage(R.string.Lose)
                                        .setPositiveButton(R.string.Try_again, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                MainActivity.this.recreate();
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                                builder.setCancelable(false);
                                builder.create();
                                builder.show();
                            } else {
                                cell.setOpened();
                            }
                        } else if(flagOn == true && !cell.isFlagged()) {
                            // Flag button is enabled
                            if(remainingMines > 0) {
                                remainingMines--;
                                counter.setText(Integer.toString(remainingMines));
                            }
                            cell.setFlagged();
                            victory = boardGame.checkVictory();
                            if (victory){
                                alertVictory(MainActivity.this);
                            }//for victory
                            button.setBackgroundResource(R.mipmap.ic_flag);
                        } // setting flag
                        else if(flagOn == true && cell.isFlagged()){
                            cell.resetFlagged();
                            if(remainingMines < mines) {
                                remainingMines++;
                                counter.setText(Integer.toString(remainingMines));
                            }
                            button.setBackgroundResource(R.mipmap.ic_button);
                        }//end of handling reset of flag
                    }
                });
                layout.addView(b);
            }
        }
        flagButton = new Button(this);
        flagButton.setBackgroundResource(R.mipmap.ic_flag);
        flagOn = false;
        flagButton.setId(1100);
        GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                (GridLayout.spec(14, 2), GridLayout.spec(1, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        flagButton.setLayoutParams(buttonLayout);
        layout.addView(flagButton);
        flagButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) (v);
                System.out.println("Inside button mark");
                System.out.println(button.getText() + "  " + getString(R.string.Mark));
                if (flagOn == true) {
                    flagOn = false;
                    button.setBackgroundResource(R.mipmap.ic_flag);


                } else {
                    flagOn = true;
                    button.setBackgroundResource(R.mipmap.ic_flagpressed);
                }
            }
        });
        counter = new Button(this);
        counter.setText(Integer.toString(remainingMines));
        counter.setId(1200);
        counter.setIncludeFontPadding(false);
        counter.setTextSize(550);
        counter.setTextColor(Color.RED);
        counter.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(14, 2), GridLayout.spec(3, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        counter.setLayoutParams(buttonLayout);
        layout.addView(counter);

        restart = new Button(this);
        restart.setText(R.string.Restart);
        restart.setId(1300);
        counter.setIncludeFontPadding(false);
        counter.setTextSize(50);
        counter.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(14, 2), GridLayout.spec(5, 2));
        buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
        restart.setLayoutParams(buttonLayout);
        layout.addView(restart);
        restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.recreate();
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.savani.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.savani.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("Landscape " + size.x + " " + size.y);
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Button b = boardGame.getCell(j, i).getButton();
                    GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                            (GridLayout.spec(cols - i), GridLayout.spec(j));
                    buttonLayout.width = (int)(size.x * 0.85)/rows;
                    buttonLayout.height = size.y/cols;
                    b.setLayoutParams(buttonLayout);
                }
            }
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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("Portrait " + size.x + " " + size.y);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Button b = boardGame.getCell(i, j).getButton();
                    GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                            (GridLayout.spec(i), GridLayout.spec(j));
                    buttonLayout.width = size.x/cols;
                    buttonLayout.height = (int)(size.y * 0.85)/rows;
                    b.setLayoutParams(buttonLayout);
                }
            }
            GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                    (GridLayout.spec(14, 2), GridLayout.spec(1, 2));
            buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
            flagButton.setLayoutParams(buttonLayout);
            buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(14, 2), GridLayout.spec(3, 2));
            buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
            counter.setLayoutParams(buttonLayout);
            buttonLayout = new GridLayout.LayoutParams(GridLayout.spec(14, 2), GridLayout.spec(5, 2));
            buttonLayout.setGravity(Gravity.FILL_VERTICAL | Gravity.FILL_HORIZONTAL);
            restart.setLayoutParams(buttonLayout);
        }
    }

    void alertVictory(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.Win)
                .setPositiveButton(R.string.Try_again, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.recreate();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create();
        builder.show();
   }
    public void displayMines(Context context){
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                BoardCell cell = boardGame.getCell(i,j);
                if(cell.isMine()){
                    Button button = (Button) findViewById(i * 8 + j);
                    button.setBackgroundResource(R.mipmap.ic_mine2);

                 }
            }
        }
    }

   public void rippleEffect(int rowId, int colId) {
       if(rowId < 0 || rowId >= rows || colId < 0 || colId >= cols) {
           return;
       }
       BoardCell cell = boardGame.getCell(rowId, colId);
       if(cell.isMine() || cell.isOpened() || cell.isFlagged() ) {
           return;
       }
       cell.setOpened();
       if(cell.getValue() == 0){
            rippleEffect(rowId - 1, colId);
            rippleEffect(rowId + 1, colId);
            rippleEffect(rowId, colId - 1);
            rippleEffect(rowId, colId + 1);
       }
    }
}
