package com.example.savani.lab1;
import java.util.Random;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.GridLayout;
import android.content.Context;

/**
 * Created by savani on 2/28/16.
 */

public class Board {

    BoardCell[][] board;
    int rows = 0,
            cols = 0,
            mines = 0;

    Board(int rows, int cols, int mines, Context context){
        this.rows = rows;
        this.cols = cols;
        board = new BoardCell[rows][cols];
        Random rand = new Random();
        int mine_row, mine_col;
        //instantiating board
        for(int i = 0; i < rows; i++) {
            for(int j= 0; j< cols; j++) {
                 board[i][j] = new BoardCell(i, j, rows, cols, context);
            }
        }
        //generating mines
        for(int i = 0; i< mines; i++){
            mine_row = rand.nextInt(rows);
            mine_col = rand.nextInt(cols);
            if (board[mine_row][mine_col].value == 0){
                board[mine_row][mine_col].value = 10;
            } else {
                i--;
            }
        }
        print();
        // setting up other boxes
        for(int i = 0; i < rows; i++){
            for(int j= 0; j< cols; j++){
                if(board[i][j].value == 10){
                    set_boxes(i, j);
                }
            }
        }
    }
    void print(){
        for(int i = 0; i < rows; i++){
            for(int j= 0; j< cols; j++){

                System.out.print("\t"+board[i][j].value);
            }
            System.out.print("\n");
        }
    }
    void set_boxes(int row, int col){
        for (int i = row - 1; i < row + 2 ; i++ ){
            if( i < 0 || i >= board.length){
                continue;
            }
            for(int j = col - 1; j < col + 2; j++){
                if( j < 0 || j >= board[0].length){
                    continue;
                }
                if (board[i][j].value != 10){
                    board[i][j].value++;
                }
            }
        }
    }

    boolean checkVictory(){
        for(int i = 0; i < rows; i++){
            for(int j= 0; j< cols; j++){
                if (board[i][j].value == 10 && !board[i][j].flagged) {
                    return false;
                } else if (board[i][j].flagged && board[i][j].value != 10) {
                    return false;
                }
            }
        }
        return true;
    }

    BoardCell getCell(int row, int col) {
        return board[row][col];
    }

    int[][] allMines(){
        int[][] arr_mines = new int[mines][2];

        int k = 0;
        for(int i = 0; i <= mines; i++){
            arr_mines[i][0] = 0;
            arr_mines[i][1] = 0;
        }

        for(int i = 0; i < rows; i++){
            for(int j= 0; j< cols; j++){
                if (board[i][j].value == 10){
                    arr_mines[k][0]= i;
                    arr_mines[k][1] = j;
                    k++;
                }
            }
        }
        return arr_mines;
    }
}

class BoardCell {
    int value = 0;
    Button button;
    boolean flagged = false;
    boolean opened = false;
    BoardCell(int i, int j, int rows, int cols, Context context) {
        button = new Button(context);
        button.setIncludeFontPadding(false);
        button.setTextSize(23);
        button.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        //b.setTypeface(null, );
        button.setId(i * cols + j);
        button.setBackgroundResource(R.mipmap.ic_button);
        value = 0;
        flagged = false;
        opened = false;
    }
    boolean isMine() {
        return (value == 10);
    }
    boolean isOpened() {
        return opened;
    }
    boolean isFlagged() {
        return flagged;
    }
    void setButton(Button b) {
        button = b;
    }
    Button getButton() {
        return button;
    }
    void setMine() {
        value = 10;
    }
    void setOpened() {
        button.setTextColor(buttonText(this.value));
        button.setBackgroundResource(R.mipmap.ic_grey);
        button.setEnabled(false);
        if(value != 0) {
            button.setText(Integer.toString(value));
        }
        opened = true;
    }
    void setFlagged() {
         flagged = true;
    }
    void resetFlagged(){
        flagged = false;
    }
    int getValue(){
        return value;
    }
    void setDesign(int boardHeight, int screenWidth, int rows1, int cols1, int i, int j, int textsize){
        GridLayout.LayoutParams buttonLayout = new GridLayout.LayoutParams
                (GridLayout.spec(i), GridLayout.spec(j));
        buttonLayout.height = (int)(boardHeight/rows1);
        buttonLayout.width = (int)(screenWidth/cols1);
        button.setLayoutParams(buttonLayout);
        button.setTextSize(textsize);

    }
    public int buttonText(int num){
        switch (num){
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.rgb(100, 60, 30);
            case 4:
                return Color.MAGENTA;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.rgb(20, 50, 30);
            case 7:
                return Color.GREEN;
            case 8:
                return Color.rgb(70, 50, 10);
        } //end switch
        return android.R.color.black;
    }// end button text
}
