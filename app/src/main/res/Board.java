/**
 * Created by savani on 2/28/16.
 */
import java.util.Random;
import java.util.Scanner;
public class Board {

        int board[][];
        int rows = 0,
                cols = 0,
                mines = 0;
        Board(int rows, int cols, int mines){
            this.rows = rows;
            this.cols = cols;
            this.board = new int[rows][cols];
            Random rand = new Random();
            int mine_row, mine_col;

            //generating mines
            for(int i = 0; i< mines; i++){
                mine_row = rand.nextInt(12);
                mine_col = rand.nextInt(8);
                if (board[mine_row][mine_col] == 0){
                    board[mine_row][mine_col] = 10;
                } else{
                    i--;
                }
            }
            print();

            // setting up other boxes
            for(int i = 0; i < rows; i++){
                for(int j= 0; j< cols; j++){
                    if(board[i][j] == 10){
                        set_boxes(i, j);
                    }
                }

            }
            System.out.print("\n");
            System.out.print("New "+ "\n");

        }
        void print(){
            for(int i = 0; i < rows; i++){
                for(int j= 0; j< cols; j++){

                    System.out.print("\t"+board[i][j]);
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
                    if (board[i][j] != 10){
                        board[i][j]++;
                    }
                }
            }
        }
    }


