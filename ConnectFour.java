// Zach Sally
// February 27, 2022
// Create Connect Four Game

import java.util.*;

public class ConnectFour 
{
    // Method to print the board
    public static void printBoard(char[][] board)
    {
        for (int i = 0; i < board.length; i++) 
        {
            System.out.printf("%3c", '|');
            
            for (int j = 0; j < board[0].length; j++) 
            {                
                // Check if the current cell is unused and print out
                // appropriate characters for spacing
                if (board[i][j] == '\u0000') 
                {
                    System.out.printf("%c%c", ' ', '|');
                    continue;
                }

                System.out.printf("%c%c", board[i][j], '|');
            }
            System.out.println();
        }
        
        // Loop to print out the base of the board
        for (int i = 0; i < board.length * 3 - 1; i++) 
        {
            System.out.printf("%c", (i < 2) ? ' ' : '-');
        }
        System.out.println();
    }
    
    // Method to check if the disk just dropped makes 4 in a row across
    public static boolean checkAcross(char[][] board, char player, int row, int col) 
    {
        boolean connectFour;
        
        for (int i = 0; i < 4; i++) 
        {
            connectFour = true;
            
            for (int j = col - i; j < col - i + 4; j++) 
            {                
                // Check if the index of the column to check is within bounds
                if (j < 0 || j > board[0].length - 1) 
                {
                    connectFour = false;
                    break;
                }
                
                // Check if that cell is equal to the next cell
                if (board[row][j] != player) 
                {
                    connectFour = false;
                    break;
                }                    
            }
            
            // Found a connect 4 across
            if (connectFour)
                return true;  
        }
        
        // Didn't find a connect 4 across
        return false;
    }
    
    // Method to check if the disk just dropped creates a connect 4 vertically
    public static boolean checkVertical(char[][] board, char player, int row, int col) 
    {        
        // Check if the three cells below the disk are in bounds and equal to the current player
        if (row + 3 >= 0 && row + 3 <= board.length - 1) 
        {
            return (board[row + 1][col] == player && board[row + 2][col] == player && board[row + 3][col] == player);
        }
        
        return false;
    }
    
    // Method to check if the current disk creates a connect 4 diagonally
    public static boolean checkDiagonal(char[][] board, char player, int row, int col) 
    {
        boolean connectFourRight, connectFourLeft;
        
        for (int i = 0; i < 4; i++) 
        {            
            connectFourRight = connectFourLeft = true;
            
            for (int j = 0; j < 4; j++) 
            {                
                // Check if index is in bounds, then check if the shifted index contains correct player
                // Checks for Increase (left -> right) and Decrease (right -> left)
                if (row - i + j < 0 || row - i + j > board.length - 1|| col - i + j < 0 || col - i + j > board[0].length - 1) 
                {
                    connectFourRight = false;
                } 
                else if (board[row - i + j][col - i + j] != player) 
                {
                    connectFourRight = false;
                }
                
                // Check if index is in bounds, then check if the shifted index contains correct player
                // Checks for Increase (right -> left) and Decrease (left -> right)
                if (row - i + j < 0 || row - i + j > board.length - 1 || col + i - j < 0 || col + i - j > board[0].length - 1) 
                {
                    connectFourLeft = false;
                } 
                else if (board[row - i + j][col + i - j] != player) 
                {
                    connectFourLeft = false;
                }
            }

            // Found a connect 4 with either left or right diagonal
            if (connectFourRight || connectFourLeft)
            {
                return true;
            }
                
        }

        // Never found a connect 4 in diagonal
        return false;
    }
    
    // Method to check if there is a draw (all of board is filled with disks)
    // Board is filled when top row (row 0) has no unused ('\u0000') cells
    public static boolean checkDraw(char[][] board) 
    {
        for (int i = 0; i < board[0].length; i++) 
        {
            // Check if there is an unused cell on the top of the board (row 0)
            if (board[0][i] == '\u0000')
                return false;
        }
        
        return true;
    }
    
    // Method to check if there is a connect 4 across, vertical, or diagonal
    public static boolean checkWin(char[][] board, char player, int row, int col) 
    {
        return (checkAcross(board, player, row, col) || checkVertical(board, player, row, col) || checkDiagonal(board, player, row, col));
    }
    
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        
        boolean activeGame = true;
        char player = 'Y';
        char[][] board = new char[6][7];
        int dropColumn, row;
        
        printBoard(board);
        
        while (activeGame) {
            
            // Switch the current player to the next player each loop
            if (player == 'Y') 
            {
                player = 'R';
            } 
            else 
            {
                player = 'Y';
            }
            
            // Pronpt current player to drop a disk in one of the columns
            System.out.printf("Drop a %s disk at column (0-6): ", (player == 'R') ? "red" : "yellow");
            dropColumn = scan.nextInt();
            
            // Check if the user input is within bounds
            if (dropColumn < 0 || dropColumn > board[0].length - 1) 
            {
                while (dropColumn < 0 || dropColumn > board[0].length - 1) 
                {
                    System.out.printf("Invalid Column Number, drop a %s disk at column (0-6): ", (player == 'R') ? "red" : "yellow");
                    dropColumn = scan.nextInt();
                }
            }
            
            // Check if the column user tries to drop in is full
            if (board[0][dropColumn] != '\u0000') 
            {
                while (board[0][dropColumn] != '\u0000') 
                {
                    System.out.printf("Column Full, drop a %s disk at different column (0-6): ", (player == 'R') ? "red" : "yellow");
                    dropColumn = scan.nextInt();
                }
            }
            
            // Loop through each row starting from bottom of the selected column
            // until there isn't a disk and place the disk at that position
            for (row = board.length - 1; row >= 0; row--) 
            {
                if (board[row][dropColumn] == '\u0000') 
                {
                    board[row][dropColumn] = player;
                    break;
                }
            }
            
            printBoard(board);
            
            // Check if game ends in draw or win, else continue to loop
            if (checkDraw(board)) 
            {
                System.out.println("It is a draw");
                activeGame = false;
            }
            else if (checkWin(board, player, row, dropColumn)) 
            {
                System.out.printf("The %s player won\n", (player == 'R') ? "red" : "yellow");
                activeGame = false;
            }
        }
    }
    
}
